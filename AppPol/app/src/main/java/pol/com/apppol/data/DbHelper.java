package pol.com.apppol.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static pol.com.apppol.data.EstructuraHijo.HijoEntry;

/*
 * Manejador de la base de datos
 */

public class DbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Hijo.db";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HijoEntry.TABLE_NAME + " ("
                + HijoEntry._ID + " TEXT PRIMARY KEY,"
                + HijoEntry.ID + " TEXT NOT NULL,"
                + HijoEntry.NAME + " TEXT NOT NULL,"
                + HijoEntry.BIRTH + " TEXT NOT NULL,"
                + HijoEntry.SEX + " TEXT NOT NULL,"
                + HijoEntry.BIO + " TEXT NOT NULL,"
                + HijoEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + HijoEntry.ID + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas ( id_vacuna integer not null , nombre text not null, " +
                "dosis intenger, edad integer, fecha text, lote text, nombre_medico text, descripcion text," +
                "id_hijo TEXT not null," +
                "aplicada INTEGER," +
                "PRIMARY KEY (id_vacuna,id_hijo)," +
                "FOREIGN KEY(id_hijo) REFERENCES Datos(id_hijos));");

        //Insertar datos ficticios para prueba inicial
        mockData(db);
        mockDatavacuna(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        insertarHijo(sqLiteDatabase, new Hijo("1", "Christian Cano", "02/01/94",
                "M", "Sin caso especial", "hombre.png"));
        insertarHijo(sqLiteDatabase, new Hijo("2", "Junior Godoy", "16/03/94",
                "M", "Sin caso especial", "mujer.png"));
        insertarHijo(sqLiteDatabase, new Hijo("3", "Osmar Romero", "14/06/94",
                "M", "Alergico", "hombre.png"));
        insertarHijo(sqLiteDatabase, new Hijo("4", "Pablo Barrios", "2/10/94",
                "M", "Sin caso especial", "mujer.png"));
    }

    private void mockDatavacuna(SQLiteDatabase sqLiteDatabase) {
        //Vacunas para hijo id 1 - Para campo Aplicada, el cero es SI APLICADA
        insertarVacunas(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis","1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis","1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año","1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "1",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(19, " V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "1",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "1",0));
        //Vacunas para hijo id 2 - Para campo Aplicada, el cero es SI APLICADA
        insertarVacunas(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis","2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis","2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año","2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "2",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(19, " V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "2",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "2",1));
        //Vacunas para hijo id 3 - Para campo Aplicada, el cero es SI APLICADA
        insertarVacunas(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis","3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis","3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "3",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año","3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(19, " V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "3",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "3",1));
        //Vacunas para hijo id 4 - Para campo Aplicada, el cero es SI APLICADA
        insertarVacunas(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Messi", "unica dosis","4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis","4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Messi", "primera dosis", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22","Messi", "primera dosis", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Messi", "segunda dosis", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Josefina", "segunda dosis", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Josefina", "tercera dosis","4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Jose Fernanzdes", "al 1 año","4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Josefina", "al 1 año", "4",0));
        insertarVacunas(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(19, " V.H.A",1 ,1, "08/06/2017",
                null, "Jose Fernanzdes", "al año y 3 meses", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, "Jose Fernanzdes", "1er refuezo", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "4",1));
        insertarVacunas(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, "Jose Fernanzdes", "2do refuerzo", "4",1));
    }


    private long insertarHijo(SQLiteDatabase db, Hijo lawyer) {
        return db.insert(
                HijoEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    private long insertarVacunas(SQLiteDatabase db, Vacuna vac) {
        return db.insert(
                EstructuraVac.VacunaEntry.TABLE_NAME,
                null,
                vac.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public Cursor getAllLawyers() {
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

    public Cursor getLawyerById(String lawyerId) {
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
                registros = database.rawQuery(q, new String[]{"0",(id_hijo)});
            } else {
                if (orden == 2) {
                    q = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
                    registros = database.rawQuery(q, new String[]{"1", (id_hijo)});
                } else {
                    if (orden == 3) {
                        q = "SELECT * FROM Vacunas where id_hijo=? order by nombre;";
                        registros = database.rawQuery(q, new String[]{(id_hijo)});
                    } else {
                        q = "SELECT * FROM Vacunas where id_hijo=?;";
                        registros = database.rawQuery(q, new String[]{(id_hijo)});
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
                vac.setNombre_medico(registros.getString(6));
                vac.setAplicada(registros.getInt(9));

                lista.add(vac);
            }while(registros.moveToNext());
        }
        return lista;
    }
}
