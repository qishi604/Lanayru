package com.lanayru.app.ui.album

/**
 * # 测试多进程内存占用情况
 *
 * 发现一个 app 可以创建多个进程，这样就可以突破最大内存限制的问题。
 * 可以将占用内存大的页面如突破浏览单独一个进程
 */
class AlbumActivity1 : AlbumActivity() {
}