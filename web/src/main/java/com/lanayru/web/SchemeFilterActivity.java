package com.lanayru.web;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 监听Scheme事件，做路由中转
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
public class SchemeFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 直接通过ARouter处理
        Uri uri = getIntent().getData();
//        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
////            @Override
////            public void onArrival(Postcard postcard) {
////                finish();
////            }
////        });
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
