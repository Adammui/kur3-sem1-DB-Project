package ast.fit.bstu.oop3;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talon);
        TextView t= findViewById(R.id.texto);
        String tickets = super.getFilesDir()+ "lab4-tickets.json";
        Bundle arg=getIntent().getExtras();
        int id = (int) arg.get("linenumber");

        File file = new File(tickets);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int i = 0;
            String[] temps = new String[0];
            while (line != null) {
                if (i == id) {
                    temps = line.split("\\s*([-]|[-]|[-]|[-]|[-]|[-]|[;])\\s*");
                }
                line = reader.readLine();
                i += 1;
            }
            String an=null;
        if(temps[6]=="1") {
            an=" Анализы требуются";
        } else {
            an=" Анализы не требуются";
        }
            t.setText(temps[0]+" "+temps[1]+" Записан(а) в больницу г. "+temps[2]+
                    " к врачу-"+temps[3]+"у "+temps[4]+". Запись назначена на "+
                    temps[5]+".00 "+ new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime())+" "+ an);
        }
        catch (IOException e)
        {
                e.printStackTrace();
        }
    }
}