package hw.lab3.s1021527;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        contact = new Contact(getApplicationContext());
        int time = contact.query("","","");
        showToast("query time: "+time+"ms");
        updataListView();
        //listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Dialog d = onCreateDialogAddGroup();
            d.show();
        } else if (id == R.id.nav_name) {
            contact.sort(0);
            updataListView();
        } else if (id == R.id.nav_Phone) {
            contact.sort(1);
            updataListView();
        }
        else if (id == R.id.nav_Email) {
            contact.sort(2);
            updataListView();
        }
        else if (id == R.id.nav_EmailAdr) {
            contact.sortAddress();
            updataListView();
        }
        else if( id == R.id.nav_circle){
            openPieChart();
        }
        else if ( id == R.id.nav_bar_Chart){
            openBarChart();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updataListView() {
        ListView listView;
        listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, contact.getItemlist());
        listView.setAdapter(adapter);
    }
    public Dialog onCreateDialogAddGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("搜尋");
        final View inputView = inflater.inflate(R.layout.edit_layout, null);
        final EditText search_name = (EditText)inputView.findViewById(R.id.search_name);
        final EditText search_phone = (EditText)inputView.findViewById(R.id.search_phone);
        final EditText search_email = (EditText)inputView.findViewById(R.id.search_email);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inputView)
                // Add action buttons
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        //String s = AddGroup.getText().toString();
                        //feedMenu.addSubMenu(AddGroup.getEditableText().toString());
                        int time = contact.query(search_name.getText().toString(), search_phone.getText().toString(), search_email.getText().toString());
                        showToast("query time: "+time+"ms");
                        updataListView();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
    private void openPieChart() {
        ArrayList<ContactItem> itmeList = contact.getItemlist();
        // Pie Chart Section Names
        ArrayList<String> firstName = new ArrayList<String>();
        for( int i = 0 ; i < itmeList.size() ; i++ ){
            if(!firstName.contains(itmeList.get(i).getName().charAt(0) + "")){
                firstName.add(itmeList.get(i).getName().charAt(0) + "");
            }
        }

        int [] nameConut = new int[firstName.size()];
        for( int i = 0 ; i < itmeList.size() ; i++ ) {
            for (int j = 0; j < firstName.size(); j++) {
                if(itmeList.get(i).getName().charAt(0) == firstName.get(j).charAt(0)) {
                    nameConut[j]++;
                    break;
                }
            }
        }
        int all = 0;
        for (int j = 0; j < firstName.size(); j++) {
            all += nameConut[j];
        }
        // Pie Chart Section Names
       /* String[] code = new String[] {
                "Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
                "IceCream Sandwich", "Jelly Bean"
        };*/
        // Pie Chart Section Value
        double[] distribution = new double[firstName.size()] ;
        for (int i = 0; i < firstName.size(); i++) {
            distribution[i] = (double)nameConut[i] / all;
        }
        // Color of each Pie Chart Sections
        int[] colors = new int[firstName.size()];
        Random ran = new Random();
        for( int i = 0 ; i < firstName.size() ; i++ ){
            colors[i] = Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
        }
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(firstName.get(i), distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);

            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
        defaultRenderer.setChartTitleTextSize(30);
        defaultRenderer.setLabelsTextSize(30);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setZoomButtonsVisible(true);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        startActivity(intent);
    }
    private void openBarChart(){
        ArrayList<ContactItem> itmeList = contact.getItemlist();
        // Pie Chart Section Names
        ArrayList<String> phoneHead = new ArrayList<String>();
        for( int i = 0 ; i < itmeList.size() ; i++ ){
            if(itmeList.get(i).getPhoneNumber().length() >= 4) {
                if (!phoneHead.contains(itmeList.get(i).getPhoneNumber().substring(0, 4))) {
                    Log.e("ddddddd", itmeList.get(i).getPhoneNumber().substring(0, 4));
                    phoneHead.add(itmeList.get(i).getPhoneNumber().substring(0, 4));
                }
            }
        }

        int [] phoneConut = new int[phoneHead.size()];
        for( int i = 0 ; i < itmeList.size() ; i++ ) {
            for (int j = 0; j < phoneHead.size(); j++) {
                if(itmeList.get(i).getPhoneNumber().length() >= 4) {
                    if (itmeList.get(i).getPhoneNumber().substring(0, 4).equals(phoneHead.get(j).substring(0, 4))) {
                        phoneConut[j]++;
                        break;
                    }
                }
            }
        }

        // Creating an  XYSeries for Income
        XYSeries phoneSeries = new XYSeries("phone number");
        // Adding data to Income and Expense Series
        for(int i = 0 ; i < phoneConut.length ; i++){
            phoneSeries.add(i,phoneConut[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(phoneSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer phoneRenderer = new XYSeriesRenderer();
        phoneRenderer.setColor(Color.rgb(130, 130, 230));
        phoneRenderer.setFillPoints(true);
        phoneRenderer.setLineWidth(5);
        phoneRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("長條圖");
        multiRenderer.setXTitle("區碼");
        multiRenderer.setAxisTitleTextSize(30);
        multiRenderer.setLegendTextSize(30);
        multiRenderer.setLabelsTextSize(20);
        multiRenderer.setPointSize(30);
        multiRenderer.setChartTitleTextSize(30);
        multiRenderer.setYTitle("個數");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i = 0; i < phoneHead.size() ; i++){
            multiRenderer.addXTextLabel(i, phoneHead.get(i));
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(phoneRenderer);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, BarChart.Type.DEFAULT);

        // Start Activity
        startActivity(intent);

    }
    public void showToast(final String toast)   //把它寫成function  有些時候可以避免Bug 例如在thread裡使用他
    {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
