package com.android.genghis.carpo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
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
 * Created by WangXin on 2015/12/22 0022.
 */
public class JsonRequestWithAuth<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Listener<T> listener;
    private static final Map<String,String>params = new HashMap<String,String>();


    public JsonRequestWithAuth(String url,Class<T> clazz,Listener<T> listener,Map<String,String>appendParams,ErrorListener errorListener){
        super(Request.Method.POST,url,errorListener);
        this.clazz = clazz;
        this.listener=listener;
        params.putAll(appendParams);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonStr = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(jsonStr,clazz),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
