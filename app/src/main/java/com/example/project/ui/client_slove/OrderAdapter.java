package com.example.project.ui.client_slove;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.FirestoreHandler.FirestoreHandler;
import com.example.project.FirestoreHandler.Order;
import com.example.project.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    private List<Order> mDataset;
    private String TAG = "OrderAdapter";
    OrderAdapter(List<Order> orderList, Context context){
        mDataset = orderList;
        Log.v(TAG, mDataset.toString());
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView object;
        public TextView size;
        public TextView material;
        public TextView other;
        public TextView status;
        public Button solve;
        public MyViewHolder(View v) {
            super(v);
            object = (TextView) v.findViewById(R.id.object);
            size = (TextView) v.findViewById(R.id.size);
            material = (TextView)v.findViewById(R.id.material);
            other = (TextView) v.findViewById(R.id.other);
            status = (TextView) v.findViewById(R.id.status);
            solve = (Button) v.findViewById(R.id.solve);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clientorder, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.v(TAG, "" + position);
        final Order order = mDataset.get(position);
        holder.object.setText("項目: " + order.object);
        holder.size.setText("尺寸: " + order.size);
        holder.material.setText("材料: " + order.material);
        holder.other.setText("備註: " + order.other);
        if(order.status.equals("ordering")) {
            holder.status.setText("未收單");
            holder.solve.setText("收單");
            holder.solve.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    FirestoreHandler.solveOrder(order.id, holder.status);
                    holder.solve.setText("刪除訂單");
                    holder.solve.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            });
        }
        else{
            if(order.status != null && order.price != null)
                holder.status.setText("廠商: " + order.status + " 出價: " + order.price);
            else if(order.status != null)
                holder.status.setText("廠商: " + order.status);
            holder.solve.setText("刪除訂單");
            holder.solve.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    remove(position);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.v(TAG, mDataset.size() + "");
        return mDataset.size();
    }

    private void remove(int position) {
        FirestoreHandler.delete("projectOrder", mDataset.get(position).id);
        FirestoreHandler.delete("orderResult", mDataset.get(position).id);
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
