package com.example.project.ui.factory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.FactoryActivity;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FactoryFragment extends Fragment {

    private TextInputLayout name, email, password, check;
    private String nameString, emailString, passwordString, checkString;
    private String TAG = "Factorysignup";
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_factory_signup, container, false);
        mAuth = FirebaseAuth.getInstance();
        init(root);
        return root;
    }

    public void signupByString(String e, String p, final String n){
        mAuth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("F" + n)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("tag", "User profile updated.");
                                            }
                                        }
                                    });
                            Intent intent = new Intent(getActivity(), FactoryActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void init(View root){
        name = (TextInputLayout) root.findViewById(R.id.name);
        email = (TextInputLayout) root.findViewById(R.id.email);
        password = (TextInputLayout) root.findViewById(R.id.password);
        check = (TextInputLayout) root.findViewById(R.id.check);
        Button signupButton = (Button)root.findViewById(R.id.signup);
        signupButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                nameString = name.getEditText().getText().toString();
                emailString = email.getEditText().getText().toString();
                passwordString = password.getEditText().getText().toString();
                checkString = check.getEditText().getText().toString();
                boolean Empty = false;
                Empty |= checkEmpty(nameString, name);
                Empty |= checkEmpty(emailString, email);
                Empty |= checkEmpty(passwordString, password);
                Empty |= checkEmpty(checkString, check);
                if(!Empty){
                    if(!passwordString.equals(checkString)){
                        check.setError("請輸入相同密碼");
                    }
                    else {
                        signupByString(emailString, passwordString, nameString);
                    }
                }
            }
        });
        name.getEditText().setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        name.setError(null);
                    }
                    else if(name.getEditText().getText().toString().equals("")){
                        name.setError("此欄位不可為空");
                    }
            }
        });
        email.getEditText().setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    email.setError(null);
                }
                else if(email.getEditText().getText().toString().equals("")){
                    email.setError("此欄位不可為空");
                }
            }
        });
        password.getEditText().setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password.setError(null);
                }
                else if(password.getEditText().getText().toString().equals("")){
                    password.setError("此欄位不可為空");
                }
            }
        });
        check.getEditText().setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    check.setError(null);
                }
                else{
                    if(!check.getEditText().getText().toString().equals(password.getEditText().getText().toString())){
                        check.setError("請輸入相同密碼");
                    }
                    else if(check.getEditText().getText().toString().equals("")){
                        check.setError("此欄位不可為空");
                    }
                }
            }
        });
    }


    public boolean checkEmpty(String s, TextInputLayout t){
        if(s.equals("")){
            t.setError("此欄位不可為空");
            return true;
        }
        return false;
    }


}
