package com.lanayru.javademo.dagger;

import com.lanayru.javademo.dagger.service.IApp;

import javax.inject.Inject;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/3/14
 */
public class ShareHelper {

    @Inject
    IApp mApp;

    public void share() {
        mApp.init();
        System.out.println("share something...");
    }
}
