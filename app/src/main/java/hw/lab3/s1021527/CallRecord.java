package hw.lab3.s1021527;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 睡睡 on 2015/11/25.
 */
public class CallRecord {
    private Context context;
    private ArrayList<CallRecorditem> itemlist = new ArrayList<CallRecorditem>();
    public  CallRecord(Context c){ context = c;}
    public void query( int mode)
    {
        itemlist.clear();
        Uri uri = Uri.parse("content://call_log/calls");
        ContentResolver provider = context.getContentResolver() ;
        String count = "count";
        final Cursor cursor;
        if( mode == 1)
            cursor = provider.query(uri, null, CallLog.Calls.TYPE + "= 2 ", null, null);
        else
            cursor = provider.query(uri, null, null, null, null);

        while(cursor.moveToNext()) {
            CallRecorditem item = new CallRecorditem();
            /*String name = cursor.getString( cursor.getColumnIndex( CallLog.Calls.CACHED_NAME ) );
            Log.e("name", name);*/
            String phoneNumber = cursor.getString( cursor.getColumnIndex( CallLog.Calls.NUMBER ) );
            item.setPhoneNum(phoneNumber);
            String type = cursor.getString( cursor.getColumnIndex(CallLog.Calls.TYPE));
            item.setType(type);
            String date = cursor.getString( cursor.getColumnIndex( CallLog.Calls.DATE ) );
            item.setDate(date);
            String duration = cursor.getString( cursor.getColumnIndex( CallLog.Calls.DURATION ) );
            item.setDuration(duration);
            itemlist.add(item);
        }
        cursor.close();

    }

    public ArrayList<CallRecorditem> getitemlist()
    {
        return itemlist;
    }
}
