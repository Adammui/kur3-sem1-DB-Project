package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Talon extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talon);
        TextView t = findViewById(R.id.texto);
        String tickets = super.getFilesDir() + "lab4-tickets.json";
        Bundle arg = getIntent().getExtras();
        int id = (int) arg.get("linenumber");
            db = new DbHelper(getApplicationContext()).getReadableDatabase();
            Cursor cursor = DbTalon.getAll(db);
            int i = 0;
            while (cursor.moveToNext()) {
                i++;
                if (id == cursor.getInt(cursor.getColumnIndexOrThrow("idtalon"))) {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow("prof_name"));
                    String doc = cursor.getString(cursor.getColumnIndexOrThrow("doctor_name"));
                    String spec = cursor.getString(cursor.getColumnIndexOrThrow("spec"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    String town = cursor.getString(cursor.getColumnIndexOrThrow("town"));
                    String an = cursor.getString(cursor.getColumnIndexOrThrow("analysis"));
                    t.setText(name + " Записан(а) в больницу г. " + town +
                            " к врачу-" + spec + "у " + doc + ". Запись назначена на " +
                            time +" "+ new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()) + " " + an);
                }
            }
    }
}