package ast.fit.bstu.oop3;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int SCHEMA = 1;
    private static final String DATABASE_NAME = "ClinicDB";
    static final String DOCTOR_TABLE = "Doctor";
    private static final String TALON_TABLE = "Talon";
    private static final String PROFILE_TABLE = "Profile";

    private static DbHelper instance = null;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    public static DbHelper getInstance(Context context) {
        if(instance == null) instance = new DbHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DOCTOR_TABLE + " (                    "
                + "iddoc integer primary key autoincrement not null,"
                + "speciality text not null,                               "
                + "doctor_name text not null  );"
        );

        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('окулист','Козлова Анастасия Станиславовна');");
        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('окулист','Тереньтьев Анатолий Иванович');");
        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('окулист','Станько Мирослав Прокофьевич');");
        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('хирург','Cавченко Василий Инокентьевич');");
        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('ЛОР','Хоружий Владимир Владимирович');");
        db.execSQL("INSERT INTO " + DOCTOR_TABLE +
                " ( speciality , doctor_name ) " +
                " VALUES ('ЛОР','Светлая Мария Алексеевна');");

        db.execSQL("create table " + PROFILE_TABLE + " (                                     "
                + "idprof integer primary key autoincrement not null,                 "
                + "name text,                                                  "
                + "surname text );"
        );
        db.execSQL("create table " + TALON_TABLE + " (                           "
                + "idtalon integer primary key autoincrement not null,       "
                + "prof_name text not null,                                   "
                + "iddoc integer not null,                                         "
                + "town text not null,                                     "
                + "time text not null,                                      "
                + "analysis text not null,                                    "
               // + "foreign key(prof_name) references " + PROFILE_TABLE + "(prof_name) "
               // + " on delete cascade on update cascade,
                        + "foreign key(iddoc) references " + DOCTOR_TABLE + "(iddoc) "
                 + " on delete cascade on update cascade );"
        );
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + DOCTOR_TABLE);
        db.execSQL("drop table if exists " + PROFILE_TABLE);
        db.execSQL("drop table if exists " + TALON_TABLE);
        onCreate(db);
    }
}
