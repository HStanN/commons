package com.hug.commons.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The type Image util.
 *
 * @author Hu  <p> Description: <p> Modify By:
 */
public class ImageUtil {


    /**
     * Bitmap to Base64 string string.
     *
     * @param path the path
     * @return the string
     */
    public static String bitmap2base64String(String path){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Matrix matrix = new Matrix();
        float scanle = bitmap.getWidth() > bitmap.getHeight() ? 200f/bitmap.getWidth() : 200f/bitmap.getHeight();
        if (scanle < 1){
            matrix.postScale(scanle,scanle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b,Base64.DEFAULT);
    }

}
