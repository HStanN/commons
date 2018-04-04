package com.hug.commons.base;

/**
 * Created by HStan on 2017/10/10.
 *
 * @param <T> the type parameter
 */
public interface BaseView<T> {

    /**
     * Sets presenter.
     *
     * @param presenter the presenter
     */
    void setPresenter(T presenter);

    /**
     * Show error.
     *
     * @param e the e
     */
    void showError(String e);

    /**
     * Show loading.
     */
    void showLoading();

    /**
     * Hide loading.
     */
    void hideLoading();
}
