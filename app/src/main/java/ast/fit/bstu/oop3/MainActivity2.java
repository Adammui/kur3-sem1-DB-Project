package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private String name,surname,town;
    private int spin11, spin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        final Spinner spin= (Spinner) findViewById(R.id.spinner),spin1= findViewById(R.id.spinnerName);
        spin.setOnItemSelectedListener(this);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        SwitchCompat sw=findViewById(R.id.switch1);
        TextView info=findViewById(R.id.info);

        Bundle arg=getIntent().getExtras();
        name=arg.get("name").toString();
        surname=arg.get("surname").toString();
        town=arg.get("town").toString();
        info.setText(name+" "+surname+" г."+town);

        try{
            spin.setSelection(arg.getInt("doc"));
            seekBar.setProgress(arg.getInt("time"));
            if(arg.getInt("analysis")==1)
                sw.toggle();
            spin2=arg.getInt("docname");
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    public void back(View view)
    {
        Spinner spin= findViewById(R.id.spinner), spin1= findViewById(R.id.spinnerName);
        SeekBar bar= findViewById(R.id.seekBar);
        SwitchCompat sw=findViewById(R.id.switch1);
        int swit=0;
        if(sw.isChecked()) swit=1;

        Intent intent =new Intent(this, MainActivity.class);
        intent.putExtra("town", town);
        intent.putExtra("surname", surname);
        intent.putExtra("name", name);
        intent.putExtra("doc", spin.getSelectedItemPosition() );
        intent.putExtra("docname", spin1.getSelectedItemPosition() );
        intent.putExtra("time", bar.getProgress() );
        intent.putExtra("analysis", swit );
        startActivity(intent);
        onStop();
    }

    public void writeDown(View view)
    {
        Spinner spin= findViewById(R.id.spinner), spin1= findViewById(R.id.spinnerName);
        SeekBar bar= findViewById(R.id.seekBar);
        SwitchCompat sw=findViewById(R.id.switch1);int swit=0;
        if(sw.isChecked()) swit=1;
        try {
            createSave(String.format( name+"-"+ surname+"-"+ town+"-"+ spin.getSelectedItem().toString()+"-"+ spin1.getSelectedItem().toString()+"-"+ bar.getProgress()+"-"+ swit+";" ),super.getFilesDir()+ "lab4-tickets.json");
            Toast toast= Toast.makeText(getApplicationContext(),"Сохранено",Toast.LENGTH_LONG);
            toast.show();
            Intent intent =new Intent(this, MainPage.class);
            startActivity(intent);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void createSave(String str,String path) throws IOException {

        try {
            PrintWriter prWr = new PrintWriter(new BufferedWriter(new FileWriter( path, true)));
            prWr.println(str);
            prWr.close();
        }
        catch(IOException ex)
        {
            Log.d("",""+ex.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinn= findViewById(R.id.spinnerName);
        final Spinner spin= (Spinner) findViewById(R.id.spinner);
        String[] eye = { "Козлова Анастасия Станиславовна", "Тереньтьев Анатолий Иванович", "Станько Мирослав Прокофьевич"};
        String[] hir = { "Cавченко Василий Инокентьевич"};
        String[] lor = { "Хоружий Владимир Владимирович","Светлая Мария Алексеевна"};
        if (spin.getSelectedItemPosition()==2) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lor );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
        }
        if (spin.getSelectedItemPosition()==0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eye );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
        }
        if (spin.getSelectedItemPosition()==1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hir );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
        }
        try{
            spinn.setSelection(spin2);
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SeekBar bar= findViewById(R.id.seekBar);
        TextView t= findViewById(R.id.texthint);
        t.setText(bar.getProgress()+".00");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("","destroy");
    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.d("","pause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("","restart");
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.d("","resume");
    }
    @Override
    protected void onStop() {
        super.onStop();

        Log.d("","stop");
    }
    @Override
    protected void onStart() {
        super.onStart();

        Log.d("","start");
    }
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Spinner spin= findViewById(R.id.spinner), spin1= findViewById(R.id.spinnerName);
        SeekBar bar= findViewById(R.id.seekBar);
        SwitchCompat sw=findViewById(R.id.switch1);
        saveInstanceState.putInt( "doc",spin.getSelectedItemPosition()) ;
        saveInstanceState.putInt( "docname", spin1.getSelectedItemPosition()) ;
        saveInstanceState.putInt("time", bar.getProgress() );
        int swit=0;
        if(sw.isChecked())
            swit=1;
        saveInstanceState.putInt("analysis",swit);
        Log.d("","save");
    }
    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        Spinner spin= findViewById(R.id.spinner), spin1= findViewById(R.id.spinnerName);
        SeekBar bar= findViewById(R.id.seekBar);SwitchCompat sw=findViewById(R.id.switch1);

        spin.setSelection(saveInstanceState.getInt("doc"));
        bar.setProgress(saveInstanceState.getInt("time"));
        if(saveInstanceState.getInt("analysis")==1)
            sw.toggle();
        spin2=saveInstanceState.getInt("docname");
        if(saveInstanceState.getInt("analysis")==1) sw.toggle();
        Log.d("","restore");
    }
}