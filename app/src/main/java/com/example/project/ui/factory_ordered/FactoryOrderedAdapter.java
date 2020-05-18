package com.example.project.ui.factory_ordered;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.FirestoreHandler.Order;
import com.example.project.R;

import java.util.List;

public class FactoryOrderedAdapter extends RecyclerView.Adapter<FactoryOrderedAdapter.MyViewHolder> {
    private List<Order> mDataset;
    private String TAG = "FactoryOrderedAdapter";

    FactoryOrderedAdapter(List<Order> orderList){
        mDataset = orderList;
        Log.v(TAG, mDataset.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factoryordered, parent, false);
        FactoryOrderedAdapter.MyViewHolder vh = new FactoryOrderedAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Order order = mDataset.get(position);
        holder.object.setText(order.object);
        holder.size.setText(order.size);
        holder.material.setText(order.material);
        holder.other.setText(order.other);
        holder.client.setText("用戶: " + order.user);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView object;
        public TextView size;
        public TextView material;
        public TextView other;
        public TextView client;
        public MyViewHolder(@NonNull View v) {
            super(v);
            object = (TextView) v.findViewById(R.id.object);
            size = (TextView) v.findViewById(R.id.size);
            material = (TextView)v.findViewById(R.id.material);
            other = (TextView) v.findViewById(R.id.other);
            client = (TextView) v.findViewById(R.id.client);
        }
    }
}
