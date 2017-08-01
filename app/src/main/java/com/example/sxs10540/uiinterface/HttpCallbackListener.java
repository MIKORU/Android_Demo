package com.example.sxs10540.uiinterface;

/**
 * Created by sxs10540 on 2017/7/27.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
