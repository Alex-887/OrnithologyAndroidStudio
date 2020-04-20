package com.example.ornithology_favre_berthouzoz.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    public EditText mMail, mPassword;
    public Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();
        mMail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);




        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent (LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });





        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String mail = mMail.getText().toString();
                String pwd = mPassword.getText().toString();

                if(mail.isEmpty()){
                    mMail.setError(null);
                    mMail.requestFocus();
                } else
                if(pwd.isEmpty() ){
                    mPassword.setError(null);
                    mPassword.requestFocus();
                }

                else
                if(mail.isEmpty() && pwd.isEmpty() ){
                    Toast.makeText(LoginActivity.this,"The field are empty", Toast.LENGTH_SHORT).show();
                } else
                if(!(mail.isEmpty() && pwd.isEmpty())){

                    mAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login unsuccessful", Toast.LENGTH_SHORT).show();

                            }

                            else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            }
                    }




                });
                }

                else {
                    Toast.makeText(LoginActivity.this,"An unexpected error occured", Toast.LENGTH_SHORT);
                }


            }

        });




        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
                }
            }
        };















    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
