package pol.com.apppol.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pol.com.apppol.data.HijoContract.HijoEntry;
import static pol.com.apppol.data.EstructuraVac.VacunaEntry;

/*
 * Manejador de la base de datos
 */

public class HijoDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Hijo.db";

    public HijoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HijoEntry.TABLE_NAME + " ("
                + HijoEntry._ID + " INTEGER PRIMARY KEY,"
                + HijoEntry.ID + " TEXT NOT NULL,"
                + HijoEntry.NAME + " TEXT NOT NULL,"
                + HijoEntry.BIRTH + " TEXT NOT NULL,"
                + HijoEntry.SEX + " TEXT NOT NULL,"
                + HijoEntry.BIO + " TEXT NOT NULL,"
                + HijoEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + HijoEntry.ID + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas ( id_vacuna integer not null , nombre text not null, " +
                "dosis intenger, edad integer, fecha text, lote text, nombre_medico text, descripcion text," +
                "id_hijo integer not null," +
                "aplicada INTEGER," +
                "PRIMARY KEY (id_vacuna,id_hijo)," +
                "FOREIGN KEY(id_hijo) REFERENCES Datos(id_hijos));");

        // Insertar datos ficticios para prueba inicial
        mockData(db);
        mockDatavacuna(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        insertarHijo(sqLiteDatabase, new Hijo("1", "Christian Cano", "02/01/94",
                "M", "Sin caso especial",
                "carlos_perez.jpg"));
        insertarHijo(sqLiteDatabase, new Hijo("2", "Junior Godoy", "16/03/94",
                "M", "Sin caso especial",
                "daniel_samper.jpg"));
        insertarHijo(sqLiteDatabase, new Hijo("3", "Osmar Romero", "14/06/94",
                "M", "Alergico",
                "lucia_aristizabal.jpg"));
        insertarHijo(sqLiteDatabase, new Hijo("4", "Pablo Barrios", "2/10/94",
                "M", "Sin caso especial",
                "marina_acosta.jpg"));
    }

    private void mockDatavacuna(SQLiteDatabase sqLiteDatabase) {
        insertarVacunas(sqLiteDatabase, new Vacuna(1, "BCG",1 , 0, "08/03/2016",
                "BXAS22", "Marcos", "unica dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(2, "ROTAVIRUS",1 , 0, "08/05/2016",
                "BXAS22", "Marcos", "primera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(3, "IPV",1 , 0, "08/05/2017",
                "BXAS22", "Marcos", "primera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(4, "PCV 10 VALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Marcos", "primera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(5, "PENTAVALENTE",1 , 0, "08/05/2016",
                "BXAS22", "Marcos", "primera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(6, "OPV/IPV",2 , 0, "08/07/2016",
                "BXAS22", "Marcos", "segunda dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(7, "ROTAVIRUS",2 , 0, "08/07/2016",
                "BXAS22", "Marcos", "segunda dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(8, "PCV 10 VALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Marcos", "segunda dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(9, "PENTAVALENTE",2 , 0, "08/07/2016",
                "BXAS22", "Marcos", "segunda dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(10, "OPV/IPV",3 , 0, "08/09/2016",
                "BXAS22", "Marcos", "tercera dosis",1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(11, "PENTAVALENTE",3 , 0, "08/09/2016",
                "BXAS22", "Marcos", "tercera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(12, "INFLUENZA 1RA",1 , 0, "08/09/2016",
                "BXAS22", "Marcos", "tercera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(13, "INFLUENZA 1RA",2 , 0, "08/09/2016",
                "BXAS22", "Marcos", "tercera dosis", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(14, "S.P.R",1 , 1, "08/03/2017",
                "BXAS22", "Marcos", "al 1 año", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(15, "PCV 10 REF",2 , 1, "08/03/2017",
                "BXAS22", "Marcos", "al 1 año", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(16, "AA",1 , 1, "08/03/2017",
                "BXAS22", "Marcos", "al 1 año", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(17, "INFLUENZA",3 , 1, "08/03/2017",
                "BXAS22", "Marcos", "al 1 año", 1,1));
        insertarVacunas(sqLiteDatabase, new Vacuna(18," V.V.Z",1 , 1, "08/06/2017",
                null, null, "al año y 3 meses", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(19, " V.H.A",1 ,1, "08/06/2017",
                null, null, "al año y 3 meses", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(20, "OPV/IPV",5 , 0, "08/09/2017",
                null, null, "1er refuezo", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(21, "D.T.P",1 , 0, "08/09/2017",
                null, null, "1er refuezo", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(22, "OPV/IPV",6 , 1, "08/03/2020",
                null, null, "2do refuerzo", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(23, "D.T.P",2 , 1, "08/03/2020",
                null, null, "2do refuerzo", 1,0));
        insertarVacunas(sqLiteDatabase, new Vacuna(24, "S.P.R",2 , 4, "08/03/2020",
                null, null, "2do refuerzo", 1,0));
    }


    public long insertarHijo(SQLiteDatabase db, Hijo lawyer) {
        return db.insert(
                HijoEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    public long insertarVacunas(SQLiteDatabase db, Vacuna vac) {
        return db.insert(
                EstructuraVac.VacunaEntry.TABLE_NAME,
                null,

                vac.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Hijo lawyer) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                HijoEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());

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
        Cursor c = getReadableDatabase().query(
                HijoEntry.TABLE_NAME,
                null,
                HijoEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String lawyerId) {
        return getWritableDatabase().delete(
                HijoEntry.TABLE_NAME,
                HijoEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    public int updateLawyer(Hijo lawyer, String lawyerId) {
        return getWritableDatabase().update(
                HijoEntry.TABLE_NAME,
                lawyer.toContentValues(),
                HijoEntry.ID + " LIKE ?",
                new String[]{lawyerId}
        );
    }
}
