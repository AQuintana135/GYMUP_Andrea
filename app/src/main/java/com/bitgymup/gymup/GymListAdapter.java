package com.bitgymup.gymup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GymListAdapter extends RecyclerView.Adapter<GymListAdapter.ViewHolder> {

    private List<Gym> mData;
    private LayoutInflater mInflater;
    private Context context;

    public GymListAdapter(List<Gym> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context   = context;
        this.mData     = itemList;

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public GymListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.gym_card, null);
        return new GymListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GymListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Gym> items){ mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name, phoneNumber;

        ViewHolder(View itemView){
            super(itemView);
            name        = itemView.findViewById(R.id.tvGymName);
            phoneNumber = itemView.findViewById(R.id.tvGymPhone);

        }

        void bindData(final Gym item){
            name.setText(item.getName());
            phoneNumber.setText(item.getPhoneNumber());
        }

    }

}
