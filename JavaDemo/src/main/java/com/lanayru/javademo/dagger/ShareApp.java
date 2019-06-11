package com.lanayru.javademo.dagger;

import com.lanayru.javademo.dagger.service.IApp;

import javax.inject.Inject;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/3/14
 */
public class ShareApp implements IApp {

    @Inject
    ShareApp() {
    }

    @Override
    public void init() {
        System.out.println("Share app init");
    }
}
