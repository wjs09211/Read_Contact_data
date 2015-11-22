package hw.lab3.s1021527;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 睡睡 on 2015/11/22.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<ContactItem> rowItems;

    public MyAdapter(Context context, List<ContactItem> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtID;
        TextView txtName;
        TextView txtPhone;
        TextView txtEmail;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtID = (TextView) convertView.findViewById(R.id.Txt_ID);
            holder.txtName = (TextView) convertView.findViewById(R.id.Txt_Name);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.Txt_Phone);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.Txt_Email);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactItem rowItem = (ContactItem) getItem(position);

        holder.txtID.setText(rowItem.getID());
        holder.txtName.setText(rowItem.getName());
        holder.txtPhone.setText(rowItem.getPhoneNumber());
        holder.txtEmail.setText(rowItem.getEmail());

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
