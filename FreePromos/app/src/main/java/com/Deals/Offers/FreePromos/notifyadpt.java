package com.Deals.Offers.FreePromos;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunny on 6/25/2017.
 */

public class notifyadpt extends ArrayAdapter<HashMap<String,String>> {

    private Context t;

    public notifyadpt(Activity context, ArrayList<HashMap<String,String>> notify_list){
        super(context,0,notify_list);
        t=context;
    }
    @Nullable
    @Override
    public HashMap<String, String> getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View l=convertView;
        if(l==null){
            l = LayoutInflater.from(getContext()).inflate(
                    R.layout.notification_panel, parent, false);
        }
        TextView desc_id=(TextView) l.findViewById(R.id.not_desc);
        TextView title_id=(TextView) l.findViewById(R.id.not_title);
        TextView timenew_id=(TextView) l.findViewById(R.id.time_id);

        final HashMap<String,String> itemnew=getItem(position);

        if(itemnew.get("t")!=null){//title
            title_id.setText(itemnew.get("t"));
        }
        if(itemnew.get("n")!=null){//description
            desc_id.setText(itemnew.get("n"));
        }
        if(itemnew.get("i")!=null){//time_of _notification_when posted
            TimeAgo timeAgo=new TimeAgo();
            String settime=timeAgo.counting(Long.parseLong(itemnew.get("i")));
            timenew_id.setText(settime);
        }
        return  l;
    }
}
