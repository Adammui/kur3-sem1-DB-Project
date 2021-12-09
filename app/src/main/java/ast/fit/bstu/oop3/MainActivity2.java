package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private String name,surname,town;
    private int spin11, spin2;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        final Spinner spin= (Spinner) findViewById(R.id.doctor_spec),spin1= findViewById(R.id.doctor_name);
        spin.setOnItemSelectedListener(this);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        SwitchCompat sw=findViewById(R.id.switch1);
        TextView info=findViewById(R.id.info);

        db = new DbHelper(getApplicationContext()).getReadableDatabase();

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
        Spinner spin= findViewById(R.id.doctor_spec), spin1= findViewById(R.id.doctor_name);
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
        int iddocе=1;
        Spinner spinspec= findViewById(R.id.doctor_spec), spinname= findViewById(R.id.doctor_name);
        Cursor cursor = db.rawQuery("select iddoc from " + DbHelper.DOCTOR_TABLE +
                " Where doctor_name = ?" , new String[]{spinname.getSelectedItem().toString()});
        if (cursor.moveToFirst()) {
            iddocе = cursor.getInt(0);
        }
        SeekBar bar= findViewById(R.id.seekBar);
        TextView t= findViewById(R.id.texthint);
        SwitchCompat sw=findViewById(R.id.switch1);String swit="Анализы не нужны";
        if(sw.isChecked()) swit="Необходимы общие анализы";
        String time= bar.getProgress() +"";
        TalonModel talon = new TalonModel(name+surname,town, iddocе, t.getText().toString(), swit);
        //createSave(String.format( name+"-"+ surname+"-"+ town+"-"+ spinspec.getSelectedItem().toString()+"-"+ spinname.getSelectedItem().toString()+"-"+ bar.getProgress()+"-"+ swit+";" ),super.getFilesDir()+ "lab4-tickets.json");
        if(DbTalon.add(db, talon) != -1) {
            Toast.makeText(this, "Талон заказан", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        
        Intent intent =new Intent(this, MainPage.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinn= findViewById(R.id.doctor_name);
        final Spinner spin= (Spinner) findViewById(R.id.doctor_spec);
        ArrayList<String> eye=new ArrayList<String>() ,hir=new ArrayList<String>() , lor=new ArrayList<String>() ;


        if (spin.getSelectedItemPosition()==2) {
            Cursor cursor2 = DbDoct.getlor(db);
            if (cursor2.moveToFirst()) {
                do { lor.add(cursor2.getString(0)); } while (cursor2.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lor );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
        }
        if (spin.getSelectedItemPosition()==0) {
            Cursor cursor = DbDoct.getEYE(db);
            if (cursor.moveToFirst()) {
                do { eye.add(cursor.getString(0)); } while (cursor.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eye );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
        }
        if (spin.getSelectedItemPosition()==1) {
            Cursor cursor1 = DbDoct.gethir(db);
            if (cursor1.moveToFirst()) {
                do { hir.add(cursor1.getString(0)); } while (cursor1.moveToNext());
            }
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
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Spinner spin= findViewById(R.id.doctor_spec), spin1= findViewById(R.id.doctor_name);
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
        Spinner spin= findViewById(R.id.doctor_spec), spin1= findViewById(R.id.doctor_name);
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