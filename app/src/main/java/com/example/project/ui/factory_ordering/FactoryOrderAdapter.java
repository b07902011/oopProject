package com.example.project.ui.factory_ordering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.FirestoreHandler.FirestoreHandler;
import com.example.project.FirestoreHandler.Order;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FactoryOrderAdapter extends RecyclerView.Adapter<FactoryOrderAdapter.MyViewHolder> {
    private List<Order> mDataset;
    private String TAG = "OrderAdapter";
    private FirebaseAuth mAuth;
    FactoryOrderAdapter(List<Order> orderList, Context context){
        mDataset = orderList;
        Log.v(TAG, mDataset.toString());
        mAuth = FirebaseAuth.getInstance();
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
        public EditText price;
        public Button submit;
        public MyViewHolder(View v) {
            super(v);
            object = (TextView) v.findViewById(R.id.object);
            size = (TextView) v.findViewById(R.id.size);
            material = (TextView)v.findViewById(R.id.material);
            other = (TextView) v.findViewById(R.id.other);
            price = (EditText) v.findViewById(R.id.price);
            submit = (Button) v.findViewById(R.id.submit);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FactoryOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factoryorder, parent, false);
        FactoryOrderAdapter.MyViewHolder vh = new FactoryOrderAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FactoryOrderAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.v(TAG, "" + position);
        final Order order = mDataset.get(position);
        holder.object.setText(order.object);
        holder.size.setText(order.size);
        holder.material.setText(order.material);
        holder.other.setText(order.other);
        holder.submit.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!holder.price.getText().toString().equals("")){
                    FirestoreHandler.update("orderResult", order.id, mAuth.getCurrentUser().getDisplayName(), holder.price.getText().toString());
                    holder.price.setText("");
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.v(TAG, mDataset.size() + "");
        return mDataset.size();
    }

    private void remove(int position) {
    }
}
