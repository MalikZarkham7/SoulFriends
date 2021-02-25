package com.example.soulfriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText Passreset;
    private Button buttonreset;
    private FirebaseAuth firebase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Passreset = (EditText) findViewById(R.id.editTextTextEmailAddress);
        buttonreset = (Button) findViewById(R.id.button6);
        firebase1 = FirebaseAuth.getInstance();
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = Passreset.getText().toString().trim();

                if (useremail.equals("")) {
                    Toast.makeText(ForgetPassword.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                } else {
                    firebase1.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgetPassword.this, Signin.class));
                            }else{
                                Toast.makeText(ForgetPassword.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}