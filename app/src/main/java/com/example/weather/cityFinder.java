package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class cityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);
        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String city = v.getText().toString().trim(); // Trim to remove leading/trailing whitespaces
                    if (!city.isEmpty()) {  // Ensure that the city is not empty
                        Intent intent = new Intent(cityFinder.this, MainActivity.class);
                        intent.putExtra("city", city);
                        startActivity(intent);
                        return true;
                    } else {
                        // Handle the case when the entered city is empty
                        Toast.makeText(cityFinder.this, "Please enter a city", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

        });

    }
}