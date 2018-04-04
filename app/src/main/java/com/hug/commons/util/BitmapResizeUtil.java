package com.hug.commons.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * The type Bitmap resize util.
 *
 * @author Stephon.hu @Gmail.com <p> Description: <p> Modify By:
 */
public class BitmapResizeUtil {

    /**
     * Resize.
     *
     * @param percent the percent
     * @param bitmap  the bitmap
     */
    public static Bitmap resize(float percent,Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(percent, percent);
        int newWidth = (int) (width * percent);
        int newHeight = (int) (height * percent);
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
