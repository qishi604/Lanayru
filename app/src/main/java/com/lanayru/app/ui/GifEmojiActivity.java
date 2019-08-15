package com.lanayru.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lanayru.library.ui.base.BaseActivity;
import com.lanayru.library.ui.base.RvAdapter;
import com.lanayru.view.IDateView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-06-30
 */
public class GifEmojiActivity extends BaseActivity {

    RecyclerView mRv;

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        mRv = new RecyclerView(_this);
        setContentView(mRv);

        final GridLayoutManager layoutManager = new GridLayoutManager(_this, 4);
        mRv.setLayoutManager(layoutManager);

        RvAdapter<String> adapter = new RvAdapter<String>() {
            @NotNull
            @Override
            public View onCreateView(@Nullable ViewGroup parent, int viewType) {
                return new ItemView(parent.getContext());
            }
        };

        mRv.setAdapter(adapter);

        adapter.setData(getData());
    }

    private List<String> getData() {
        try {
            final String[] ss = getAssets().list("gif");
            List<String> list = new ArrayList<>(ss.length);
            for (String s : ss) {
                list.add("file:///android_asset/gif/" + s);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class ItemView extends android.support.v7.widget.AppCompatImageView implements IDateView<String> {

        private String mData;

        public ItemView(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public String getData() {
            return mData;
        }

        @Override
        public void setData(@Nullable String d) {
            mData = d;
            Glide.with(this).load(d).into(this);
        }
    }
}
