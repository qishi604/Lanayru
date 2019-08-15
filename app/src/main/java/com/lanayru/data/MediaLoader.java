package com.lanayru.data;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.lanayru.model.Media;
import com.lanayru.util.Logs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MediaLoader implements LoaderManager.LoaderCallbacks {

    private static final String[] MEDIA_PROJECTION = {
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.PARENT,
            MediaStore.Video.Media.DURATION

    };

    Context mContext;

    private static List<Media> sAllPhotos;


    /**
     * 视频时长缓存，因为获取视频时长比较耗时，所以缓存下来
     */
    private static Map<Long, Long> sDurationMap = new HashMap<>();

    private Callback mCallback;

    public MediaLoader(Context context, Callback callback) {
        this.mContext = context;
        mCallback = callback;
    }

    public static List<Media> getPhotoCache() {
        return sAllPhotos;
    }

    @Override
    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader loader, Object o) {
        final Cursor cursor = (Cursor) o;
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ArrayList<Media>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Media>> emitter) throws Exception {
                emitter.onNext(process(cursor));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Media>>() {
                    @Override
                    public void accept(ArrayList<Media> media) throws Exception {
                        sAllPhotos = media;
                        mCallback.call(media);
                    }
                });
    }

    private ArrayList<Media> process(Cursor cursor) {
        ArrayList<Media> medias = new ArrayList<>(1000);

        try {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                Logs.INSTANCE.i("media data " + path);
                if (!isExist(path)) {
                    continue;
                }

                String thumb = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                long id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                if (TextUtils.isEmpty(path)) {
                    continue;
                }
                if (TextUtils.isEmpty(thumb)) {
                    thumb = path;
                }

                Media media = new Media(id, path, thumb, name, mediaType, dateTime, size, 0);

                medias.add(media);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return medias;
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    /**
     * 获取视频时长
     *
     * @param videoPath         视频地址
     * @param metadataRetriever 优先使用 MediaMetadataRetriever 获取，获取不到再用 MediaPlayer 获取
     * @param mediaPlayer       MediaPlayer
     * @return 视频时长，单位毫秒
     */
    public static long getDuration(String videoPath, MediaMetadataRetriever metadataRetriever, MediaPlayer mediaPlayer) {
        long duration = 0;

        try {
            metadataRetriever.setDataSource(videoPath);
            final String s = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            duration = Long.parseLong(s);
        } catch (Exception e) {
            // 出现异常，用 MediaPlayer 获取
            try {
                mediaPlayer.setDataSource(videoPath);
                duration = mediaPlayer.getDuration();
            } catch (IOException e1) {
                // ignore
            }
        }

        return duration;
    }

    private boolean isExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File f = new File(path);
        if (f.exists() && f.length() > 10) {
            return true;
        }

        return false;
    }

    /**
     * 清除缓存
     */
    public void clear() {
        if (null != sAllPhotos) {
            sAllPhotos.clear();
            sAllPhotos = null;
        }
    }
}
