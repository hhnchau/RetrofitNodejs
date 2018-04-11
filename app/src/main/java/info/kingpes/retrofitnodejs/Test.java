package info.kingpes.retrofitnodejs;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Test extends AppCompatActivity {
    int y = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10000; i++){
                    y++;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TEST", y + " 1");
                    }
                });

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TEST", y + " 2");
                    }
                },0);


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("TEST", y + " 3");
                            }
                        });
                    }
                });
                thread.start();

            }
        });

    }
}
