package com.example.angelaq.textwhen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class form extends Activity {

    public static String destination;
    public static String when;
    public static String number;

    EditText mEdit;
    EditText mEdit2;
    EditText mEdit3;

    Button goo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
//        while (R.id == null)
//        {
//            //
//        }
        goo = (Button)findViewById(R.id.ButtonSendFeedback);

        mEdit = (EditText)findViewById(R.id.editText2);;
        mEdit2 = (EditText)findViewById(R.id.editText);
        mEdit3  = (EditText)findViewById(R.id.editText3);

    }

    public void sendFeedback(View v) {
        destination = mEdit.getText().toString();
        when = mEdit2.getText().toString();
        number = mEdit3.getText().toString();
        mEdit.setText("sdkfjsldkfjljf");
        Log.v("sfsf", "dest: " + destination);
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }
}
