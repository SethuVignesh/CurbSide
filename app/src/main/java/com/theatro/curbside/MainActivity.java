package com.theatro.curbside;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String RESUME = "resume";
    public static final String SERVER_PUSH_MSG = "server_push_msg";

    public static String logOnUrl;
    //="wss://demos.kaazing.com/echo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemListActiity.class);
                startActivity(intent);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUI();
            }
        });
        logOnUrl = Utility.getLogOn(MainActivity.this);
        if (logOnUrl == null || Utility.getName(MainActivity.this) == null || Utility.getRole(MainActivity.this) == null) {
            imageView.performClick();
        }
    }



    EditText url, userName,email;
    String urlText, userNameText,emailText;
    String roleText;

    public void showDialogUI() {

        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.custom_dialog);

        dialog.setTitle("Settings");
        url = (EditText) dialog.findViewById(R.id.editText1);
        userName = (EditText) dialog.findViewById(R.id.editText2);
        email = (EditText) dialog.findViewById(R.id.editText3);


        final String[] role = {"Select role", "VIP", "Normal"};
        Spinner spin = (Spinner) dialog.findViewById(R.id.spinnerRole);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), role[i], Toast.LENGTH_LONG).show();
                roleText = role[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, role);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        if (logOnUrl != null) {
            url.setText(logOnUrl);
        }
        if (Utility.getName(MainActivity.this) != null) {
            userName.setText(Utility.getName(MainActivity.this));
        }
        if (Utility.getRole(MainActivity.this) != null) {
            roleText = Utility.getRole(MainActivity.this);
            switch (roleText){
                case "Select role":
                    spin.setSelection(0);
                    break;
                case "VIP":
                    spin.setSelection(1);
                    break;
                case "Normal":
                    spin.setSelection(2);
                    break;
            }

        }

        dialog.show();

        Button submit = (Button) dialog.findViewById(R.id.btnSubmit);
//        selectRole = (Button) dialog.findViewById(R.id.btnRole);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                urlText = url.getText().toString().trim();
                if (Utility.validateUrl(urlText)) {
                    logOnUrl = urlText;
                    Utility.saveLogOn(logOnUrl, MainActivity.this);

                } else {
                    url.setError("invalid url");
                    return;
                }
                userNameText = userName.getText().toString().trim();
                if (userNameText != null && userNameText.isEmpty() == false) {
                    Utility.saveName(userNameText, MainActivity.this);


                } else {
                    userName.setError("invalid name");
                    return;
                }
                emailText = email.getText().toString().trim();

                if(emailText==null || emailText.isEmpty()){}else {
                    if(Utility.emailValidator(emailText.toString())){
                        Utility.saveMail(emailText, MainActivity.this);
                    }else{
                        email.setError("invalid mail");
                        return;
                    }
                }
                if (roleText != null && roleText.isEmpty() == false && roleText.equalsIgnoreCase("Select role") == false) {
                    Utility.saveRole(roleText, MainActivity.this);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid role", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
