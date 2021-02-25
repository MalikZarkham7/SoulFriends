package com.example.soulfriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {
    TextView Aaccount;
    private EditText emailId,password;
    Button Signin;
    TextView Forgottpassword;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Aaccount=(TextView)findViewById(R.id.textView3);
        Signin=(Button)findViewById(R.id.SignIn);
        emailId=(EditText)findViewById(R.id.editTextTextEmailAddress2);
        password=(EditText)findViewById(R.id.editTextTextPassword3);
        Forgottpassword=(TextView)findViewById(R.id.textView2);
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(Signin.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Signin.this, Menufile.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Signin.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        Aaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Signin=new Intent(Signin.this,Signup.class);
                startActivity(Signin);
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Signin.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Signin.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(Signin.this,Menufile.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Signin.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });
        Forgottpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToHome = new Intent(Signin.this,ForgetPassword.class);
                startActivity(intToHome);
            }
        });
    }
  /*  private void validate(String username,String userpassword){
        progressDialog.setMessage("You need to login for going to Shop");
        progressDialog.show();
        Login_user.signInWithEmailAndPassword(username,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this,"Login Sucessfull",Toast.LENGTH_SHORT).show();
                    emailverification();
                }else{
                    Toast.makeText(Signin.this,"Login does not sucessfull",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }*/
   /* private void emailverification(){
        FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(Signin.this, Menufile.class));

        if(emailflag){
            finish();
            startActivity(new Intent(Signin.this,Menufile.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signOut();
        }
    }*/
}