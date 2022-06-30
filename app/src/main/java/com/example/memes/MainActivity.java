package com.example.memes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
String s;
ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("loading the next meme...");
        loadmeme();
    }
    private void loadmeme() {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme/wholesomememes";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           s=  response.get("url").toString();
                           pd.dismiss();
                          ImageView image=findViewById(R.id.imageView);
                            Glide.with(MainActivity.this).load(s).into(image);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Volly", Toast.LENGTH_SHORT).show();

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

    public void clicknext(View view) {
        pd.show();
        loadmeme();

    }

    public void clickshare(View view) {
        Intent send=new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT,s);
        send.setType("text/*");
        Intent chooser=Intent.createChooser(send,"Share this meme! >>>");
        startActivity(send);
    }
}