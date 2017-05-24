package com.example.administrator.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class ForgetActivity extends AppCompatActivity {

    private EditText userNameText, getCodeText, userKeyText, newKey;
    private Button codeButton, submitButton;
    private ImageButton forgetBackButton;

    private boolean tag = true;
    private int i = 60;

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_imet);
        userNameText = (EditText) findViewById(R.id.userName);
        getCodeText = (EditText) findViewById(R.id.getCode);
        userKeyText = (EditText) findViewById(R.id.userNewKey);
        newKey = (EditText) findViewById(R.id.newKey);

        codeButton = (Button) findViewById(R.id.codeButton);
        submitButton = (Button) findViewById(R.id.setButton);
        forgetBackButton = (ImageButton) findViewById(R.id.forgetBackButton);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int a = msg.what;
                if (a == 1){
                    Toast.makeText(ForgetActivity.this,"密码修改成功", Toast.LENGTH_LONG).show();
                }else {

                }
            }
        };

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code();
                Toast.makeText(ForgetActivity.this,"验证码发送成功", Toast.LENGTH_LONG).show();
                changeBtnGetCode();
            }
        });

        forgetBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userNameText.getText().toString();
                String newKey1 = userKeyText.getText().toString();
                String newKey2 = newKey.getText().toString();
                if (user == null || user.equals("")){
                    Toast.makeText(ForgetActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
                }else if (newKey1.equals(newKey2)){
                    Toast.makeText(ForgetActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                    setUp();
                }else {
                    Toast.makeText(ForgetActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //获取验证码
    private void code(){
        new Thread(){
            @Override
            public void run() {
                super.run();

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://api.zhaibao.cn/sendResetPwdMobileCode?" +
                        "mobile="+userNameText.getText()+ "&version=1.0&deviceType=2");

                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity httpEntity = httpResponse.getEntity();

                        String str = EntityUtils.toString(httpEntity);

                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            String success = jsonObject.getString("success");
                            JSONObject jsonObject1 = new JSONObject(success);
                            String mgs = jsonObject1.getString("message");
                            int code = mgs.indexOf("code");

                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //重置密码提交、登录
    private void setUp(){
        new Thread(){
            @Override
            public void run() {
                super.run();

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://api.zhaibao.cn/resetPwd?mobile="+userNameText.getText()+"code="+getCodeText.getText()+
                        "password="+newKey.getText()+"&version=1.0&deviceType=2");

                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity httpEntity = httpResponse.getEntity();

                        String str = EntityUtils.toString(httpEntity);

                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            String success = jsonObject.getString("success");
                            JSONObject jsonObject1 = new JSONObject(success);
                            String mgs = jsonObject1.getString("message");
                            int code = mgs.indexOf("code");
                            Message message = new Message();
                            message.what = 1;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (ForgetActivity.this == null) {
                            break;
                        }

                        ForgetActivity.this.runOnUiThread(new Runnable() {
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
                if (ForgetActivity.this != null) {
                    ForgetActivity.this.runOnUiThread(new Runnable() {
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
