package com.example.angelaq.textwhen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class form extends Activity {

    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        but = (Button)findViewById(R.id.button);
    }

    public void clicked(View v) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }
}
