package info.kingpes.retrofitnodejs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import okhttp3.internal.Util;

public class Socketio extends AppCompatActivity implements View.OnClickListener {
    private Button btnSend;
    private EditText edtKey, edtContent;
    private TextView txtKey1, txtKey2, txtKey3, txtView1, txtView2, txtView3;

    private Socket mSocket;

    private String key1, key2, key3;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set FullScreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_socketio);

        sharedPreferences = this.getSharedPreferences("IO", Context.MODE_PRIVATE);
        final String url = getIntent().getStringExtra("url");
        key1 = getIntent().getStringExtra("key1");
        key2 = getIntent().getStringExtra("key2");
        key3 = getIntent().getStringExtra("key3");
        init();
        {

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        //mSocket = IO.socket("http://192.168.0.109:3000");
                        mSocket = IO.socket(url);
                        mSocket.connect();

                        mSocket.on(key1, onKey1);
                        mSocket.on(key2, onKey2);
                        mSocket.on(key3, onKey3);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        Log.d("Socket", e.toString());
                        Toast.makeText(Socketio.this, "Connect fail", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }, 1500);
        }

    }

    private void init() {
        findViewById(R.id.clear).setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.send);
        btnSend.setOnClickListener(this);
        edtKey = (EditText) findViewById(R.id.key);
        String key = sharedPreferences.getString("key", "");
        edtKey.setText(key);
        edtContent = (EditText) findViewById(R.id.content);
        String content = sharedPreferences.getString("content", "");
        edtContent.setText(content);
        txtKey1 = (TextView) findViewById(R.id.key1);
        txtKey2 = (TextView) findViewById(R.id.key2);
        txtKey3 = (TextView) findViewById(R.id.key3);
        txtView1 = (TextView) findViewById(R.id.view1);
        txtView2 = (TextView) findViewById(R.id.view2);
        txtView3 = (TextView) findViewById(R.id.view3);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) {
            if (edtKey.getText().toString().equals("")) {
                edtKey.setError("Please input");
                return;
            } else if (edtContent.getText().toString().equals("")) {
                edtContent.setError("Please input");
                return;
            }

            sharedPreferences.edit().putString("key", edtKey.getText().toString().trim()).apply();
            sharedPreferences.edit().putString("content", edtContent.getText().toString().trim()).apply();


            String content = edtContent.getText().toString();
            if (content.substring(0,1).equals("(")){
                //content = content.substring(1,content.length()-1);
                content = content.replace("(", "{");
                content = content.replace(")", "}");

                try {
                    JSONObject obj = new JSONObject(content);

                    if (mSocket.connected()) {
                        mSocket.emit(edtKey.getText().toString().trim(), obj);
                        Toast.makeText(this, "Sending...", Toast.LENGTH_SHORT).show();
//                Person p = new Person();
//                p.setTen("chau");
//                p.setHo("Huynh");
//                Gson gson = new Gson();
//                try {
//                    JSONObject obj = new JSONObject(gson.toJson(p));
//                    mSocket.emit("object", obj);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                if (mSocket.connected()) {
                    mSocket.emit(edtKey.getText().toString().trim(), content);
                    Toast.makeText(this, "Sending...", Toast.LENGTH_SHORT).show();
//                Person p = new Person();
//                p.setTen("chau");
//                p.setHo("Huynh");
//                Gson gson = new Gson();
//                try {
//                    JSONObject obj = new JSONObject(gson.toJson(p));
//                    mSocket.emit("object", obj);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                }

            }
        }else {
            txtView1.setText("");
            txtView2.setText("");
            txtView3.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtKey1.setText(key1);
        txtKey2.setText(key2);
        txtKey3.setText(key3);
    }

    private Emitter.Listener onKey1 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    txtView1.append(args[0].toString());

//
//                    Gson gson = new Gson();
//                    Person p = gson.fromJson(args[0].toString(), Person.class);
//                    Toast.makeText(Socketio.this, p.getTen(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener onKey2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    txtView2.append(args[0].toString());
//
//                    Gson gson = new Gson();
//                    Person p = gson.fromJson(args[0].toString(), Person.class);
//                    Toast.makeText(Socketio.this, p.getTen(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener onKey3 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    txtView3.append(args[0].toString());
//
//                    Gson gson = new Gson();
//                    Person p = gson.fromJson(args[0].toString(), Person.class);
//                    Toast.makeText(Socketio.this, p.getTen(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (mSocket !=null) {
            mSocket.close();
        }
    }
}