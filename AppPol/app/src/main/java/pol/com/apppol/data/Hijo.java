package pol.com.apppol.data;

import android.content.ContentValues;
import android.database.Cursor;

import pol.com.apppol.data.EstructuraHijo.HijoEntry;

/**
 * Clase hijo, cursor y getters
 */

public class Hijo {
    private String id;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String lugar_nacimiento;
    private String sexo;
    private String nacionalidad;
    private String direccion;
    private String departamento;
    private String municipio;
    private String barrio;
    private String referencia_domicilio;
    private String responsable;
    private String telefono_contacto;
    private String seguro_medico;
    private String alergias;
    private String ava_uri;

    public Hijo(String id, String nombre, String apellido, String fecha_nacimiento, String lugar_nacimiento,
                String sexo, String nacionalidad, String direccion, String departamento, String municipio,
                String barrio, String referencia_domicilio, String responsable, String telefono_contacto,
                String seguro_medico, String alergias, String ava_uri) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.barrio = barrio;
        this.referencia_domicilio = referencia_domicilio;
        this.responsable = responsable;
        this.telefono_contacto = telefono_contacto;
        this.seguro_medico = seguro_medico;
        this.alergias = alergias;
        this.ava_uri = ava_uri;
    }



    public Hijo(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(HijoEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(HijoEntry.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex(HijoEntry.APELLIDO));
        fecha_nacimiento = cursor.getString(cursor.getColumnIndex(HijoEntry.FECHA_NACIMIENTO));
        lugar_nacimiento = cursor.getString(cursor.getColumnIndex(HijoEntry.LUGAR_NACIMIENTO));
        sexo = cursor.getString(cursor.getColumnIndex(HijoEntry.SEXO));
        nacionalidad = cursor.getString(cursor.getColumnIndex(HijoEntry.NACIONALIDAD));
        direccion = cursor.getString(cursor.getColumnIndex(HijoEntry.DIRECCION));
        departamento = cursor.getString(cursor.getColumnIndex(HijoEntry.DEPARTAMENTO));
        municipio = cursor.getString(cursor.getColumnIndex(HijoEntry.MUNICIPIO));
        barrio = cursor.getString(cursor.getColumnIndex(HijoEntry.BARRIO));
        referencia_domicilio = cursor.getString(cursor.getColumnIndex(HijoEntry.REFERENCIA_DOMICILIO));
        responsable = cursor.getString(cursor.getColumnIndex(HijoEntry.RESPONSABLE));
        telefono_contacto = cursor.getString(cursor.getColumnIndex(HijoEntry.TELEFONO_CONTACTO));
        seguro_medico = cursor.getString(cursor.getColumnIndex(HijoEntry.SEGURO_MEDICO));
        alergias = cursor.getString(cursor.getColumnIndex(HijoEntry.ALERGIAS));
        ava_uri =cursor.getString(cursor.getColumnIndex(HijoEntry.AVA_URI));


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijoEntry.ID, id);
        values.put(HijoEntry.NOMBRE, nombre);
        values.put(HijoEntry.APELLIDO, apellido);
        values.put(HijoEntry.FECHA_NACIMIENTO, fecha_nacimiento);
        values.put(HijoEntry.LUGAR_NACIMIENTO, lugar_nacimiento);
        values.put(HijoEntry.SEXO, sexo);

        values.put(HijoEntry.NACIONALIDAD, nacionalidad);
        values.put(HijoEntry.DIRECCION, direccion);
        values.put(HijoEntry.DEPARTAMENTO, departamento);
        values.put(HijoEntry.MUNICIPIO, municipio);
        values.put(HijoEntry.BARRIO, barrio);
        values.put(HijoEntry.REFERENCIA_DOMICILIO, referencia_domicilio);

        values.put(HijoEntry.RESPONSABLE, responsable);
        values.put(HijoEntry.TELEFONO_CONTACTO, telefono_contacto);
        values.put(HijoEntry.SEGURO_MEDICO, seguro_medico);
        values.put(HijoEntry.ALERGIAS, alergias);

        values.put(HijoEntry.AVA_URI, ava_uri);


        return values;
    }

    public String getId() {
        return id;
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

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getReferencia_domicilio() {
        return referencia_domicilio;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public String getSeguro_medico() {
        return seguro_medico;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getAva_uri() {
        return ava_uri;
    }
}
