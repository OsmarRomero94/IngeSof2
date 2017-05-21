package pol.com.apppol.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pol.com.apppol.hijo.HijoFragment;

import static pol.com.apppol.data.EstructuraHijo.HijoEntry;

/*
 * Manejador de la base de datos
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Hijo.db";
    //Se debe cambiar puerto y direccion ip de la red para ejecutar el proyecto
    String servidor="http://192.168.1.54:8084";
    String linkService = servidor+"/RestService/webresources/usuario/gethijos?userId=";
    String linkServiceVacuna = servidor+"/RestService/webresources/usuario/getregistro?userId=";
    int id_usuario;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) { //HijoEntry.TABLE_NAME = "hijo"
        try {
            db.execSQL(
                    "CREATE TABLE " + HijoEntry.TABLE_NAME + " ("
                            + HijoEntry._ID + " TEXT PRIMARY KEY,"
                            + HijoEntry.ID + " TEXT NOT NULL,"
                            + HijoEntry.NOMBRE + " TEXT NOT NULL,"
                            + HijoEntry.APELLIDO + " TEXT NOT NULL,"
                            + HijoEntry.FECHA_NACIMIENTO + " TEXT NOT NULL,"
                            + HijoEntry.LUGAR_NACIMIENTO + " TEXT NOT NULL,"
                            + HijoEntry.SEXO + " TEXT NOT NULL,"
                            + HijoEntry.NACIONALIDAD + " TEXT NOT NULL,"
                            + HijoEntry.DIRECCION + " TEXT NOT NULL,"
                            + HijoEntry.TELEFONO_CONTACTO + " TEXT NOT NULL,"
                            + HijoEntry.ALERGIAS + " TEXT NOT NULL,"
                            + "UNIQUE (" + HijoEntry.ID + "))");
            db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas (" +
                    "id_vacuna integer not null, " +
                    "nombre text not null, " +
                    "dosis intenger," +
                    "edad integer," +
                    "fecha text," +
                    "lote text," +
                    "nombre_medico text," +
                    "descripcion text," +
                    "id_hijo TEXT not null," +
                    "aplicada INTEGER," +
                    "PRIMARY KEY (id_vacuna, id_hijo)," +
                    "FOREIGN KEY(id_hijo) REFERENCES hijo(id_hijo));");
            cargarHijos(db);
            cargarVacunas(db);
        }catch (Exception e){
            System.out.println("Error al cargar tablas");
        }
    }

    private void cargarHijos(final SQLiteDatabase sqLiteDatabase) {
        String id = HijoFragment.getUsuario();
        id_usuario = Integer.parseInt(id);
        HttpClient httpClient = new DefaultHttpClient();
                HttpGet del =
                        new HttpGet(linkService + id_usuario);
                del.setHeader("content-type", "application/json");
                try {
                    HttpResponse resp = httpClient.execute(del);
                    String respStr = EntityUtils.toString(resp.getEntity());
                    JSONArray respJSON = new JSONArray(respStr);
                    //clientes = new String[respJSON.length()];
                    for (int i = 0; i < respJSON.length(); i++) {
                        JSONObject obj = respJSON.getJSONObject(i);
                        insertarHijo(sqLiteDatabase, new Hijo((obj.getString("id_hijo")),obj.getString("nombre"),obj.getString("apellido"), obj.getString("fecha_nacimiento"),
                                obj.getString("lugar_nacimiento"),obj.getString("sexo"),obj.getString("nacionalidad"),obj.getString("direccion"),obj.getString("telefono_contacto"),
                                obj.getString("alergia")));
                    }
                } catch (Exception ex) {
                    Log.e("ServicioRest", "Error!", ex);
                }
            }
    /*esta parte se debe probar si ejecuta y carga las vacunas en el sql lite
    * no esta guardando del json a las vacunas al sql lite*/
    private void cargarVacunas(SQLiteDatabase sqLiteDatabase) {
/*
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkServiceVacuna + id_usuario);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray respJSON = new JSONArray(respStr);
            //clientes = new String[respJSON.length()];
            for (int i = 0; i < respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                insertarVacuna(sqLiteDatabase, new Vacuna((obj.getInt("id_vacuna")),obj.getString("nombre"),obj.getInt("dosis"), 1, obj.getString("fecha"),
                        "BXAS22", obj.getString("responsable"),"descruocuib",obj.getString("id_hijo"),obj.getInt("estado")));
            }
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
*/
        int aplicada = 0;
        int noAplicada = 1;
        String nroHijo;
        //Vacunas para hijo id 1
        nroHijo = "1";
        insertarVacuna(sqLiteDatabase, new Vacuna(1, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(19, "V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(24, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi","descruocuib",nroHijo,aplicada));
        //Vacunas para hijo id 2
        nroHijo = "2";
        insertarVacuna(sqLiteDatabase, new Vacuna(1, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(4, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(5, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(15, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(17, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(19, "V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(22, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        //Vacunas para hijo id 3
        nroHijo = "3";
        insertarVacuna(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(19, "V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        //Vacunas para hijo id 4
        nroHijo = "4";
        insertarVacuna(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año",nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", nroHijo,aplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(19, "V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
        insertarVacuna(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", nroHijo,noAplicada));
    }


    private long insertarHijo(SQLiteDatabase db, Hijo lawyer) {
        return db.insert(
                HijoEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    private long insertarVacuna(SQLiteDatabase db, Vacuna vac) {
        return db.insert(
                EstructuraVac.VacunaEntry.TABLE_NAME,
                null,
                vac.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public Cursor getAllHijos() {
        return getReadableDatabase()
                .query(
                        HijoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getHijoById(String lawyerId) {
        return getReadableDatabase().query(HijoEntry.TABLE_NAME,
                null,
                HijoEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
    }

    public ArrayList llenar_lv(int orden, String id_hijo){
        ArrayList<Vacuna> lista = new ArrayList<>();
        String q;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor registros;
        if (orden==0){
            q = "SELECT * FROM Vacunas where aplicada=2 and id_hijo=?;";
            registros= database.rawQuery(q, null);
        }   //No modificar los 5 warnning
        else {
            if (orden == 1) {
                q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
                registros = database.rawQuery(q, new String[]{"0", id_hijo});

            } else {
                if (orden == 2) {
                    q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
                    registros = database.rawQuery(q, new String[]{"1", id_hijo});
                } else {
                    if (orden == 3) {
                        q = "SELECT * FROM Vacunas where id_hijo=? order by nombre;";
                        registros = database.rawQuery(q, new String[]{id_hijo});
                    } else {
                        q = "SELECT * FROM Vacunas where id_hijo=?;";
                        registros = database.rawQuery(q, new String[]{id_hijo});
                    }
                }
            }
        }

        Vacuna vac;
        if(registros.moveToFirst()){
            do{
                vac=new Vacuna();
                //vac.setId_vacuna(registros.getInt(0));
                vac.setNombre(registros.getString(1));
                vac.setFecha(registros.getString(4));
                vac.setNombre_medico(registros.getString(6));
                vac.setAplicada(registros.getInt(9));
                lista.add(vac);
            }while(registros.moveToNext());
        }
        return lista;
    }


}
