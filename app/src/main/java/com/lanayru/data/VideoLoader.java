package com.lanayru.data;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.lanayru.model.Media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class VideoLoader implements LoaderManager.LoaderCallbacks {

    private static final String[] MEDIA_PROJECTION = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.MINI_THUMB_MAGIC,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DURATION
    };

    Context mContext;

    private static List<Media> sAllVideos;

    private Callback mCallback;

    public VideoLoader(Context context, Callback callback) {
        this.mContext = context.getApplicationContext();
        mCallback = callback;
    }

    public static List<Media> getVideoCache() {
        return sAllVideos;
    }

    @Override
    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        Uri queryUri = MediaStore.Video.Media.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                null,
                null, // Selection args (none).
                MediaStore.Video.Media.DATE_ADDED + " DESC" // Sort order.
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
                        sAllVideos = media;
                        mCallback.call(media);
                    }
                });
    }

    private ArrayList<Media> process(Cursor cursor) {
        ArrayList<Media> medias = new ArrayList<>(1000);

        try {
            while (cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String thumb = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MINI_THUMB_MAGIC));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                if (TextUtils.isEmpty(path)) {
                    continue;
                }

                if (TextUtils.isEmpty(thumb)) {
                    thumb = path;
                }

                Media media = new Media(id, path, thumb, name, 0, dateTime, size, duration);
                medias.add(media);

                if (duration <= 0) {
                    addDuration(media);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        if (mNoDurationMedias.size() > 0) {
            execute();
        }

        return medias;
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    Map<Long, Long> mDurationCache = new HashMap<>();

    LinkedList<Media> mNoDurationMedias = new LinkedList<>();
    private void addDuration(Media media) {
        final Long duration = mDurationCache.get(media.getId());
        if (null != duration) {
            media.setDuration(duration);
            return;
        }

        mNoDurationMedias.add(media);

//        synchronized (mNoDurationMedias) {
//            final int i = mNoDurationMedias.indexOf(media);
//            if (i != -1) {
//                mNoDurationMedias.remove(media);
//            }
//        mNoDurationMedias.add(media);
//
//        }
    }

    boolean isExecuted = false;
    private void execute() {
        if (isExecuted) {
            return;
        }
        isExecuted = true;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                while (mNoDurationMedias.size() > 0) {
                    final Media media = mNoDurationMedias.removeLast();
                    final long duration = getDuration(media.getPath());
                    media.setDuration(duration);
                    mDurationCache.put(media.getId(), duration);
                }
                isExecuted = false;
            }
        });
    }

    MediaMetadataRetriever metadataRetriever;

    MediaPlayer mediaPlayer;

    /**
     * 获取视频时长
     * 优先使用 MediaMetadataRetriever 获取，获取不到再用 MediaPlayer 获取
     *
     * @param videoPath         视频地址
     * @return 视频时长，单位毫秒
     */
    public long getDuration(String videoPath) {
        long duration = 0;

        try {
            if (null == metadataRetriever) {
                metadataRetriever = new MediaMetadataRetriever();
            }
            metadataRetriever.setDataSource(videoPath);
            final String s = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            duration = Long.parseLong(s);
        } catch (Exception e) {
            // 出现异常，用 MediaPlayer 获取
            if (null == mediaPlayer) {
                mediaPlayer = new MediaPlayer();
            }
            try {
                mediaPlayer.setDataSource(videoPath);
                duration = mediaPlayer.getDuration();
            } catch (IOException e1) {
                // ignore
            }
        }

        return duration;
    }

    /**
     * 清除缓存
     */
    public void clear() {
        if (null != sAllVideos) {
            sAllVideos.clear();
            sAllVideos = null;
        }
    }
}
