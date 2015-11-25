package hw.lab3.s1021527;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private Contact contact;
    private CallRecord callrecord;
    private ArrayList<ContactItem> list = new ArrayList<ContactItem>();
    private TextView text_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_load = (TextView)findViewById(R.id.text_load2);
        Bundle bundle = getIntent().getExtras();
        final int mode = bundle.getInt("mode");
        if( mode == 1) {
            new Thread() {
                @Override
                public void run() {     //使用網路相關的功能要用thread android 4.0以上都要這樣，為了安全性
                    contact = new Contact(getApplicationContext());
                    contact.query("", "", "");
                    callrecord = new CallRecord(getApplicationContext());
                    callrecord.query(mode);
                    ArrayList<CallRecorditem> recordList = callrecord.getitemlist();
                    for (int i = 0; i < recordList.size(); i++) {
                        for (int j = 0; j < contact.getItemlist().size(); j++) {
                            if (recordList.get(i).getPhoneNum().equals(contact.getItemlist().get(j).getPhoneNumber())) {
                                contact.getItemlist().get(j).addTimes();
                                break;
                            }
                        }
                    }
                    contact.sort(6);
                    for( int i = 0 ; i < contact.getItemlist().size() ; i++ ) {
                        list.add(contact.getItemlist().get(i));
                    }
                    Message msgg = new Message();//component要交給Handler處理
                    msgg.what = 1;
                    mHandler.sendMessage(msgg);
                }
            }.start();
        }
        else{
            new Thread() {
                @Override
                public void run() {     //使用網路相關的功能要用thread android 4.0以上都要這樣，為了安全性
                    contact = new Contact(getApplicationContext());
                    contact.query("", "", "");
                    callrecord = new CallRecord(getApplicationContext());
                    callrecord.query(mode);
                    ArrayList<CallRecorditem> recordList = callrecord.getitemlist();
                    for (int i = 0; i < recordList.size(); i++) {
                        for (int j = 0; j < contact.getItemlist().size(); j++) {
                            if (recordList.get(i).getPhoneNum().equals(contact.getItemlist().get(j).getPhoneNumber())) {
                                contact.getItemlist().get(j).addduration(Integer.parseInt(recordList.get(i).getDuration()));
                                break;
                            }
                        }
                    }
                    contact.sort(7);
                    for( int i = 0 ; i < contact.getItemlist().size() ; i++ ) {
                        list.add(contact.getItemlist().get(i));
                    }
                    Message msgg = new Message();//component要交給Handler處理
                    msgg.what = 2;
                    mHandler.sendMessage(msgg);
                }
            }.start();
        }
    }
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msgg) {
            switch(msgg.what){
                case 1:
                    updataListView(1);
                    break;
                case 2:
                    updataListView(2);
                    break;
            }
            super.handleMessage(msgg);
        }
    };
    public void updataListView(int i) {
        ListView listView;
        listView = (ListView) findViewById(R.id.listView2);
        MyAdapter2 adapter = new MyAdapter2(this, list, i);
        listView.setAdapter(adapter);
        text_load.setVisibility(View.GONE);
    }
}
