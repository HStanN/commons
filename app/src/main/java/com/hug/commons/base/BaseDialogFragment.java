package com.hug.commons.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baohan.ebox.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Stephon.hu@Gmail.com
 * <p>
 * Description:
 * <p>
 * Modify By:
 */
public abstract class BaseDialogFragment extends DialogFragment {

    Unbinder unbinder;

    public static final int NO_ANIM = 0;

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, dm.heightPixels);
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 5.0f;
//        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        if (animStyle() != NO_ANIM){
            getDialog().getWindow().setWindowAnimations(animStyle());
        }
        unbinder = ButterKnife.bind(this, rootView);
        init();
        bind();
        return rootView;
    }

    protected abstract void bind();

    protected abstract void init();

    protected abstract int getLayoutId();

    protected abstract int animStyle();

    /**
     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Bh_dialog);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    
}
