package pol.com.apppol.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static pol.com.apppol.data.EstructuraHijo.HijoEntry;

/*
 * Manejador de la base de datos
 */

public class DbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Hijo.db";
    protected ArrayList<Hijo> hijoList = new ArrayList<>();
    protected ArrayList<Vacuna> vacList = new ArrayList<>();
    public static String id_usuario;
    public static String servidor = "http://192.168.43.11:8080";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String id) {
        super(context, name, factory, version);
        this.id_usuario = id;
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
                            + HijoEntry.DEPARTAMENTO + " TEXT NOT NULL,"
                            + HijoEntry.MUNICIPIO + " TEXT NOT NULL,"
                            + HijoEntry.BARRIO + " TEXT NOT NULL,"
                            + HijoEntry.REFERENCIA_DOMICILIO + " TEXT NOT NULL,"
                            + HijoEntry.RESPONSABLE + " TEXT NOT NULL,"
                            + HijoEntry.TELEFONO_CONTACTO + " TEXT NOT NULL,"
                            + HijoEntry.SEGURO_MEDICO + " TEXT NOT NULL,"
                            + HijoEntry.ALERGIAS + " TEXT NOT NULL,"
                            + "UNIQUE (" + HijoEntry.ID + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas (" +
                            "id_vacuna integer not null, " +
                            "nombre text not null, " +
                            "dosis intenger not null," +
                            "edad integer," +
                            "fecha text," +
                            "lote text," +
                            "nombre_medico text," +
                            "descripcion text," +
                            "id_hijo TEXT not null," +
                            "aplicada INTEGER," +
                            "PRIMARY KEY (id_vacuna, id_hijo,dosis)," +
                            "FOREIGN KEY(id_hijo) REFERENCES hijo(id_hijo));");

            cargarHijos(db);
            cargarVacunas(db);
        }catch (Exception e){
            System.out.println("Error al cargar tablas");
        }
    }
    private void obtenerHijosWS() {
        String linkService = servidor + "/RestService/webresources/usuario/gethijos?userId=";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService+id_usuario);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray respJSON = new JSONArray(respStr);
            for (int i = 0; i <respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                Hijo hijo;
                hijo = new Hijo();
                hijo.setId(obj.getString("id"));
                hijo.setNombre(obj.getString("nombre"));
                hijo.setApellido(obj.getString("apellido"));
                hijo.setFecha_nacimiento(obj.getString("fechaNacimiento"));
                hijo.setLugar_nacimiento(obj.getString("lugarNacimiento"));
                hijo.setBarrio(obj.getString("barrio"));
                hijo.setDepartamento(obj.getString("departamento"));
                hijo.setDireccion(obj.getString("direccion"));
                hijo.setMunicipio(obj.getString("municipio"));
                hijo.setReferencia_domicilio(obj.getString("referenciaDomicilio"));
                hijo.setTelefono_contacto(obj.getString("telefonoContacto"));
                hijo.setResponsable(obj.getString("responsable"));
                hijo.setSeguro_medico(obj.getString("seguroMedico"));
                hijo.setAlergias(obj.getString("alergia"));
                hijo.setNacionalidad(obj.getString("nacionalidad"));
                hijo.setSexo(obj.getString("sexo"));
                hijoList.add(i, hijo);
            }
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
    }

    private void obtenerRegistroWS() {
        String linkService = servidor + "/RestService/webresources/usuario/getregistro?userId=";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService+id_usuario);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray respJSON = new JSONArray(respStr);
            for (int i = 0; i <respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                Vacuna vac = new Vacuna();
                vac.setId_hijo(obj.getString("hijoId"));
                vac.setEdad(obj.getInt("edad"));
                vac.setId_vacuna(obj.getInt("vacunaId"));
                vac.setDosis(obj.getInt("dosis"));
                vac.setLote(obj.getString("lote"));
                vac.setNombre(obj.getString("nombreVacuna"));
                vac.setNombre_medico(obj.getString("responsable"));
                vac.setDescripcion("");
                vac.setAplicada(obj.getInt("estado"));
                vac.setFecha(obj.getString("fecha"));
                vacList.add(vac);
            }
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
    }

    private void cargarHijos(SQLiteDatabase sqLiteDatabase) {
        obtenerHijosWS();
        for (int i = 0; i <hijoList.size(); i++) {
            insertarHijo(sqLiteDatabase, new Hijo(hijoList.get(i).getId(),
                    hijoList.get(i).getNombre(), hijoList.get(i).getApellido(),hijoList.get(i).getFecha_nacimiento(),
                    hijoList.get(i).getLugar_nacimiento(), hijoList.get(i).getSexo(), hijoList.get(i).getNacionalidad(),
                    hijoList.get(i).getDireccion(), hijoList.get(i).getDepartamento(), hijoList.get(i).getMunicipio(),
                    hijoList.get(i).getBarrio(), hijoList.get(i).getReferencia_domicilio(), hijoList.get(i).getResponsable(),
                    hijoList.get(i).getTelefono_contacto(), hijoList.get(i).getSeguro_medico(), hijoList.get(i).getAlergias()));
        }
    }

    private void cargarVacunas(SQLiteDatabase sqLiteDatabase) {
        obtenerRegistroWS();
        //Vacunas para hijo id 1
        for (int i = 0; i < vacList.size(); i++) {
            insertarVacuna(sqLiteDatabase, new Vacuna(vacList.get(i).getId_vacuna(),
                    vacList.get(i).getNombre(),vacList.get(i).getDosis(),vacList.get(i).getEdad(),
                    vacList.get(i).getFecha(),vacList.get(i).getLote(),vacList.get(i).getNombre_medico(),
                    vacList.get(i).getDescripcion(),vacList.get(i).getId_hijo(),vacList.get(i).getAplicada()));
        }
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
            q = "SELECT * FROM Vacunas where id_hijo=?;";
            registros= database.rawQuery(q, new String[]{id_hijo});
        } else if (orden == 1) {
            q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
            registros = database.rawQuery(q, new String[]{"1", id_hijo});
        } else if (orden == 2) {
            q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
            registros = database.rawQuery(q, new String[]{"0", id_hijo});
        } else if (orden == 3) {
            q = "SELECT * FROM Vacunas where id_hijo=? order by nombre;";
            registros = database.rawQuery(q, new String[]{id_hijo});
        } else {
            q = "SELECT * FROM Vacunas where id_hijo=? order by fecha;";
            registros = database.rawQuery(q, new String[]{id_hijo});
        }

        Vacuna vac;
        if(registros.moveToFirst()){
            do{
                vac=new Vacuna();
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
