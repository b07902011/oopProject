package com.example.project.ui.client_order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.FirestoreHandler.FirestoreHandler;
import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClientOrderFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Spinner object, material;
    private TextInputLayout size, other;
    private String TAG = "order";

    public ClientOrderFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_client_order, container, false);
        initSpinner(root);
        init(root);
        return root;
    }

    private void initSpinner(View root){
        object = (Spinner)root.findViewById(R.id.object);
        material = (Spinner)root.findViewById(R.id.material);
        final String[] lunch1 = {"門", "門", "門", "門", "門"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        object.setAdapter(lunchList);
        final String[] lunch2 = {"鐵", "鐵", "鐵", "鐵", "鐵"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);
        material.setAdapter(lunchList2);
    }

    private void init(View root){
        size = (TextInputLayout)root.findViewById(R.id.size);
        other = (TextInputLayout)root.findViewById(R.id.other);
        Button orderBt = (Button)root.findViewById(R.id.order);
        mAuth = FirebaseAuth.getInstance();
        size.getEditText().setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    size.setError(null);
                }
                if(!hasFocus){
                    Log.v(TAG, "接到的: " + size.getEditText().getText().toString());
                    if(!size.getEditText().getText().toString().equals("")){
                        size.setError(null);
                    }
                }
            }
        });
        orderBt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, Object> order = new HashMap<>();
                Map<String, Object> result = new HashMap<>();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if(size.getEditText().getText().toString().equals("")){
                    size.setError("此欄位不可為空");
                }
                else {
                    order.put("項目", object.getSelectedItem().toString());
                    order.put("材料", material.getSelectedItem().toString());
                    order.put("尺寸", size.getEditText().getText().toString());
                    if(!other.getEditText().getText().toString().equals(""))
                        order.put("備註", other.getEditText().getText().toString());
                    else
                        order.put("備註", "無");
                    order.put("user", mAuth.getCurrentUser().getDisplayName());
                    order.put("status", "ordering");
                    String doucumentId = FirestoreHandler.upload("projectOrder", order);
                    result.put("id", doucumentId);
                    FirestoreHandler.upload("orderResult", doucumentId, result);
                    size.getEditText().setText("");
                    other.getEditText().setText("");
                    Toast.makeText(getActivity(), "訂單已發出",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
