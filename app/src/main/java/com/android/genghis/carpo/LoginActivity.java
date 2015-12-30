package com.android.genghis.carpo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.genghis.carpo.entity.User;
import com.android.genghis.carpo.network.JsonRequestWithAuth;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * *
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 * Created by WangXin on 2015/12/19 0019.
 */
public class LoginActivity extends Activity {
    private EditText username;
    private EditText password;
    private Button login;
    private ProgressDialog progressDialog;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("loginRequest");
    }

    private void initView() {
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(LoginActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                if (StringUtils.isBlank(userName) || StringUtils.isBlank(passWord)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    passWord = DigestUtils.md5Hex(passWord);
                    progressDialog.setMessage("正在登陆,请稍后...");
                    progressDialog.onStart();
                    progressDialog.show();
                    login(userName, passWord);
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void login(String userId, String password) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("userId",userId);
        params.put("password",password);
        JsonRequestWithAuth<User> loginRequset = new JsonRequestWithAuth<User>(ConstantValue.LOGIN_URL, User.class, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                if(response.getMessage().equals("success")){
                    it = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(it);
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误,请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        }, params,null);
        System.out.println("1");
        MyApplication.getHttpQueues().add(loginRequset);
    }
}
