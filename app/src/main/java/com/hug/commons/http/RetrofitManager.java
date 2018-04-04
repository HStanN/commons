package com.hug.commons.http;

import android.content.Context;

import com.baohan.ebox.EboxApp;
import com.baohan.ebox.constans.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit manager.
 */
public class RetrofitManager {
    private static volatile RetrofitManager instance = null;

    //超时时间
    private static final long DEFAULT_TIMEOUT = 15;

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    private Retrofit retrofit;
    private RetrofitManager() {
        init();
    }

    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException { //添加公共参数
                        Context context = EboxApp.getInstance();
                        String params = CommParams.getStringParams(context);
                        String url;
                        if (chain.request().url().toString().contains("?")){
                            url = chain.request().url().toString() + "&" +params;
                        }else{
                            url = chain.request().url().toString() + "?" +params;
                        }
                        Request request = chain.request().newBuilder().url(url).build();  //重新构建请求
//                    Log.d("http_post_body",chain.request().body().toString());
                        return chain.proceed(request);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RetrofitManager getInstance() {
        if (instance == null){
            synchronized (RetrofitManager.class){
                if (instance == null){
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    /**
     * Get api t.
     *
     * @param <T>      the type parameter
     * @param apiClass the api class
     * @return the t
     */
    public <T> T getApi(Class<T> apiClass){
        return retrofit.create(apiClass);
    }

}
