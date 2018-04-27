package com.example.kopp.a20180426_wegpunkt_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.Serializable;

public class ListViewActivity extends AppCompatActivity {

    private android.widget.ListView listView;
    private WegepunktRepo wegepunktRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.listView);

        Serializable serializable = getIntent().getSerializableExtra("wegepunkte");

        if (serializable instanceof WegepunktRepo) {
            wegepunktRepo = (WegepunktRepo)serializable;

            final ArrayAdapter<WegePunkt> arrayAdapter = new ArrayAdapter<WegePunkt>(this, android.R.layout.simple_list_item_1, wegepunktRepo.getWegePunkte());
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/search/?api=1&query=" + arrayAdapter.getItem(position).getLatitude() + "," + arrayAdapter.getItem(position).getLongitude()));
                    startActivity(browserIntent);
                }
            });
        }


    }
}
