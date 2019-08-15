package com.lanayru.app.ui.rv;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.lanayru.app.R;
import com.lanayru.library.ui.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/4/24
 */
public class LoadMoreRvActivity extends BaseActivity {

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private int mPage = 1;

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(_this);
        setContentView(rv);

        rv.setLayoutManager(new LinearLayoutManager(_this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_view_item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.textView1, item);
            }
        };

        setEmptyView();
        mAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                fetchData(mPage);
            }
        }, rv);

        rv.setAdapter(mAdapter);

        fetchData(mPage);

    }

    private void setEmptyView() {
        TextView tv = new TextView(_this);
        tv.setText("Empty");
        mAdapter.setEmptyView(tv);
    }

    private void fetchData(final int page) {
        Observable<List<String>> ob = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> list = new ArrayList<>(10);
                for (int i = 0, N = 10; i < N; i++) {
                    list.add("Data " + i);
                }

                Thread.sleep(3000);


                emitter.onNext(list);
                emitter.onComplete();
            }
        });

        final Disposable subscribe = ob.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mAdapter.addData(strings);
                        mAdapter.loadMoreComplete();
                    }
                });
    }
}
