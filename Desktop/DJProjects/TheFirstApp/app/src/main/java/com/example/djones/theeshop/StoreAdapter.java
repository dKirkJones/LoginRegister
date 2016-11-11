package com.example.djones.theeshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by djones on 10/7/16.
 */

public class StoreAdapter extends ArrayAdapter<Store>{

    Context mContext;
    int mLayoutResourceId;
    Store mData[] = null;


    public StoreAdapter(Context context, int resource, Store[] data) {
        super(context, resource, data);
        this.mContext= context;
        this.mLayoutResourceId =resource;
        this.mData = data;
    }

    @Nullable
    @Override
    public Store getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        StoreHolder holder = null;

        // if we currently don't have a row View to reuse...
        if (row == null){
            //inflate
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId,parent,false);

            holder = new StoreHolder();

            holder.nameView = (TextView) row.findViewById(R.id.nameTextView);
            holder.addressView = (TextView) row.findViewById(R.id.addressTextView);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);

            row.setTag(holder);
        } else {
            holder = (StoreHolder) row.getTag();
        }

        Store store = mData[position];
        //setting the view to reflect the data we need to display
        holder.imageView.setOnClickListener(PopupListener);
        Integer rowPosition = position;
        holder.imageView.setTag(rowPosition);

        holder.nameView.setText(store.mNameOfStore);
        holder.addressView.setText(String.valueOf(store.mAddress));

        //for getting the image
        int resID = mContext.getResources().getIdentifier(store.mNameOfImage, "drawable",mContext.getPackageName());
        holder.imageView.setImageResource(resID);

        return row;

    }

    View.OnClickListener PopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer viewPosition = (Integer) v.getTag();
            Store s = mData[viewPosition];
            Toast.makeText(getContext(), s.mPopup,Toast.LENGTH_SHORT).show();
        }
    };

    private static class StoreHolder {
        TextView nameView;
        TextView addressView;
        ImageView imageView;
    }
}
