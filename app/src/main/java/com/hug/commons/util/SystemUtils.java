package com.hug.commons.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by HStan on 2017/10/11.
 */
public class SystemUtils {


    /**
     * Get android id string.
     *
     * @param context the context
     * @return the string
     */
    public static String getAndroidId(Context context){
        SharedPreferences sp = context.getSharedPreferences("android_id",Context.MODE_PRIVATE);
        String android_id = sp.getString("id","");
        if (android_id.equals("")){
            android_id = createAndroidId(context);
        }
        return android_id;
    }

    private static String createAndroidId(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if ("9774d56d682e549c".equals(android_id)) {
            Random random = new Random();
            String randomId = Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt());
            return new UUID(randomId.hashCode(), getBuildInfo().hashCode()).toString();
        }
        return android_id;
    }

    /**
     * 获取Build的部分信息
     *
     * @return
     */
    public static String getBuildInfo() {
        //这里选用了几个不会随系统更新而改变的值
        StringBuffer buildSB = new StringBuffer();
        buildSB.append(Build.BRAND).append("/");
        buildSB.append(Build.PRODUCT).append("/");
        buildSB.append(Build.DEVICE).append("/");
        buildSB.append(Build.ID).append("/");
        buildSB.append(Build.VERSION.INCREMENTAL);
        return buildSB.toString();
//        return Build.FINGERPRINT;
    }

    /**
     * Get current date string.
     *
     * @return the string
     */
    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        builder.append("-");
        if (monthOfYear < 10){
            builder.append(0);
        }
        builder.append(monthOfYear);
        builder.append("-");
        if (dayOfMonth < 10){
            builder.append(0);
        }
        builder.append(dayOfMonth);
        return builder.toString();
    }

    /**
     * Md 5 string.
     *
     * @param s the s
     * @return the string
     */
    public static String MD5(String s) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            //使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
