package com.hug.commons.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baohan.ebox.EboxApp;
import com.baohan.ebox.R;
import com.baohan.ebox.utils.ToastUtil;
import com.baohan.ebox.widget.dialog.ConfirmDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by HStan on 2017/10/9.
 *
 * @param <T> the type parameter
 */
public abstract class BaseActivity<T extends BasePresenterImpl> extends AppCompatActivity implements BaseView<T> {

    protected static final String TAG_MENU = "menu";
    /**
     * The constant NONE.
     */
    public static final int NONE = 0;
    /**
     * The constant CLOSE.
     */
    public static final int CLOSE = 1001;
    /**
     * The constant BACK.
     */
    public static final int BACK = 1002;

    /**
     * The Binder.
     */
    protected Unbinder binder;

    /**
     * The M presenter.
     */
    protected T mPresenter;

    /**
     * The M toolbar.
     */
    protected Toolbar mToolbar;
    /**
     * The M toolbar title.
     */
    protected TextView mToolbarTitle;
    /**
     * The M toolbar menu.
     */
    protected ImageView mToolbarMenu;

//    protected LoadingProgress progress;

    private ConfirmDialogFragment confirmDialog = new ConfirmDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setScreenOrientation(true);
        setContentView(getLayoutId());
        initPresenter();
        EboxApp.getInstance().addActivity(this);
        binder = ButterKnife.bind(this);
//        progress = new LoadingProgress(this);
        if (enableToolbar()) {
            initToolbar();
        }
        init();
        bind();
    }

    /**
     * @param portrait 是否垂直
     */
    protected void setScreenOrientation(boolean portrait) {
        if (portrait) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * Init presenter.
     */
    protected abstract void initPresenter();

    /**
     * Init toolbar.
     */
    protected void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbarTitle);
        setSupportActionBar(mToolbar);
        setTitle("");
        if (enableToolbarBackUp() != 0) {
            if (enableToolbarBackUp() == BACK) {
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            } else if (enableToolbarBackUp() == CLOSE) {
                mToolbar.setNavigationIcon(R.drawable.ic_close_white);
            }
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (enableToolbarMenu()) {
            mToolbarMenu = findViewById(R.id.toolbarMenu);
            mToolbarMenu.setVisibility(View.VISIBLE);
//            GlideLoader.normal(R.mipmap.ic_logo_menu, mToolbarMenu);
            mToolbarTitle.setTag(TAG_MENU);
//            mToolbarMenu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

    }

    /**
     * Enable toolbar menu boolean.
     *
     * @return the boolean
     */
    protected abstract boolean enableToolbarMenu();

    /**
     * Set toolbar title.
     *
     * @param title the title
     */
    protected void setToolbarTitle(String title) {
        if (mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }
    }

    /**
     * Set toolbar title.
     *
     * @param titleRes the title in Strint.xml
     */
    protected void setToolbarTitle(int titleRes) {
        if (mToolbarTitle != null){
            mToolbarTitle.setText(getString(titleRes));
        }
    }

    @Override
    public void setPresenter(T presenter) {
        if (presenter != null) {
//            Log.d("Presenter",presenter.getClass()+"");
        }
    }

    @Override
    public void showLoading() {
//        if (progress != null){
//            progress.show();Z
//        }
    }

    @Override
    public void hideLoading() {
//        if (progress != null){
//            progress.dismiss();
//        }
    }

    @Override
    public void showError(String e) {
        showToast(e);
    }

    @Override
    protected void onDestroy() {
        binder.unbind();
        reseizeRes();
        if (mPresenter != null) {
//            mPresenter.unsubscribe();
        }
        super.onDestroy();
    }

    /**
     * Reseize res.
     */
    protected void reseizeRes() {

    }

    /**
     * Enable toolbar back up boolean.
     *
     * @return the boolean
     */
    protected abstract int enableToolbarBackUp();

    /**
     * Enable toolbar boolean.
     *
     * @return the boolean
     */
    protected abstract boolean enableToolbar();

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    /**
     * Init.
     */
    protected abstract void init();

    /**
     * Bind.
     */
    protected abstract void bind();

    /**
     * Show toast.
     *
     * @param msg the msg
     */
    protected void showToast(String msg) {
        ToastUtil.getInstance().show(msg);
    }

    /**
     * Show tip dialog.
     *
     * @param msg the msg
     */
    protected void showTipDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * Show confirm dialog.
     *
     * @param msg    the msg
     * @param btname the btname
     * @param tag    the tag
     */
    protected void showConfirmDialog(String msg, String btname, String tag) {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDialogFragment();
        }
        confirmDialog.setArg(msg, btname).show(getSupportFragmentManager(), tag);
    }


    /**
     * 初始化recyclerview （LinearlayoutManage）
     **/
    protected void initRecyclerViewWithVerDivider(RecyclerView recyclerView, int dividerDrawable) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(dividerDrawable));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * 初始化recyclerview （GridLayoutManager）
     **/
    protected void initGridRecyclerView(RecyclerView recyclerView, int count) {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), count));
        recyclerView.setHasFixedSize(true);
    }

//    protected <K extends BaseQuickAdapter> void setEmptyView(K adapter, String msg){
//        View view = getLayoutInflater().inflate(R.layout.empty_view,null);
//        TextView tv = (TextView) view.findViewById(R.id.empty_text);
//        ImageView icon = (ImageView) view.findViewById(R.id.empty_icon);
//        GlideLoader.normal(R.drawable.ic_empty,icon);
//        tv.setText(msg);
//        adapter.setEmptyView(view);
//    }

//    protected <K extends BaseQuickAdapter> void setEmptyView(K adapter, @DrawableRes int emptyIcon, String msg){
//        View view = getLayoutInflater().inflate(R.layout.empty_view,null);
//        TextView tv = (TextView) view.findViewById(R.id.empty_text);
//        ImageView icon = (ImageView) view.findViewById(R.id.empty_icon);
//        GlideLoader.normal(emptyIcon,icon);
//        tv.setText(msg);
//        adapter.setEmptyView(view);
//    }

    /**
     * Set intent.
     *
     * @param activity the activity
     */
    protected void setIntent(Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }

    /**
     * Set intent for result.
     *
     * @param requestCode the request code
     * @param activity    the activity
     */
    protected void setIntentForResult(int requestCode, Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Set intent with bundle.
     *
     * @param activity the activity
     * @param bundle   the bundle
     */
    protected void setIntentWithBundle(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Post event.
     *
     * @param event the event
     */
    protected void postEvent(Object event) {
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
