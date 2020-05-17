package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView total, recover, death, todayDeath, population;
    String api;
    String defaultPath = "all";
    String path = "countries/";
    EditText input;
    Button find;
    String inputText;
    String fullPath;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        total = findViewById(R.id.totaldata);
        recover = findViewById(R.id.recoverdata);
        death = findViewById(R.id.deathData);
        population = findViewById(R.id.populationdata);
        input = findViewById(R.id.editText);
        find = findViewById(R.id.button);
        linearLayout = findViewById(R.id.data);
        linearLayout.setVisibility(View.INVISIBLE);
        todayDeath = findViewById(R.id.todaydeathdata);

        api = "https://disease.sh/v2/";

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = input.getText().toString().trim();
                if( ! inputText.equals("")){
                    new GetData().execute();
                }

            }
        });

    }

    class GetData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Wait a second",Toast.LENGTH_SHORT).show();

            if( inputText.equals("world")){
                fullPath = api + defaultPath;
            }
            else
                fullPath = api + path + inputText;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                total.setText(json.get("cases").toString());
                recover.setText(json.get("recovered").toString());
                death.setText(json.get("deaths").toString());
                population.setText(json.get("population").toString());
                todayDeath.setText(json.get("todayDeaths").toString());
                linearLayout.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                Log.d("WHy this Meesage",e.getMessage());
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String st =  HttpRequest.excuteGet(fullPath);
            return st;
        }
    }
}
