package com.example.sxs10540.uiutil;

/**
 * Created by sxs10540 on 2017/7/27.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
