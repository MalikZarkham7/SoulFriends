package com.example.soulfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menufile extends AppCompatActivity {
    Button Chatinfo,languagebot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menufile);
        Chatinfo=(Button)findViewById(R.id.button);
        languagebot=(Button)findViewById(R.id.button3);
        Chatinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Chatinfo=new Intent(Menufile.this,Chatbotinfo.class);
                startActivity(Chatinfo);
            }
        });
        languagebot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Lchatbot= new Intent(Menufile.this,LanguageChatbotinfo.class);
                startActivity(Lchatbot);
            }
        });
    }
}