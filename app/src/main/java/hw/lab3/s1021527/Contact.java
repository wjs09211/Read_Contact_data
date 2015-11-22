package hw.lab3.s1021527;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 睡睡 on 2015/11/21.
 */
public class Contact {
    private ArrayList<ContactItem> itemlist = new ArrayList<ContactItem>();
    private Context context;
    public Contact(Context c){ context = c;}

    public void query() {
        //通訊錄的位置
        Uri uri = ContactsContract.Contacts.CONTENT_URI ;
        ContentResolver provider = context.getContentResolver() ;
        //搜尋所有通訊錄的資料
        Cursor cursor = provider.query(uri, null, null, null, ContactsContract.Contacts.DISPLAY_NAME+","+ContactsContract.Contacts._ID) ;
        while(cursor.moveToNext()){
            ContactItem item = new ContactItem();
            //ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)) ;
            item.setID(contactId);
            Log.e("contactId", contactId);
            //name
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) ;
            item.setName(name);
            Log.e("name", name);

            //搜尋電話號碼 SELECT * FORM Phone.CONTENT_URI WHERE Phone.CONTACT_ID = contactId
            Cursor phoneCursor = provider.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId + " AND " +
                            ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE " + "'%%'", null, null) ;
            if(phoneCursor.moveToNext()){
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
                item.setPhoneNumber(phoneNumber);
                Log.e("phoneNumber", phoneNumber);
            }
            //關閉搜尋
            phoneCursor.close() ;
            //搜尋email SELECT * FORM Email.CONTENT_URI WHERE Email.CONTACT_ID = contactId
            Cursor emailCursor = provider.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+contactId, null, null) ;
            if(emailCursor.moveToNext()){
                String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)) ;
                item.setEmail(email);
                Log.e("email", email);
            }
            emailCursor.close() ;
            itemlist.add(item);
        }
        cursor.close();
    }
    public ArrayList<ContactItem> getItemlist()
    {
     return itemlist;
    }
}
