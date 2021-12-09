package ast.fit.bstu.oop3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbTalon {

    private static final String TALON_TABLE = "Talon";


    public static long add(SQLiteDatabase db, TalonModel talon) {
        ContentValues values = new ContentValues();

        values.put("prof_name", talon.getName());
        values.put("iddoc", talon.getDoctor());
        values.put("town", talon.getTown());
        values.put("time", talon.getTime());
        values.put("analysis", talon.getAn());

        return db.insert(TALON_TABLE, null, values);
    }

    public static Cursor getAll(SQLiteDatabase db) {
        return db.rawQuery("select * from " + TALON_TABLE + ";", null);
    }
}
