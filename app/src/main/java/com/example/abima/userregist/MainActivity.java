package com.example.abima.userregist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    Button signup,login;
    EditText myMail,myPass;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        signup = (Button) findViewById(R.id.button);
        login = (Button) findViewById(R.id.button2);
        myMail = (EditText) findViewById(R.id.myEmail);
        myPass = (EditText) findViewById(R.id.pass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = myMail.getText().toString();
                String password = myPass.getText().toString();
                createAccount(mail,password);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = myMail.getText().toString();
                String password = myPass.getText().toString();
                signin(mail,password);
            }
        });

    }

    private void signin(String email, String password) {
        if(email.equals("") || email.isEmpty() || password.equals("") || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Fields cannot be left empty",
                    Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, user_login.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Login failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    public void createAccount(String email, String password) {
        if(email.equals("") || email.isEmpty() || password.equals("") || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Fields cannot be left empty",
                    Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("tag", "createUserWithEmail:success");
                                Toast.makeText(MainActivity.this, "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("tag", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}


















