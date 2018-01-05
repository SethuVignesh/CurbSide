package com.theatro.curbside;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class LastActivity extends AppCompatActivity {
    TextView tvMessage,tvName;
    ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

         tvMessage=(TextView)findViewById(R.id.tvMessage);
         tvName=(TextView)findViewById(R.id.tvName);
         imageView=(ImageView)findViewById(R.id.tvImage);
        Button btnRestart=(Button)findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LastActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(LastActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter(Utility.WEBSOCKET_MESSAGE));

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            try {
                LastActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String message = intent.getStringExtra("message");
                            tvMessage.setText(message);

                            String imgUrl = intent.getStringExtra("url");

                            Glide.with(LastActivity.this).load(imgUrl)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView);

                            String name= intent.getStringExtra("name");
                            tvName.setText(name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    };
}
