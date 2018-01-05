package com.theatro.curbside;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    TextView messageView;
    ImageView profile;
    String orderId;
    Button button4;
    LinearLayout userScreen;
    RelativeLayout mapsScreen;

    TextView tvMessage, tvName;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pick up location");
        messageView = (TextView) findViewById(R.id.textView4);
        profile = (ImageView) findViewById(R.id.imageView6);
//        tvName = (TextView) findViewById(R.id.tvName);
        userScreen = (LinearLayout) findViewById(R.id.userScreen);
        mapsScreen = (RelativeLayout) findViewById(R.id.mapsScreen);
        mapsScreen.setVisibility(View.VISIBLE);
        userScreen.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if (intent != null) {
            String message = intent.getStringExtra("message");
            orderId = intent.getStringExtra("orderId");
//            tvName.setText(orderId);
            messageView.setText(message);
        }

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvName = (TextView) findViewById(R.id.tvName);
        imageView = (ImageView) findViewById(R.id.tvImage);
        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            try {
                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (intent.hasExtra("ws")) {

                                Toast.makeText(getApplicationContext(), "Connection to server lost", Toast.LENGTH_LONG).show();
                                return;
                            }

                            String message = intent.getStringExtra("message").trim();


                            String imgUrl = intent.getStringExtra("url").trim();
                            String name = intent.getStringExtra("name").trim();

                            if (imgUrl != null && name != null && imgUrl.equalsIgnoreCase("null") == false && name.equalsIgnoreCase("null") == false && imgUrl.length()>0 && imgUrl.length()>0) {

                                mapsScreen.setVisibility(View.INVISIBLE);
                                userScreen.setVisibility(View.VISIBLE);

                                Glide.with(MapsActivity.this).load(imgUrl)
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView);


                                tvName.setText(name);
                                tvMessage.setText(message);
//                                Intent intent1 = new Intent(MapsActivity.this, LastActivity.class);
//                                startActivity(intent1);
                            } else {
                                mapsScreen.setVisibility(View.VISIBLE);
                                userScreen.setVisibility(View.INVISIBLE);
                                messageView.setText(message);
                            }
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MapsActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter(Utility.WEBSOCKET_MESSAGE));

    }

    @Override
    protected void onPause() {
        super.onPause();
//        disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    private void connect() {
        Intent i = new Intent(MapsActivity.this, WebSocketService.class);
        i.putExtra("orderId", orderId);
        startService(i);
    }

    private void disconnect() {
        Intent i = new Intent(MapsActivity.this, WebSocketService.class);
        stopService(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
    }
}
