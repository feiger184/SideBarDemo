package com.ghf.sidebardemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ghf.sidebardemo.ui.ContactsActivity;
import com.ghf.sidebardemo.ui.RightPositionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openRightPositionSample(View view) {
        Intent intent = new Intent(this, RightPositionActivity.class);
        startActivity(intent);

    }

    public void openCustomIndexSample(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);

    }

}
