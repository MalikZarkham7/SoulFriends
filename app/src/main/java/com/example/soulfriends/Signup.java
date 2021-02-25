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

public class Signup extends AppCompatActivity {
    EditText Name,emailId,password,CPassword;
    Button Sign_up;
    TextView Signin_text;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Sign_up=(Button)findViewById(R.id.Signupbutton);
        Signin_text=(TextView)findViewById(R.id.textView10);
        Name = (EditText) findViewById(R.id.editTextTextPersonName);
        emailId = (EditText) findViewById(R.id.Emailaddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        CPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String email=emailId.getText().toString();
                    String pwd=password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Signup.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(Signup.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Signup.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                sendEmailVerification();
                            }
                        }
                    });
                } else{
                    Toast.makeText(Signup.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
                }

        });
        Signin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Signin=new Intent(Signup.this,Signin.class);
                startActivity(Signin);
            }
        });
    }
   private void sendEmailVerification(){
        FirebaseUser User =mFirebaseAuth.getCurrentUser();
        if(User!=null){
           User.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(Signup.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                       mFirebaseAuth.signOut();
                       finish();
                       startActivity(new Intent(Signup.this, Menufile.class));
                   }else{
                       Toast.makeText(Signup.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }
}