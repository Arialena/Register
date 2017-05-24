package com.example.administrator.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class SingInActivity extends AppCompatActivity {

    private EditText userText, codeText, userKeyText;
    private Button codeButton, singInButton;
    private ImageButton singBackButton;

    RequestQueue mQueue;

    private boolean tag = true;
    private int i = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singin_item);

        mQueue = Volley.newRequestQueue(this);

        userText = (EditText) findViewById(R.id.user);
        codeText = (EditText) findViewById(R.id.code);
        userKeyText = (EditText) findViewById(R.id.userKey);

        codeButton = (Button) findViewById(R.id.button);
        singInButton = (Button) findViewById(R.id.singIn);
        singBackButton = (ImageButton) findViewById(R.id.singBackButton);

        singBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SingInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest("http://api.zhaibao.cn/sendRegCode?mobile=" + userText.getText() + "&version=1.0&deviceType=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(SingInActivity.this, "验证码获取成功", Toast.LENGTH_LONG).show();
                                Log.d("TAG", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(stringRequest);
                changeBtnGetCode();
            }
        });

        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String code = codeText.getText().toString();
            }
        });
    }

    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (SingInActivity.this == null) {
                            break;
                        }

                        SingInActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                codeButton.setText("获取验证码(" + i + ")");
                                codeButton.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (SingInActivity.this != null) {
                    SingInActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            codeButton.setText("获取验证码");
                            codeButton.setClickable(true);
                        }
                    });
                }
            };
        };
        thread.start();
    }
}
