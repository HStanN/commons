package com.hug.commons.util;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baohan.ebox.EboxApp;
import com.baohan.ebox.R;

/**
 * The type Toast util.
 */
public class ToastUtil {
    private static ToastUtil instance = null;

    private static Toast toast;

    private ToastUtil(){
        toast = new Toast(EboxApp.getInstance());
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(EboxApp.getInstance()).inflate(R.layout.toast_layout,null);
        toast.setView(view);
    }

    /**
     * Get instance toast util.
     *
     * @return the toast util
     */
    public static ToastUtil getInstance(){
        if (instance == null){
            synchronized (ToastUtil.class){
                if (instance == null){
                    instance = new ToastUtil();
                }
            }
        }
        return instance;
    }

    /**
     * Show.
     *
     * @param msg the msg
     */
    public void show(String msg){
        toast.setText(msg);
        toast.show();
    }
}
