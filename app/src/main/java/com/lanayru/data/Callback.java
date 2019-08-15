package com.lanayru.data;

import com.lanayru.model.Media;

import java.util.ArrayList;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-03
 */
public interface Callback {

    void call(ArrayList<Media> list);
}
