package ast.fit.bstu.oop3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbDoct {

    private static final String DOCTOR_TABLE = "Doctor";

    public static Cursor getAll(SQLiteDatabase db) {
        return db.rawQuery("select * from " + DOCTOR_TABLE + ";", null);
    }

    public static Cursor getEYE(SQLiteDatabase db) {
        return db.rawQuery("select doctor_name from " + DOCTOR_TABLE + " where speciality = ?", new String[]{"окулист"});
    }

    public static Cursor gethir(SQLiteDatabase db) {
        return db.rawQuery("select doctor_name from " + DOCTOR_TABLE + " where speciality = ?", new String[]{"хирург"});
    }

    public static Cursor getlor(SQLiteDatabase db) {
        return db.rawQuery("select doctor_name from " + DOCTOR_TABLE + " where speciality = ?", new String[]{"ЛОР"});
    }
}
