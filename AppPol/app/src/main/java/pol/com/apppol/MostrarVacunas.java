package pol.com.apppol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;
import java.util.ArrayList;

import pol.com.apppol.data.DbHelper;
import pol.com.apppol.data.Vacuna;
import pol.com.apppol.entidad.Filtro;
import pol.com.apppol.entidad.Tabla;

/*
 * Agrega los registros para la tabla segun el tipo de orden que se elige
 */

public class MostrarVacunas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<Vacuna> elemento;
    protected Spinner clientesSpin;
    ArrayList<Filtro> listita=new ArrayList<>();
    public int orden;
    protected TableLayout tab;
    Tabla tabla;
    String id_hijo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vacunas);
        //Captura el id_hijo mandado desde HijoDetalleActivity
        id_hijo = getIntent().getExtras().getString("parametro");
        //Opcion para el tipo de orden con sus filtros
        Filtro aux=new Filtro();
        aux.setNombre("PRESIONE AQUI");
        aux.setPosicion(0);
        listita.add(aux);
        aux=new Filtro();
        aux.setNombre("Aplicadas");
        aux.setPosicion(1);
        listita.add(aux);
        aux=new Filtro();
        aux.setNombre("No Aplicadas");
        aux.setPosicion(2);
        listita.add(aux);
        aux=new Filtro();
        aux.setNombre("Alfabeticamente");
        aux.setPosicion(3);
        listita.add(aux);
        aux=new Filtro();
        aux.setNombre("Por fecha");
        aux.setPosicion(4);
        listita.add(aux);
        //Creacion del spinner
        clientesSpin = (Spinner) findViewById(R.id.spinner);
        clientesSpin.setOnItemSelectedListener(this);
        ArrayAdapter<Filtro> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listita);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientesSpin.setAdapter(dataAdapter);
        tab = (TableLayout) findViewById(R.id.tabla);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        // Muestra el elemento seleccionado y pone los datos en la pantalla
        if(position == 0){
            Toast.makeText(parent.getContext(), "Seleccione un Filtro ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parent.getContext(), "Seleccionó: " + item, Toast.LENGTH_SHORT).show();
        }
        tab.removeAllViews();
        tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        //
        if(position!=0) {
            DbHelper x = new DbHelper(getApplicationContext(), "Hijo.db", null, 1,"");
            //No modificar el warning
            elemento = x.llenar_lv(position, id_hijo);
            //
            int tamano = elemento.size();
            tabla.agregarCabecera(R.array.cabecera_tabla);
            for (int i = 0; i < tamano; i++) {
                ArrayList<String> elementos = new ArrayList<>();
                elementos.add(elemento.get(i).getNombre());
                elementos.add(elemento.get(i).getFecha());
                //
                tabla.agregarFilaTabla(elementos);
            }
        }
        orden=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Sin accion, sigue esperando
    }
}

