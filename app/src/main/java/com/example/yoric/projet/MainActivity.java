package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.yoric.projet.asynctask.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GetResult.ICallBack, View.OnClickListener {

    private Button bt_rechercher;
    private EditText et_recherche;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rechercher = (Button) findViewById(R.id.bt_rechercher);
        bt_rechercher.setOnClickListener(this);

        //Liste déroulante
        spinner = (Spinner) findViewById(R.id.s_choix);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.s_choix, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        et_recherche= (EditText) findViewById(R.id.et_recherche);

        GetResult task = new GetResult();
        task.setCallback(this);

        task.execute(
                et_recherche.getText().toString(),
                spinner.getSelectedItemPosition()+""
        );
    }

    @Override
    public void parseData(String string) throws JSONException {
        Log.i("STRING_GET", string);
        //JSONObject object = new JSONObject(string);
    }
}
