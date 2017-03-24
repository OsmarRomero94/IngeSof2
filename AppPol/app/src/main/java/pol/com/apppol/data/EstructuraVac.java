package pol.com.apppol.data;

import android.provider.BaseColumns;

/**
 * Estructura para la clase Vacuna
 */

public class EstructuraVac {
    public static abstract class VacunaEntry implements BaseColumns {
        public static final String TABLE_NAME ="Vacunas";
        //Datos de la vacuna
        public static final String ID = "id_vacuna";
        public static final String NOMBRE = "nombre";
        public static final String DOSIS = "dosis";
        public static final String EDAD = "edad";
        public static final String FECHA = "fecha";
        public static final String LOTE = "lote";
        public static final String NOMBRE_MEDICO = "nombre_medico";
        public static final String DESCRIPCION = "descripcion";
        public static final String ID_HIJO = "id_hijo";
        public static final String APLICADA = "aplicada";
    }
}
