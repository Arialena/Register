package com.example.administrator.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class PersonalActivity extends AppCompatActivity {

    private Button personMgeBtn, officeMgeBtn, informMgeBtn, transfuseStateBtn,
            helpUsBtn, installBtn, quitBtn;
    private ImageButton personalBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);

        personMgeBtn = (Button) findViewById(R.id.button_person);
        officeMgeBtn = (Button) findViewById(R.id.button_keshi);
        informMgeBtn = (Button) findViewById(R.id.button_message);
        transfuseStateBtn = (Button) findViewById(R.id.button_shuye);
        helpUsBtn = (Button) findViewById(R.id.button_help);
        installBtn = (Button) findViewById(R.id.button_setting);
        quitBtn = (Button) findViewById(R.id.button_upte);

        personalBackButton = (ImageButton) findViewById(R.id.personalBackButton);
        personalBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        installBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, InstallActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
