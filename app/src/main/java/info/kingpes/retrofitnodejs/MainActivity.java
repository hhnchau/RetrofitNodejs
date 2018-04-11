package info.kingpes.retrofitnodejs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Retrofit retrofit;
    public static ServiceApi serviceApi;

    private String url = "http://192.168.1.112:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent i = new Intent(this, SettingSocket.class);
//        startActivity(i);
//        finish();


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(new RequestInterceptor(this));
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            serviceApi = retrofit.create(ServiceApi.class);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Retrofit", "Cannot connect to server!");
        }


        findViewById(R.id.get).setOnClickListener(this);
        findViewById(R.id.post).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.get) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", 1);

            serviceApi.demoGet(params, "1", "2", "3").enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {


                    List<User> u = response.body();
                    if (u != null) {
                        Toast.makeText(MainActivity.this, u.get(0).getTen(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Map<String, Object> params = new HashMap<>();
            params.put("ho", "Chau123Bao");
            params.put("ten", "Chau123Bao");

            Person p = new Person();
            p.setHo("Huynh");
            p.setTen("Bao");

            serviceApi.demoPost(p, "Author").enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response != null) {
                        User u = response.body();
                        if (u != null)
                            Toast.makeText(MainActivity.this, u.getTen() + u.getTuoi(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }
    }
}