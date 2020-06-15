package com.example.project.ui.client_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;

public class ClientHomeFragment extends Fragment {

    private ClientHomeViewModel clientHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientHomeViewModel =
                ViewModelProviders.of(this).get(ClientHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client_home, container, false);
        return root;
    }
}
