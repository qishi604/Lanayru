package com.lanayru.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanayru.library.ui.base.BaseActivity;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
@Route(path = "/web/main")
public class WebActivity extends BaseActivity {

    private WebView mWebView;

    @Autowired
    String url;

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        ARouter.getInstance().inject(this);

        mWebView = findViewById(R.id.webView);

        if (TextUtils.isEmpty(url)) {
            url = "http://cn.bing.com";
        }

        mWebView.loadUrl(url);
    }
}
