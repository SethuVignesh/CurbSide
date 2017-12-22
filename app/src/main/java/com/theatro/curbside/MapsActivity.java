package com.theatro.curbside;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView messageView,tvName;
    ImageView profile;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pick up location");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        messageView = (TextView) findViewById(R.id.textView4);
        profile = (ImageView) findViewById(R.id.imageView6);
        tvName=(TextView) findViewById(R.id.tvName);

        Intent intent = getIntent();
        if (intent != null) {
            String message = intent.getStringExtra("message");
             orderId = intent.getStringExtra("orderId");
            tvName.setText(orderId);
            messageView.setText(message);
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            try {
                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String message = intent.getStringExtra("message");
                            messageView.setText(message);

                            String imgUrl = intent.getStringExtra("url");

                            Glide.with(MapsActivity.this).load(imgUrl)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(profile);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(36.2144764, -113.6973982);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void connect() {
        Intent i = new Intent(MapsActivity.this, WebSocketService.class);
        i.putExtra("orderId",orderId);
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
