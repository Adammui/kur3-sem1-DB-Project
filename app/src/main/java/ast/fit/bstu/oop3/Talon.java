package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
                    String speciality = null, docname= "";
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("prof_name"));
                    String doc = cursor.getString(cursor.getColumnIndexOrThrow("iddoc"));
                    Cursor cursor1= db.rawQuery("select speciality, doctor_name from " + DbHelper.DOCTOR_TABLE +
                            " Where iddoc = ?" , new String[]{doc});
                    if (cursor1.moveToFirst()) {
                        speciality = cursor1.getString(0);
                        docname = cursor1.getString(1);
                    }
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    String town = cursor.getString(cursor.getColumnIndexOrThrow("town"));
                    String an = cursor.getString(cursor.getColumnIndexOrThrow("analysis"));
                    t.setText(name + " Записан(а) в больницу г. " + town +
                            " к врачу-" + speciality + "у " + docname + ". Запись назначена на " +
                            time +" "+ new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()) + " " + an);
                }
            }
    }
}