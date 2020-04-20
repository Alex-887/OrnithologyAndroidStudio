package com.example.ornithology_favre_berthouzoz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ornithology_favre_berthouzoz.ui.main.MainActivity;
import com.example.ornithology_favre_berthouzoz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {


    public EditText mMail, mPassword;
    public Button mRegister;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mMail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mRegister = findViewById(R.id.register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mMail.getText().toString();
                String pwd = mPassword.getText().toString();

                if(mail.isEmpty()){
                    mMail.setError(null);
                    mMail.requestFocus();
                } else
                    if(pwd.isEmpty()){
                        mPassword.setError(null);
                        mPassword.requestFocus();
                    }

                    else
                        if(mail.isEmpty() && pwd.isEmpty() ){
                        Toast.makeText(SignUpActivity.this,"The field are empty", Toast.LENGTH_SHORT).show();
                    } else
                        if(pwd.length()<6){
                            Toast.makeText(SignUpActivity.this,"Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                        }
                        else


                        if(!(mail.isEmpty() && pwd.isEmpty())){
                            mAuth.createUserWithEmailAndPassword(mail, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(!task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this,"Cannot register", Toast.LENGTH_SHORT).show();

                                    }

                                    else {
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"An unexpected error occured", Toast.LENGTH_SHORT);
                        }


            }
        });

    }





}
