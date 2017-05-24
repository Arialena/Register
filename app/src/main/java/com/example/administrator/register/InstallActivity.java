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

public class InstallActivity extends AppCompatActivity{

    private Button mgePushBtn, cleanCacheBtn, opinionBackBtn, disclaimerBtn;
    private ImageButton installBackButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.install_inst);

        mgePushBtn = (Button) findViewById(R.id.button_tuisong);
        cleanCacheBtn = (Button) findViewById(R.id.button_huancun);
        opinionBackBtn = (Button) findViewById(R.id.button_yijian);
        disclaimerBtn = (Button) findViewById(R.id.button_shengming);

        installBackButton = (ImageButton) findViewById(R.id.installBackButton);
        installBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstallActivity.this, PersonalActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
