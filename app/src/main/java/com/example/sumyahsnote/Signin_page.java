package com.example.sumyahsnote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signin_page extends AppCompatActivity {
    EditText enteremail,entername,enterage,enterpassword;
    Button login,signin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private String userID;
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
        setContentView(R.layout.activity_signin_page);

        enteremail = findViewById(R.id.enteremail);
        entername = findViewById(R.id.entername);
        enterage = findViewById(R.id.enterage);
        enterpassword = findViewById(R.id.enterpassword);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);
        ProgressBar progressBar = findViewById(R.id.progress1);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String Name = entername.getText().toString();
                String Email = enteremail.getText().toString();
                String Age = enterage.getText().toString();
                String Password =  enterpassword.getText().toString();

                if(Name.isEmpty() || Email.isEmpty() || Age.isEmpty() || Password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
                mAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                    Toast.makeText(Signin_page.this, "Registration Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Name",Name);
                                    user.put("Age",Age);
                                    user.put("email",Email);
                                    documentReference.set(user);
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
                                    Toast.makeText(Signin_page.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });}
            });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}