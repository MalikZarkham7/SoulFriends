package com.example.soulfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Signin,Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Signin=(Button)findViewById(R.id.Signin);
        Signup=(Button)findViewById(R.id.Signup);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Signin=new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Signup=new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
    }
}