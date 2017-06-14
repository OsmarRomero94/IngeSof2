package pol.com.apppol.data;

/**
 * Created by CHRISTIANCS on 26/05/2017.
 */

public class Fecha {
    String fecha;

    public Fecha(){
        this.fecha = " ";
    }
    public Fecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
