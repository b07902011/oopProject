package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEt, passwordEt;
    private String email, password, TAG = "Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //jumpToClient();
        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());
        emailEt = (EditText)findViewById(R.id.username);
        passwordEt = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        if(email.equals("") || password.equals(""))
            return;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void signup(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void jumpToFactory(){
        Intent intent = new Intent(this, FactoryActivity.class);
        startActivity(intent);
    }

    public void jumpToClient(){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void updateUI(FirebaseUser user){
        if(user == null){

        }
        else if(user.getDisplayName().substring(0, 1).equals("F")){
            jumpToFactory();
        }
        else if(user.getDisplayName().substring(0, 1).equals("C")){
            jumpToClient();
        }
    }

}
