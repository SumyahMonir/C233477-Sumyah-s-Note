package com.example.sumyahsnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText enteremail,enterpassword;
    Button login,signin;
    ProgressBar progressBar;
    TextView textView;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent welcomebhaiyya = new Intent(getApplicationContext(), Home_page.class);
            startActivity(welcomebhaiyya);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        enteremail = findViewById(R.id.enteremail);
        enterpassword = findViewById(R.id.enterpassword);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progress1);
        textView = findViewById(R.id.textView3);
        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signin_page.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = enteremail.getText().toString();
                password = enterpassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter Your Email!",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter Your Password!",Toast.LENGTH_LONG).show();
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                    Toast.makeText(LoginActivity.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putBoolean("flag",true);
                                    editor.apply();
                                    Intent letsgo = new Intent(getApplicationContext(), Home_page.class);
                                    startActivity(letsgo);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}