package com.hug.commons.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.baohan.ebox.EboxApp;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.MaskTransformation;


/**
 * The type Glide loader.
 */
public class GlideLoader {

    /**
     * The constant normalOption.
     */
    public static final RequestOptions normalOption = new RequestOptions()
            .dontAnimate();

    public static final RequestOptions cropOption = new RequestOptions()
            .transform(new CircleCrop())
            .dontAnimate();


    public static void objNormal(Object uri,ImageView imageView){
        if (uri instanceof String){
            normal((String)uri,imageView);
        }else{
            normal((int)uri,imageView);
        }
    }
    /**
     * Normal.
     *
     * @param url       the url
     * @param imageView the image view
     */
    public static void normal(String url, ImageView imageView) {
        Glide.with(EboxApp.getInstance()).load(url)
                .apply(normalOption)
                .transition(new GenericTransitionOptions<Drawable>())
                .into(imageView);
    }

    /**
     * Normal.
     *
     * @param res       the res
     * @param imageView the image view
     */
    public static void normal(int res, ImageView imageView) {
        Glide.with(EboxApp.getInstance()).load(res)
                .apply(normalOption)
                .transition(new GenericTransitionOptions<Drawable>())
                .into(imageView);
    }

    public static void avatar(String url,ImageView imageView){
        Glide.with(EboxApp.getInstance()).load(url)
                .apply(cropOption)
                .transition(new GenericTransitionOptions<Drawable>())
                .into(imageView);
    }

    /**
     * Mask.
     *
     * @param imageRes  the image res
     * @param imageView the image view
     * @param maskId    the mask id
     */
    public static void mask(int imageRes,ImageView imageView,int maskId){
        RequestOptions maskOption = new RequestOptions()
                .centerCrop()
                .transform(new MaskTransformation(maskId));
        loadMaskImage(imageRes,imageView,maskOption);
    }

    /**
     * Mask.
     *
     * @param imageRes  the image res
     * @param imageView the image view
     * @param maskId    the mask id
     */
    public static void mask(String imageRes,ImageView imageView,int maskId){
        RequestOptions maskOption = new RequestOptions()
                .centerCrop()
                .transform(new MaskTransformation(maskId));
        loadMaskImage(imageRes,imageView,maskOption);
    }

    /**
     * Load mask image.
     *
     * @param imageRes  the image res
     * @param imageView the image view
     * @param options   the options
     */
    public static void loadMaskImage(int imageRes,ImageView imageView,RequestOptions options){
        Glide.with(EboxApp.getInstance()).load(imageRes)
                .apply(options)
                .transition(new GenericTransitionOptions<Drawable>())
                .into(imageView);
    }

    /**
     * Load mask image.
     *
     * @param imageRes  the image res
     * @param imageView the image view
     * @param options   the options
     */
    public static void loadMaskImage(String imageRes,ImageView imageView,RequestOptions options){
        Glide.with(EboxApp.getInstance()).load(imageRes)
                .apply(options)
                .transition(new GenericTransitionOptions<Drawable>())
                .into(imageView);
    }
}
