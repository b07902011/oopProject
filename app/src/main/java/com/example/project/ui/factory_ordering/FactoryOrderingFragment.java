package com.example.project.ui.factory_ordering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.FirestoreHandler.FirestoreHandler;
import com.example.project.FirestoreHandler.Order;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FactoryOrderingFragment extends Fragment {

    private List<Order> orderList;
    private FirebaseAuth mAth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_factory_ordering, container, false);
        mAth = FirebaseAuth.getInstance();
        FirestoreHandler.getAllOrder(this);
        return root;
    }

    public void setRecycleView(View root, List<Order> orderList){
        recyclerView = (RecyclerView) root.findViewById(R.id.factoryRecycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FactoryOrderAdapter(orderList, getActivity());
        recyclerView.setAdapter(mAdapter);
    }
}
