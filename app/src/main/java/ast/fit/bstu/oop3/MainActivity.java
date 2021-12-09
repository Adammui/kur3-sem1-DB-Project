package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String town;
    private EditText sur,name;
    private RadioGroup gr;
    private RadioButton M,B,G;
    private Bundle arg;
    private int time, analysis, doc, docname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            sur=findViewById(R.id.surname);
            name=findViewById(R.id.name);
            gr=findViewById(R.id.radio);
            M=findViewById(R.id.radioM); B=findViewById(R.id.radioB);G=findViewById(R.id.radioG);
            arg=getIntent().getExtras();
            sur.setText(arg.get("surname").toString());
            name.setText(arg.get("name").toString());
            if(arg.get("town").toString().equals("Минск")) M.toggle();
            if(arg.get("town").toString().equals("Брест")) B.toggle();
            if(arg.get("town").toString().equals("Гродно")) G.toggle();

            doc= arg.getInt("doc");
            docname=arg.getInt("docname");
            time= arg.getInt("time");
            analysis=arg.getInt("analysis");
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void goto2(View view)
    {
        sur=findViewById(R.id.surname);
        name=findViewById(R.id.name);
        gr=findViewById(R.id.radio);
        M=findViewById(R.id.radioM); B=findViewById(R.id.radioB);G=findViewById(R.id.radioG);

        if(gr.getCheckedRadioButtonId()!=-1 && !sur.getText().toString().isEmpty() && !name.getText().toString().isEmpty()) {
            RadioButton r = findViewById(gr.getCheckedRadioButtonId());
            town = r.getText().toString();
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("town", town);
            intent.putExtra("surname", sur.getText().toString());
            intent.putExtra("name", name.getText().toString());

            intent.putExtra("doc", doc );
            intent.putExtra("docname", docname );
            intent.putExtra("time", time );
            intent.putExtra("analysis", analysis );

            startActivity(intent);
        }
        else {
            TextView r = findViewById(R.id.warn);
            r.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState1) {
        super.onSaveInstanceState(savedInstanceState1);
        sur = findViewById(R.id.surname);
        name = findViewById(R.id.name);
        gr = findViewById(R.id.radio);
        M = findViewById(R.id.radioM);
        B = findViewById(R.id.radioB);
        G = findViewById(R.id.radioG);

        savedInstanceState1.putString("name", name.getText().toString());
        savedInstanceState1.putString("surname", sur.getText().toString());
        if (gr.getCheckedRadioButtonId() != -1 && !sur.getText().toString().isEmpty() && !name.getText().toString().isEmpty()) {
            RadioButton r = findViewById(gr.getCheckedRadioButtonId());
            town = r.getText().toString();
            savedInstanceState1.putString("town", town);
            Log.d("", "save");
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState1) {
        super.onRestoreInstanceState(savedInstanceState1);
        sur=findViewById(R.id.surname);
        name=findViewById(R.id.name);
        gr=findViewById(R.id.radio);
        M=findViewById(R.id.radioM); B=findViewById(R.id.radioB);G=findViewById(R.id.radioG);

        sur.setText(savedInstanceState1.get("surname").toString());
        name.setText(savedInstanceState1.get("name").toString());
        if(savedInstanceState1.get("town").toString().equals("Минск")) M.toggle();
        if(savedInstanceState1.get("town").toString().equals("Брест")) B.toggle();
        if(savedInstanceState1.get("town").toString().equals("Гродно")) G.toggle();
        Log.d("","restore");
    }
}