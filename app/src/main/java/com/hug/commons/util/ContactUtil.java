package com.hug.commons.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * @author Stephon.hu@Gmail.com
 * <p>
 * Description:
 * <p>
 * Modify By:
 */
public class ContactUtil {


    public static void getContactPeople(final Activity context, final int requestCode) {
        AndPermission.with(context)
                .permission(Permission.READ_CONTACTS)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.PICK");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setType("vnd.android.cursor.dir/phone_v2");
                        context.startActivityForResult(intent, requestCode);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        final SettingService settingService = AndPermission.permissionSetting(context);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("您已拒绝该权限");
                        builder.setMessage("如果要继续使用，需要进入 设置-权限管理 中打开联系人权限");
                        builder.setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingService.execute();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingService.cancel();
                            }
                        });
                        builder.create().show();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                    }
                }).start();
    }

    public static void getContactPeople(final Fragment context,final int requestCode) {
        AndPermission.with(context)
                .permission(Permission.READ_CONTACTS)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.PICK");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setType("vnd.android.cursor.dir/phone_v2");
                        context.startActivityForResult(intent, requestCode);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        final SettingService settingService = AndPermission.permissionSetting(context);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                        builder.setTitle("您已拒绝该权限");
                        builder.setMessage("如果要继续使用，需要进入 设置-权限管理 中打开联系人权限");
                        builder.setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingService.execute();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingService.cancel();
                            }
                        });
                        builder.create().show();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                    }
                }).start();
    }

    /**
     * 解析联系人
     */
    public static String[] getPhoneContacts(Uri uri, ContentResolver contentResolver) {
        String[] contact = new String[2];
        ContentResolver cr = contentResolver;
        //取得联系人中第一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
//            取得电话号码
            contact[1] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }
}
