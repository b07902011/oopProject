package com.example.project.ui.client_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;

public class ClientHomeFragment extends Fragment {

    private ClientHomeViewModel clientHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientHomeViewModel =
                ViewModelProviders.of(this).get(ClientHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        clientHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
