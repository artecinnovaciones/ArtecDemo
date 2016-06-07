package com.artecinnovaciones.artecdemo.Conexion;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by LAP-NIDIA on 04/06/2016.
 */
public class Post {

    public String Sendpost(String posturl,String id,String dato){

        try {

            HttpClient cliente = new DefaultHttpClient();
            HttpPost post = new HttpPost(posturl);

            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            nvp.add(new BasicNameValuePair(id,dato));

            post.setEntity(new UrlEncodedFormEntity(nvp));

            HttpResponse resp = cliente.execute(post);

            HttpEntity ent = resp.getEntity();
            String text = EntityUtils.toString(ent);

            return text;

        } catch(Exception e) { return "error";}
    }

}
