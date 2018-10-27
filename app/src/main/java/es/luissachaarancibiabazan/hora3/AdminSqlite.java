package es.luissachaarancibiabazan.hora3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.sql.Date;

public class AdminSqlite extends SQLiteOpenHelper {

    private static final String SQLCREATE = "CREATE TABLE \"hora\" ( `id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `numeroHoras` real NOT NULL, `fechaHoras` datetime NOT NULL, `descripcion` TEXT )";
    private static final String SQLDELETE = "DROP TABLE IF EXISTS \"hora\"";

    private static final String TABLA = "hora";
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public static final String HORAS = "numeroHoras";
    public static final String FECHA_HORAS = "fechaHoras";
    public static final String DESCRIPCION = "descripcion";


    public AdminSqlite(@Nullable Context context) {
        super(context, TABLA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDELETE);
        db.execSQL(SQLCREATE);

    }

    public void setNewHoras(String horas, String fechaHoras, String descripcion){
        db = getWritableDatabase();
        String[] columns = {HORAS, FECHA_HORAS, DESCRIPCION};

        ContentValues contentValues = new ContentValues();
        contentValues.put(HORAS, horas);
        contentValues.put(FECHA_HORAS, fechaHoras);
        contentValues.put(DESCRIPCION, descripcion);
        db.insert(TABLA, null,contentValues);
    }

    public Cursor getHoras() {
        db = getReadableDatabase();
        String[] columns = {HORAS, FECHA_HORAS};
        Cursor c = db.query(TABLA, null, null, null, null, null, FECHA_HORAS +" ASC");
        return c;
    }

    public float getMediaHoras() {
        db = getReadableDatabase();
        float media = 0;
        float sumaHoras = 0;
        int i = 0;
        String[] columns = {HORAS, FECHA_HORAS};
        Cursor c = db.query(TABLA, null, null, null, null, null, null);
        while (c.moveToNext()){
            sumaHoras = sumaHoras + c.getFloat(2);
            i++;
        }
        media = sumaHoras / i;
        return media;
    }

    public int deleteHoras(){
        db = getReadableDatabase();
        int result = db.delete(TABLA,null,null);
        return result;
    }

}
