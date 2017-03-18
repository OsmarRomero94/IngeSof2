package pol.com.apppol.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pol.com.apppol.data.HijoContract.HijoEntry;
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
                + HijoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HijoEntry.ID + " TEXT NOT NULL,"
                + HijoEntry.NAME + " TEXT NOT NULL,"
                + HijoEntry.BIRTH + " TEXT NOT NULL,"
                + HijoEntry.SEX + " TEXT NOT NULL,"
                + HijoEntry.BIO + " TEXT NOT NULL,"
                + HijoEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + HijoEntry.ID + "))");

        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Hijo("Christian Cano", "02/01/94",
                "M", "Sin caso especial",
                "carlos_perez.jpg"));
        mockLawyer(sqLiteDatabase, new Hijo("Junior Godoy", "16/03/94",
                "M", "Sin caso especial",
                "daniel_samper.jpg"));
        mockLawyer(sqLiteDatabase, new Hijo("Osmar Romero", "14/06/94",
                "M", "Alergico",
                "lucia_aristizabal.jpg"));
        mockLawyer(sqLiteDatabase, new Hijo("Pablo Barrios", "2/10/94",
                "M", "Sin caso especial",
                "marina_acosta.jpg"));
    }

    public long mockLawyer(SQLiteDatabase db, Hijo lawyer) {
        return db.insert(
                HijoEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
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
