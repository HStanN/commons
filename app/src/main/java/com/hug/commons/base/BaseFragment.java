package com.hug.commons.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baohan.ebox.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The type Base fragment.
 *
 * @param <T> the type parameter
 */
public abstract class BaseFragment<T extends BasePresenterImpl> extends Fragment implements BaseView<T> {

    /**
     * The M presenter.
     */
    protected T mPresenter;

    /**
     * The Unbinder.
     */
    protected Unbinder unbinder;

    /**
     * Init presenter.
     */
    protected abstract void initPresenter();

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initPresenter();
        unbinder = ButterKnife.bind(this, view);
        init();
        bind();
        return view;
    }

    protected abstract void bind();

    protected abstract void init();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(T presenter) {

    }

    @Override
    public void showError(String e) {
        showToast(e);
    }

    /**
     * Show toast.
     *
     * @param msg the msg
     */
    protected void showToast(String msg) {
        ToastUtil.getInstance().show(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    protected void setIntent(Class<?> activity){
        Intent intent = new Intent(getActivity(),activity);
        getActivity().startActivity(intent);
    }

    /**
     * Post event.
     *
     * @param event the event
     */
    protected void postEvent(Object event){
        EventBus.getDefault().post(event);
    }

    /**
     * Post event delay.
     *
     * @param event the event
     * @param delay the delay
     */
    protected void postEventDelay(final Object event, long delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(event);
            }
        }, delay);
    }

}
