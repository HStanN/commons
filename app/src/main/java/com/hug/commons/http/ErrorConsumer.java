package com.hug.commons.http;

import com.baohan.ebox.EboxApp;
import com.baohan.ebox.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * Created by HStan on 2017/9/29.
 */
public abstract class ErrorConsumer implements Consumer<Throwable> {

    private static final String TAG = "RxException";
    public String error;

    private static final String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    private static final String CONNECTEXCEPTION = "网络连接异常，请检查您的网络状态";
    private static final String SERVICE_NETWORKEXCEPTION = "服务器正在维护中。。。";
    private static final String USER_NETWORKEXCEPTION = "网络无连接，请检查网络状态是否正常";

    @Override
    public void accept(Throwable e) throws Exception {
        if (e instanceof SocketTimeoutException) {
            Logger.t(TAG).e("onError: SocketTimeoutException----" + SOCKETTIMEOUTEXCEPTION);
            error = SOCKETTIMEOUTEXCEPTION;
        } else if (e instanceof ConnectException) {
            Logger.t(TAG).e("onError: ConnectException-----" + CONNECTEXCEPTION);
            error = CONNECTEXCEPTION;
        } else if (e instanceof UnknownHostException) {
            if (!NetworkUtil.getConnectivityStatus(EboxApp.getInstance())){
                Logger.t(TAG).e("onError: UnknownHostException-----" + USER_NETWORKEXCEPTION);
                error = USER_NETWORKEXCEPTION;
            }else{
                Logger.t(TAG).e("onError: UnknownHostException-----" + SERVICE_NETWORKEXCEPTION);
                error = SERVICE_NETWORKEXCEPTION;
            }
        } else {
            Logger.t(TAG).e("onError:----" +e.getMessage());
            error = "未知异常";
        }
    }
}
