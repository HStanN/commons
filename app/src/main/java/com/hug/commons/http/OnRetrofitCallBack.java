package com.hug.commons.http;

import com.baohan.ebox.model.BaseResponse;

/**
 * Created by HStan on 2017/10/11.
 */

public interface OnRetrofitCallBack<T extends BaseResponse> {
    void onSuccess(T response);
    void onFailure(String e);
}
