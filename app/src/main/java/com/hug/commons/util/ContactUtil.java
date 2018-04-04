package com.hug.commons.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

/**
 * @author Stephon.hu@Gmail.com
 * <p>
 * Description:
 * <p>
 * Modify By:
 */
public class ContactUtil {

    public static String url = "content://contacts/people";

    public static void getContactPeople (Activity context, int requestCode){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_PICK,uri);
        context.startActivityForResult(intent,requestCode);
    }

    public static void getContactPeople (Fragment context, int requestCode){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_PICK,uri);
        context.startActivityForResult(intent,requestCode);
    }

    /**
     * 解析联系人
     *
     * */
    public static String[] getPhoneContacts(Uri uri,ContentResolver contentResolver){
        String[] contact=new String[2];
        ContentResolver cr = contentResolver;
        //取得联系人中第一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        }
        else
        {
            return null;
        }
        return contact;
    }
}
