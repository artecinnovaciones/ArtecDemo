package com.artecinnovaciones.artecdemo.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
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

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GoogleCloudMessaging gcm = null;

    private String SENDER_ID = "58800470060";
    public static  String regid;

    String text="";

    public void Sendpost(final String posturl,final String id,final String dato,final int pet,final Context context){
        new AsyncTask<Void, Void, Void>(){

            private String msg;

            @Override
            protected Void doInBackground(Void... arg0) {

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                try {
                    Log.i("Sender",SENDER_ID);

                    regid = gcm.register(SENDER_ID);

                    HttpClient cliente = new DefaultHttpClient();
                    HttpPost post = new HttpPost(posturl);

                    List<NameValuePair> nvp = new ArrayList<NameValuePair>();
                    if (pet==1){
                        nvp.add(new BasicNameValuePair(id,regid));
                    }if (pet==2){
                        nvp.add(new BasicNameValuePair(id,dato));
                        nvp.add(new BasicNameValuePair("id_user",regid));
                    }if (pet==3){
                        nvp.add(new BasicNameValuePair(id,dato));
                        nvp.add(new BasicNameValuePair("id_user",regid));
                    }

                    post.setEntity(new UrlEncodedFormEntity(nvp));

                    HttpResponse resp = cliente.execute(post);

                    HttpEntity ent = resp.getEntity();
                    text = EntityUtils.toString(ent);

                } catch (IOException e) { e.printStackTrace();
                } catch(Exception e) { e.printStackTrace(); }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

            }
        }.execute();
    }

}
