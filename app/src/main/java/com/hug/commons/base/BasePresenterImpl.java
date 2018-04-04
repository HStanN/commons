package com.hug.commons.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by HStan on 2017/10/10.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    protected CompositeDisposable disposables = new CompositeDisposable();

    protected V v;

    public BasePresenterImpl(V v) {
        this.v = v;
        v.setPresenter(this);
    }

    @Override
    public void detachView() {
        if (disposables.size() > 0) {
            disposables.clear();
        }
    }
}
