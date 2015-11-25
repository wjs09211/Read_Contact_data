package hw.lab3.s1021527;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 睡睡 on 2015/11/25.
 */
public class MyAdapter2 extends BaseAdapter {
    private Context context;
    private List<ContactItem> rowItems;
    private int mode = 0;
    public MyAdapter2(Context context, List<ContactItem> items, int mode) {
        this.context = context;
        this.rowItems = items;
        this.mode = mode;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtTimes;
        TextView txtEmail;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item2, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.t_name);
            holder.txtTimes = (TextView) convertView.findViewById(R.id.t_times);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.t_email);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactItem rowItem = (ContactItem) getItem(position);

        holder.txtName.setText(rowItem.getName()+"  ");
        if(mode == 1)
            holder.txtTimes.setText(rowItem.getTimes()+"次 ");
        else
            holder.txtTimes.setText(rowItem.getDuration()+"秒 ");
        holder.txtEmail.setText(rowItem.getEmail()+" ");

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
