package com.theatro.curbside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemListActiity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_actiity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ApiCalls("single").execute();
            }
        });
        Button button2 = (Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ApiCalls("multiple").execute();
            }
        });
    }

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
    protected void onResume() {
        super.onResume();


    }

    public JSONObject getJson(String type) {
        JSONObject full = new JSONObject();
        try {
            if (type.equalsIgnoreCase("single")) {

                JSONObject customer = new JSONObject();
//                customer.put("id", "NS-34334323");
                customer.put("name", Utility.getName(ItemListActiity.this));
                customer.put("type", Utility.getRole(ItemListActiity.this));
                customer.put("email", Utility.getMail(ItemListActiity.this));

                full.put("customer", customer);

                JSONObject order = new JSONObject();
                order.put("id", "4423255");
                order.put("type", "single");

                JSONArray itemsArray = new JSONArray();

                JSONObject item = new JSONObject();
                item.put("sku", 121212);
                item.put("name", "Name of item");
                item.put("description", "Item Description");
                itemsArray.put(item);


                order.put("items", itemsArray);
                full.put("order", order);
            } else {


                JSONObject customer = new JSONObject();
                customer.put("id", "NS-34334323");
                customer.put("name", Utility.getName(ItemListActiity.this));
                customer.put("type", Utility.getRole(ItemListActiity.this));
                full.put("customer", customer);

                JSONObject order = new JSONObject();
                order.put("id", "4423255");
                order.put("type", "multiple");

                JSONArray itemsArray = new JSONArray();

                JSONObject item = new JSONObject();
                item.put("sku", 121212);
                item.put("name", "Name of item");
                item.put("description", "Item Description");
                JSONObject item2 = new JSONObject();
                item2.put("sku", 121212);
                item2.put("name", "Name of item");
                item2.put("description", "Item Description");
                itemsArray.put(item);
                itemsArray.put(item2);

                order.put("items", itemsArray);
                full.put("order", order);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return full;
    }

    public class ApiCalls extends AsyncTask<Void, Void, Void> {
        String type;
        private final ProgressDialog dialog = new ProgressDialog(ItemListActiity.this);

        ApiCalls(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                this.dialog.setMessage("Placing order...");
                this.dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            placeorder(type);
            return null;
        }

        protected void onPostExecute(final Boolean result) {

        }
        private void placeorder(String type) {

            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 11000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 12000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

            String completeUrl = MainActivity.logOnUrl + "/theatrodemo/order";
            try {
                HttpPost httpPost = new HttpPost(completeUrl);
                String credentials = "user" + ":" + "password";
                String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Basic " + credBase64);
                StringEntity se = new StringEntity(getJson(type).toString());
                httpPost.setEntity(se);
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                String responseReceived = EntityUtils.toString(entity);
                if (response.getStatusLine().getStatusCode() == 201) {
                    JSONObject jsonObject1 = new JSONObject(responseReceived);
                    String message = jsonObject1.getString("message");
                    String orderId = jsonObject1.getString("orderId");
                    Intent intent = new Intent(ItemListActiity.this, MapsActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                } else {
                    ItemListActiity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error, Please try again", Toast.LENGTH_LONG).show();

                        }
                    });
                       }

            } catch (IllegalArgumentException e) {


            } catch (Exception jse) {
                jse.printStackTrace();

            }
            try {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
