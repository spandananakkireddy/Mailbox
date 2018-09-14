package com.example.manup.group32_inclass13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manup on 4/24/2018.
 */

public class InboxAdapter extends ArrayAdapter<Message> {

    List<Message> mdata;
    InboxActivity mContext;
    int mResource;


    public InboxAdapter(@NonNull InboxActivity context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mdata = objects;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Message message = mdata.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView tvname = (TextView) convertView.findViewById(R.id.tvname);
        TextView tvdate = (TextView) convertView.findViewById(R.id.tvdate);
        TextView tvmsg = (TextView) convertView.findViewById(R.id.tvmsg);

        ImageView imgread = (ImageView) convertView.findViewById(R.id.imgread);

        tvname.setText(message.getUserName());
        tvdate.setText(message.getDate());
        tvmsg.setText(message.getText());

        if (message.getIsRead() == false)
        {
            imgread.setImageResource(R.drawable.circle_blue);

        }else
            imgread.setImageResource(R.drawable.circle_grey);

        return convertView;
    }

}
