package pol.com.apppol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pol.com.apppol.entidades.Hijo;

/**
 * Esquema y carga de datos
 */

public class DB extends SQLiteOpenHelper {
    protected List<String> nombreClientes = new ArrayList<String>();
    protected List<Hijo> hijoList = new ArrayList<Hijo>();
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  db.execSQL(Create table );
        try {
        /*db.execSQL("CREATE TABLE IF NOT EXISTS Usuarios (id_usuario INTEGER PRIMARY KEY," +
                    "nombres TEXT, apellidos TEXT, telefono TEXT, correo_electronico TEXT," +
                    "direccion TEXT);");*/
            db.execSQL("CREATE TABLE IF NOT EXISTS Datos (id_hijo INTEGER PRIMARY KEY," +
                    "cedula INTEGER, nombres TEXT, apellidos TEXT,lugar_nacimiento TEXT, fecha_nacimiento TEXT," +
                    "sexo TEXT, nacionalidad TEXT,id_usuario INTEGER);");
            //"FOREING KEY (id_usuario) REFERENCES  Usuarios(id_usuario));");

            Cursor cuenta = db.rawQuery("SELECT count(*) from Datos", null);
            cuenta.moveToFirst();
            int c = cuenta.getInt(cuenta.getColumnIndex("count(*)"));
            if (c == 0) {
                ContentValues valores = new ContentValues();

                valores.put("id_hijo", "1");
                valores.put("cedula","3706741");
                valores.put("nombres", "Osmar Alberto");
                valores.put("apellidos", "Romero");
                valores.put("lugar_nacimiento", "Lambare");
                valores.put("fecha_nacimiento", "14/08/1994");
                valores.put("sexo", "M");
                valores.put("nacionalidad", "Paraguaya");
                valores.put("id_usuario", "1");
                db.insert("Datos", null, valores);
                valores = new ContentValues();
                valores.put("id_hijo", "2");
                valores.put("cedula","4132757");
                valores.put("nombres", "Christian");
                valores.put("apellidos", "Cano Stein");
                valores.put("lugar_nacimiento", "Asuncion");
                valores.put("fecha_nacimiento", "08/03/1994");
                valores.put("sexo", "M");
                valores.put("nacionalidad", "Paraguaya");
                valores.put("id_usuario", "1");
                db.insert("Datos", null, valores);
            }
        } catch (Exception e) {
            System.out.println("No se cargo nada");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}