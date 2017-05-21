package pol.com.apppol.data;

import android.content.ContentValues;
import android.database.Cursor;

import pol.com.apppol.data.EstructuraHijo.HijoEntry;

/**
 * Clase hijo, cursor y getters
 */

public class Hijo {
    private String id_hijo;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String lugar_nacimiento;
    private String sexo;
    private String nacionalidad;
    private String direccion;
    private String telefono_contacto;
    private String alergias;


    public Hijo(String id_hijo, String nombre, String apellido, String fecha_nacimiento, String lugar_nacimiento,
                String sexo, String nacionalidad, String direccion, String telefono_contacto,
                 String alergias) {
        this.id_hijo = id_hijo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;


        this.telefono_contacto = telefono_contacto;

        this.alergias = alergias;
    }



    public Hijo(Cursor cursor) {
        id_hijo = cursor.getString(cursor.getColumnIndex(HijoEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(HijoEntry.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex(HijoEntry.APELLIDO));
        fecha_nacimiento = cursor.getString(cursor.getColumnIndex(HijoEntry.FECHA_NACIMIENTO));
        lugar_nacimiento = cursor.getString(cursor.getColumnIndex(HijoEntry.LUGAR_NACIMIENTO));
        sexo = cursor.getString(cursor.getColumnIndex(HijoEntry.SEXO));
        nacionalidad = cursor.getString(cursor.getColumnIndex(HijoEntry.NACIONALIDAD));
        direccion = cursor.getString(cursor.getColumnIndex(HijoEntry.DIRECCION));

        telefono_contacto = cursor.getString(cursor.getColumnIndex(HijoEntry.TELEFONO_CONTACTO));

        alergias = cursor.getString(cursor.getColumnIndex(HijoEntry.ALERGIAS));


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijoEntry.ID, id_hijo);
        values.put(HijoEntry.NOMBRE, nombre);
        values.put(HijoEntry.APELLIDO, apellido);
        values.put(HijoEntry.FECHA_NACIMIENTO, fecha_nacimiento);
        values.put(HijoEntry.LUGAR_NACIMIENTO, lugar_nacimiento);
        values.put(HijoEntry.SEXO, sexo);
        values.put(HijoEntry.NACIONALIDAD, nacionalidad);
        values.put(HijoEntry.DIRECCION, direccion);
        values.put(HijoEntry.TELEFONO_CONTACTO, telefono_contacto);
        values.put(HijoEntry.ALERGIAS, alergias);

        return values;
    }

    public String getId() {
        return id_hijo;
    }


    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public String getAlergias() {
        return alergias;
    }
}
