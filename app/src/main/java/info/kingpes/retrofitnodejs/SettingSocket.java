package info.kingpes.retrofitnodejs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Chau Huynh on 5/7/2017.
 */

public class SettingSocket extends AppCompatActivity {
    private LinearLayout mainLinearLayout;

    private Button btn;
    private EditText edtUrl, edtKey1, edtKey2, edtKey3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mainLinearLayout = new LinearLayout(this);
//        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        setContentView(mainLinearLayout);
        setContentView(R.layout.activity_setting_socket);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("SOCKET", Context.MODE_PRIVATE);

        edtKey1 = (EditText) findViewById(R.id.key1);
        String key1 = sharedPreferences.getString("key1", "");
        edtKey1.setText(key1);
        edtKey2 = (EditText) findViewById(R.id.key2);
        String key2 = sharedPreferences.getString("key2", "");
        edtKey2.setText(key2);
        edtKey3 = (EditText) findViewById(R.id.key3);
        String key3 = sharedPreferences.getString("key3", "");
        edtKey3.setText(key3);
        edtUrl = (EditText) findViewById(R.id.url);
        String url = sharedPreferences.getString("url", "");
        edtUrl.setText(url);

        btn = (Button) findViewById(R.id.btn_connect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUrl.getText().toString().equals("")){
                    edtUrl.setError("Please input");
                    return;
                }

                Intent i = new Intent(SettingSocket.this, Socketio.class);
                i.putExtra("url",edtUrl.getText().toString().trim());
                sharedPreferences.edit().putString("url",edtUrl.getText().toString().trim()).apply();
                if (edtKey1.getText().toString().equals("")){
                    i.putExtra("key1","key_1");
                }else {
                    i.putExtra("key1",edtKey1.getText().toString().trim());
                    sharedPreferences.edit().putString("key1",edtKey1.getText().toString().trim()).apply();
                }

                if (edtKey2.getText().toString().equals("")){
                    i.putExtra("key2","key_2");
                }else {
                    i.putExtra("key2",edtKey2.getText().toString().trim());
                    sharedPreferences.edit().putString("key2",edtKey2.getText().toString().trim()).apply();
                }

                if (edtKey3.getText().toString().equals("")){
                    i.putExtra("key3","key_3");
                }else {
                    i.putExtra("key3",edtKey3.getText().toString().trim());
                    sharedPreferences.edit().putString("key3",edtKey3.getText().toString().trim()).apply();
                }
                startActivity(i);
            }
        });


    }
    private void createTextView(){
        for (int i = 0; i < 10; i++){
            TextView textView = new TextView(this);
            textView.setText("Chau");
            textView.setPadding(5,5,5,5);
            textView.setTextColor(Color.RED);
            //textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.GREEN);
            textView.setTextSize((float)20);
            mainLinearLayout.addView(textView);


        }
    }
}
