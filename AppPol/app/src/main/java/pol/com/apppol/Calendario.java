package pol.com.apppol;

import android.support.v7.app.AppCompatActivity;

/**
 * Crea un calendario y carga los eventos
 */

public class Calendario extends AppCompatActivity { //public class MostrarDatos
    /*
    Uri uri;
    private static final String MY_ACCOUNT_NAME ="Vacunas" ;
    protected SQLiteDatabase db;
    ArrayList<Vacuna> elemento;
    protected List<Hijo> hijoList = new ArrayList<Hijo>();
    public int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        //Inicializa el spinner a partir del spinner definido en la vista
        llenar_lv();

        DB nuevo = new DB(getApplicationContext(), "DBHijos", null, 1);
        SQLiteDatabase nuevo1 = nuevo.getWritableDatabase();

        Cursor cuenta = nuevo1.rawQuery("SELECT count(*) from OK", null);
        cuenta.moveToFirst();
        int c = cuenta.getInt(cuenta.getColumnIndex("count(*)"));
        if (c == 0) {
            CrearCalendario();
            Long calID= getCalendarId();
            String nombre="";
            String titulo="";
            String descripcion="";
            String fecha="";
            for (int w=1;w<hijoList.size();w++) {
                nombre = hijoList.get(w).getNombres();
                DB x = new DB(getApplicationContext(), "DBHijos", null, 1);
                elemento = x.llenar_lv(4, w);


                for (int xe = 0; xe < 24; xe++) {
                    titulo = elemento.get(xe).getNombre();
                    descripcion = elemento.get(xe).getDescripcion() + ", para " + nombre;
                    fecha = elemento.get(xe).getFecha();
                    System.out.println("****");
                    System.out.println(fecha);
                    System.out.println("****");
                    CargarEve(calID, titulo, descripcion, fecha);
                }
            }
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println(nuevo.guardar("listo"));
            System.out.println("----------------");
            System.out.println("----------------");
        }
    }

    public void llenar_lv() {
        DB x = new DB(getApplicationContext(),"DBHijos",null,1);
        db = x.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Datos", null);
        c.moveToFirst();
        Hijo hijo = new Hijo();
        hijo.setId_hijo(0);
        hijo.setNombres("Nombres  ");
        hijo.setFecha_nacimiento(" Nacimiento ");
        hijo.setSexo("Sexo");
        nombreClientes.add(0, "Selecionar Hij@");
        hijoList.add(0, hijo);

        //Guarda los clientes en una lista
        for (int i = 1; i <= c.getCount(); i++){
            hijo= new Hijo();
            hijo.setId_hijo(c.getInt(c.getColumnIndex("id_usuario")));
            hijo.setCedulaRuc(c.getInt(c.getColumnIndex("cedula")));
            hijo.setNombres(c.getString(c.getColumnIndex("nombres")));
            hijo.setApellidos(c.getString(c.getColumnIndex("apellidos")));
            hijo.setLugar_nacimiento(c.getString(c.getColumnIndex("lugar_nacimiento")));
            hijo.setFecha_nacimiento(c.getString(c.getColumnIndex("fecha_nacimiento")));
            hijo.setNacionalidad(c.getString(c.getColumnIndex("nacionalidad")));
            hijo.setSexo(c.getString(c.getColumnIndex("sexo")));
            hijo.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
            nombreClientes.add(i, c.getString(c.getColumnIndex("nombres")));
            hijoList.add(i, hijo);
            c.moveToNext();//salta a la siguiente fila de resultados
        }
        c.close();
    }

    public void CrearCalendario(){

        ContentValues values = new ContentValues();
        values.put(
                CalendarContract.Calendars.ACCOUNT_NAME,
                MY_ACCOUNT_NAME);
        values.put(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(
                CalendarContract.Calendars.NAME,
                "Vacunas Calendar");
        values.put(
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                "Vacunas Calendar");
        values.put(
                CalendarContract.Calendars.CALENDAR_COLOR,
                0xffff0000);
        values.put(
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(
                CalendarContract.Calendars.OWNER_ACCOUNT,
                "vacunas@googlemail.com");
        values.put(
                CalendarContract.Calendars.CALENDAR_TIME_ZONE,
                "Paraguay/Asunción");
        values.put(
                CalendarContract.Calendars.SYNC_EVENTS,
                1);
        Uri.Builder builder =
                CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_NAME,
                "com.grokkingandroid");
        builder.appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(
                CalendarContract.CALLER_IS_SYNCADAPTER,
                "true");
        uri = getContentResolver().insert(builder.build(), values);
        System.out.println("¨**************");
        System.out.println("¨**************");
        System.out.println(getCalendarId());
        System.out.println("¨**************");
        System.out.println("¨**************");
    }

    private long getCalendarId() {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        CalendarContract.Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        MY_ACCOUNT_NAME,
                        CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor = null;
        try{
            cursor =
                    getContentResolver().
                            query(
                                    CalendarContract.Calendars.CONTENT_URI,
                                    projection,
                                    selection,
                                    selArgs,
                                    null);
        }catch (SecurityException e){
            System.out.println("algo salio mal");
        }

        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }

    public void CargarEve(long calID,String Title,String Descripcion,String fecha){
        String[] fechArray = fecha.split("/");

        int dia = Integer.valueOf(fechArray[0]);
        int mes = Integer.valueOf(fechArray[1]) - 1;
        int anio = Integer.valueOf(fechArray[2]);

        long startMillis = 0;
        long endMillis = 0;

        Calendar cal = new GregorianCalendar(anio, mes, dia);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start=cal.getTimeInMillis();
        cal = new GregorianCalendar(anio, mes, dia);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long end=cal.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, start);
        values.put(CalendarContract.Events.DTEND, end);

        values.put(CalendarContract.Events.TITLE, Title);
        values.put(CalendarContract.Events.EVENT_LOCATION, "Paraguay");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Berlin");
        values.put(CalendarContract.Events.DESCRIPTION, Descripcion);
        // reasonable defaults exist:
        values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS,
                CalendarContract.Events.STATUS_CONFIRMED);
        values.put(CalendarContract.Events.ALL_DAY, 1);
        ContentResolver cr = getContentResolver();
        try{
            uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        }catch (SecurityException e){
            System.out.println("algo anda mal jeje");
        }

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());

        cr = getContentResolver();
        values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 2880);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        try{
            uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        }catch (SecurityException e){
            System.out.println( "algo anda mal");

        }
        values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 1140);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        try{
            uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        }catch (SecurityException e){
            System.out.println( "algo anda mal");

        }
        values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 60);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        try{
            uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        }catch (SecurityException e){
            System.out.println( "algo anda mal");
        }
    }
*/
}

/*
En DB.java
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS Datos (id_hijo INTEGER PRIMARY KEY," +
                    "cedula INTEGER, nombres TEXT, apellidos TEXT,lugar_nacimiento TEXT, fecha_nacimiento TEXT," +
                    "sexo TEXT, nacionalidad TEXT,id_usuario INTEGER);");

            db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas ( id_vacuna integer not null , nombre text not null, " +
                    "dosis intenger, edad integer, fecha text, lote text, nombre_medico text, descripcion text," +
                    "id_hijo integer not null," +
                    "aplicada INTEGER," +
                    "PRIMARY KEY (id_vacuna,id_hijo)," +
                    "FOREIGN KEY(id_hijo) REFERENCES Datos(id_hijos));");

            Cursor cuenta = db.rawQuery("SELECT count(*) from Datos", null);
            cuenta.moveToFirst();
            int c = cuenta.getInt(cuenta.getColumnIndex("count(*)"));
            if (c == 0) {
                ContentValues valores = new ContentValues();

                valores.put("id_hijo", "1");
                valores.put("cedula","1234");
                valores.put("nombres", "Juan gregorio");
                valores.put("apellidos", "Caceres Pérez");
                valores.put("lugar_nacimiento", "asuncion");
                valores.put("fecha_nacimiento", "08/03/2016");
                valores.put("sexo", "M");
                valores.put("nacionalidad", "Paraguaya");
                valores.put("id_usuario", "1");
                db.insert("Datos", null, valores);
                valores = new ContentValues();
                valores.put("id_hijo", "2");
                valores.put("cedula","123");
                valores.put("nombres", "maria juana");
                valores.put("apellidos", "Caceres Pérez");
                valores.put("lugar_nacimiento", "asuncion");
                valores.put("fecha_nacimiento", "08/03/2016");
                valores.put("sexo", "f");
                valores.put("nacionalidad", "Paraguaya");
                valores.put("id_usuario", "1");
                db.insert("Datos", null, valores);
                insertarDatos(db);
            }
            Cursor jeje = db.rawQuery("SELECT * from Vacunas", null);
            int contar=0;
            if(jeje.moveToFirst()){
                do{
                    contar++;
                }while(jeje.moveToNext());
            }
            System.out.println("*********");
            System.out.println("*********");
            System.out.println(contar);
            System.out.println("*********");
            System.out.println("*********");
        } catch (Exception e) {
            System.out.println("no se cargo nada");
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS OK(BANDERA TEXT);");
    }

    public ArrayList llenar_lv(int orden, int id_hijo){
        ArrayList<Vacuna> lista = new ArrayList<>();
        String q = null;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor registros;
        if (orden==0){
            q = "SELECT * FROM Vacunas where aplicada=2 and id_hijo=?;";
            registros= database.rawQuery(q,null);
        }
        else {
            if (orden == 1) {
                q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
                registros = database.rawQuery(q, new String[]{"0", Integer.toString(id_hijo)});
            } else {
                if (orden == 2) {
                    q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
                    registros = database.rawQuery(q, new String[]{"1", Integer.toString(id_hijo)});
                } else {
                    if (orden == 3) {
                    q = "SELECT * FROM Vacunas where id_hijo=? order by nombre;";
                    registros = database.rawQuery(q, new String[]{Integer.toString(id_hijo)});
                } else {
                    q = "SELECT * FROM Vacunas where id_hijo=?;";
                    registros = database.rawQuery(q, new String[]{Integer.toString(id_hijo)});
                }
            }
        }
    }

    Vacuna vac;
    if(registros.moveToFirst()){
        do{
            vac=new Vacuna();
            vac.setId_vacuna(registros.getInt(0));
            vac.setNombre(registros.getString(1));
            vac.setFecha(registros.getString(4));
            vac.setDescripcion(registros.getString(7));
            vac.setAplicada(registros.getInt(9));
            lista.add(vac);
        }while(registros.moveToNext());
    }
        return lista;
    }

    public ArrayList llenar(int id_hijo){
        ArrayList<String> lista = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos where id_hijo=?";
        Cursor registros = database.rawQuery(q,new String[]{Integer.toString(id_hijo)});
        if(registros.moveToFirst()){
            do{
                lista.add("ID HIJO:  "+registros.getString(0));
                lista.add("CEDULA:  "+registros.getString(1));
                lista.add("NOMBRES:  "+registros.getString(2));
                lista.add("APELLIDOS:  "+registros.getString(3));
                lista.add("LUGAR NACIMIENTO: "+registros.getString(4));
                lista.add("FECHA NACIMIENTO: "+registros.getString(5));
                lista.add("SEXO:  "+registros.getString(6));
                lista.add("NACIONAD:  "+registros.getString(7));
                lista.add("ID USUARIO:  "+registros.getString(8));
            }while(registros.moveToNext());
        }
        return lista;
    }

    public String guardar(String bandera){
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("BANDERA",bandera);
        try {
            database.insertOrThrow("OK",null,contenedor);
            mensaje="Ingresado Correctamente";
        }catch (SQLException e){
            mensaje="No Ingresado";
        }
        database.close();
        return mensaje;
    }
 */