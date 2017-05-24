package com.example.administrator.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText accountText, keyText;
    private Button forgetKeyButton, loginButton, inButton;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mQueue = Volley.newRequestQueue(this);

        accountText = (EditText) findViewById(R.id.account);
        keyText = (EditText) findViewById(R.id.keying);
        forgetKeyButton = (Button) findViewById(R.id.forgetKey);
        loginButton = (Button) findViewById(R.id.login);
        inButton = (Button) findViewById(R.id.In);
        forgetKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valodata();
            }
        });

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
                startActivity(intent);
            }
        });

        StringRequest stringRequest = new StringRequest("http://api.zhaibao.cn/login?mobile="+accountText.getText()+
                "&password="+keyText.getText()+"&version=1.0&deviceType=2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONObject jsonObject2 = new JSONObject(success);
                            String msg = jsonObject2.getString("message");
                        }catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }

    private boolean valodata(){
        boolean valid = true;

        String account = accountText.getText().toString();
        String password = keyText.getText().toString();
        if (account.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(account).matches()){
            accountText.setError("enter a valid account address");
            valid = false;
        }else {
            accountText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            keyText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else {
            keyText.setError(null);
        }
        return valid;
    }
}
