package com.lanayru.data

import android.support.v4.app.LoaderManager
import com.lanayru.app.App
import com.lanayru.model.Media

object AlbumProvider {

    fun getData(loaderManager: LoaderManager, callback:(List<Media>)->Unit) {

        val mediaLoader = MediaLoader(App.getApp(), MediaLoader.Callback {
            callback.invoke(it)
        })

        loaderManager.initLoader(0, null, mediaLoader)
    }
}
