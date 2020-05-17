package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView data;
    String api;
    String defaultPath = "all";
    String path = "countries/";
    EditText input;
    Button bt;
    String inpuText;
    String fullPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = findViewById(R.id.data);
        input = findViewById(R.id.editText);
        bt = findViewById(R.id.button);
        api = "https://disease.sh/v2/";

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setText("");
                inpuText = input.getText().toString().trim();
                if( ! inpuText.equals("")){
                    new GetData().execute();
                }

            }
        });

    }

    class GetData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Wait a second",Toast.LENGTH_SHORT).show();

            if( inpuText.equals("world")){
                fullPath = api + defaultPath;
            }
            else
                fullPath = api + path + inpuText;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                data.setText(json.get("cases").toString());

    //countries/India
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
