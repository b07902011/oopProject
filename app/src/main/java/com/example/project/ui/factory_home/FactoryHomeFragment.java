package com.example.project.ui.factory_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;

public class FactoryHomeFragment extends Fragment {

    private FactoryHomeViewModel factoryHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        factoryHomeViewModel =
                ViewModelProviders.of(this).get(FactoryHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_factory_home, container, false);
        return root;
    }
}
