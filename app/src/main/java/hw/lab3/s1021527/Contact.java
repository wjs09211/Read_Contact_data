package hw.lab3.s1021527;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by 睡睡 on 2015/11/21.
 */
public class Contact {
    private ArrayList<ContactItem> itemlist = new ArrayList<ContactItem>();
    private Context context;
    public Contact(Context c){ context = c;}

    public int query( String qName, String qPhonenumber, String qEmail) {
        Date start = new Date();
        itemlist.clear();
        //通訊錄的位置
        Uri uri = ContactsContract.Contacts.CONTENT_URI ;
        ContentResolver provider = context.getContentResolver() ;
        //搜尋所有通訊錄的資料
        //SELECT * FORM Contacts.DISPLAY_NAME WHERE Contacts.DISPLAY_NAME LIKE %qName%
        Cursor cursor = provider.query(uri, null, ContactsContract.Contacts.DISPLAY_NAME + " LIKE " +"'%"+ qName +"%'", null, ContactsContract.Contacts.DISPLAY_NAME) ;
        while(cursor.moveToNext()){
            ContactItem item = new ContactItem();
            //ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)) ;
            item.setID(contactId);
            //Log.e("contactId", contactId);
            //name
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) ;
            item.setName(name);
            //Log.e("name", name);

            //搜尋電話號碼 SELECT * FORM Phone.CONTENT_URI WHERE Phone.CONTACT_ID = contactId　AND .Phone.NUMBER LIKE %qPhonenumber%
            Cursor phoneCursor = provider.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId + " AND " +
                            ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE " + "'%"+qPhonenumber+"%'", null, null) ;
            if(phoneCursor.moveToNext()){
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
                item.setPhoneNumber(phoneNumber);
                //Log.e("phoneNumber", phoneNumber);
            }
            //關閉搜尋
            phoneCursor.close() ;
            //搜尋email SELECT * FORM Email.CONTENT_URI WHERE Email.CONTACT_ID = contactId
            Cursor emailCursor = provider.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId + " AND " +
                            ContactsContract.CommonDataKinds.Email.DATA + " LIKE " + "'%"+qEmail+"%'", null, null) ;
            if(emailCursor.moveToNext()){
                String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)) ;
                item.setEmail(email);
                //Log.e("email", email);
            }
            emailCursor.close() ;
            if( !qPhonenumber.equals("") && item.getPhoneNumber().equals("") )
                continue;
            else if( !qEmail.equals("") && item.getEmail().equals("") )
                continue;
            itemlist.add(item);
        }
        cursor.close();
        Date end = new Date();
        return (int)(end.getTime() - start.getTime());
    }
    public void sort( final int mode )
    {
        Collections.sort(itemlist, new Comparator<ContactItem>() {
            @Override
            public int compare(ContactItem a, ContactItem b) {
                if (mode == 0)
                    return a.getName().compareTo(b.getName());
                else if (mode == 1)
                    return a.getPhoneNumber().compareTo(b.getPhoneNumber());
                else if (mode == 2)
                    return a.getEmail().compareTo(b.getEmail());
                else if (mode == 3)
                    return a.getAddress().compareTo(b.getAddress());
                else if (mode == 4)
                    return a.getName().charAt(0) - b.getName().charAt(0);
                else if (mode == 5){
                    if (a.getName().charAt(0) == b.getName().charAt(0))
                        return a.getAddress().compareTo(b.getAddress());
                    else
                        return 0;
                }
                else if (mode == 6){
                    return b.getTimes() - a.getTimes();
                }
                else if (mode == 7){
                    return b.getDuration() - a.getDuration();
                }
                else
                    return a.getName().compareTo(b.getName());
            }
        });
    }
    public void sortAddress()
    {
        for( int i = 0 ; i < itemlist.size() ; i++ ){
            int pos = itemlist.get(i).getEmail().indexOf("@");
            if( pos == -1 )
                itemlist.get(i).setAddress("");
            else {
                try {
                    itemlist.get(i).setAddress(itemlist.get(i).getEmail().substring(pos + 1, itemlist.get(i).getEmail().length()));
                } catch (Exception e){
                    itemlist.get(i).setAddress("");
                }
            }
        }
        sort(3);
    }
    public void sortNameEmail(){
        sort(4);
        sort(5);
    }
    public ArrayList<ContactItem> getItemlist()
    {
     return itemlist;
    }
}
