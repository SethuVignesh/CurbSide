package com.theatro.curbside;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.theatro.curbside.webSocket.WebSocket;
import com.theatro.curbside.webSocket.WebSocketConnection;
import com.theatro.curbside.webSocket.WebSocketException;
import com.theatro.curbside.webSocket.WebSocketOptions;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketService extends Service implements WebSocket.WebSocketConnectionObserver {
    private static final String WS_THEATRO_SERVER = "wss"+MainActivity.logOnUrl.replace("https","").replace("http","") +"/clickandcollect";
    public static WebSocketConnection mConnection;
    private URI mServerURI;

    public WebSocketService() {
    }
String orderId="";
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        orderId=intent.getStringExtra("orderId");
        connect();
        return Service.START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnection != null && mConnection.isConnected() == true) {

            mConnection.disconnect();

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void connect() {
        try {

            this.mServerURI = new URI(WS_THEATRO_SERVER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mConnection != null && mConnection.isConnected() == true && mServerURI != null && mConnection.getUri().equals(mServerURI)) {
            return;
        }
        if (mConnection != null && mConnection.isConnected() == true) {

            mConnection.disconnect();
            return;
        }
        if (mConnection == null) {
            this.mConnection = new WebSocketConnection();
        }
        try {
            String[] protocolVal = {"echo-protocol"};
            mConnection.connect(mServerURI, protocolVal, this, new WebSocketOptions());
        } catch (WebSocketException e) {
            e.printStackTrace();
        } catch
                (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen() {
        Log.d("WebsocketnService", "onOpen");

        String name = Utility.getName(getApplicationContext());
        String msg="{\n" +
                "    \"messageType\": \"subscribe\",\n" +
                "    \"routingKey\": \"" + orderId + "\",\n" +
                "    \"userName\": \"" + name + "\"\n" +
                "}\n";
        mConnection.sendTextMessage(msg);
    }


    @Override
    public void onClose(WebSocketCloseNotification code, String reason) {
        mConnection = null;
        Log.d("WebsocketnService", "onClose");
    }

    @Override
    public void onTextMessage(String payload) {
        Log.d("WebsocketnService", "onTextMessage");
        sendMessage(payload);

    }

    private void sendMessage(String payload) {
        Log.d("sender", "Broadcasting message");
        try {
            JSONObject jsonObject = new JSONObject(payload);
            JSONObject emp= jsonObject.getJSONObject("employee");
            String url=emp.getString("image");
            String name=emp.getString("name");
            String status=jsonObject.getString("status");
            Intent intent = new Intent(Utility.WEBSOCKET_MESSAGE);
            intent.putExtra("message", status);
            intent.putExtra("url", url);
            intent.putExtra("name", name);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRawTextMessage(byte[] payload) {
        Log.d("WebsocketnService", "onRawText");

    }

    @Override
    public void onBinaryMessage(byte[] payload) {
        Log.d("WebsocketnService", "onBinaryMessage");

    }
}
