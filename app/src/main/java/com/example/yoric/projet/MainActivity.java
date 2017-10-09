package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yoric.projet.asynctask.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GetResult.ICallBack, View.OnClickListener {

    private Button bt_rechercher;
    private EditText et_recherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rechercher = (Button) findViewById(R.id.bt_rechercher);
        bt_rechercher.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        et_recherche= (EditText) findViewById(R.id.et_recherche);

        GetResult task = new GetResult();
        task.setCallback(this);

        task.execute(
                et_recherche.getText().toString()
        );
    }

    @Override
    public void parseData(String string) throws JSONException {
        Log.i("STRING_GET", string);
        JSONObject object = new JSONObject(string);
    }
}
