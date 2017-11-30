package com.Deals.Offers.FreePromos;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Priya on 29-05-2017.
 */

public class ExpandListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandListAdapter(Context context, List<String> listDataHeader,
                             HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        ImageView childicon = (ImageView)convertView.findViewById(R.id.child_icon);
        if(childText.equals("Electronics")){
            childicon.setImageResource(R.drawable.electronics);
        }
        else if(childText.equals("Fashion")){
            childicon.setImageResource(R.drawable.fashion);
        }
        else if(childText.equals("Appliances")){
            childicon.setImageResource(R.drawable.appliances);
        }
        else if(childText.equals("Recharge")){
            childicon.setImageResource(R.drawable.icons8_wallet);
        }
        else if(childText.equals("Grocery&Pantry")){
            childicon.setImageResource(R.drawable.icons_food);
        }
        else if(childText.equals("Travel")){
            childicon.setImageResource(R.drawable.travel);
        }
        else if(childText.equals("Others")){
            childicon.setImageResource(R.drawable.others);
        }
        else if(childText.equals("Home")){
            childicon.setImageResource(R.drawable.home);
        }
        else if(childText.equals("Movies")){
            childicon.setImageResource(R.drawable.movie);
        }
        else if(childText.equals("Food")){
            childicon.setImageResource(R.drawable.food);
        }
        txtListChild.setText(childText);
        LinearLayout.LayoutParams tx = new LinearLayout.LayoutParams(55,55);
        tx.setMargins(30,20,20,20);
        childicon.setLayoutParams(tx);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }}

