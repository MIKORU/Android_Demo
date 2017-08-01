package com.example.sxs10540.uiinterface;

/**
 * Created by sxs10540 on 2017/7/28.
 */

public interface DownLoadListener {
    void onProgress(int progress);    //下载进度

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
