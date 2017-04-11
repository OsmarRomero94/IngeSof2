package pol.com.apppol.hijodetalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pol.com.apppol.MostrarVacunas;
import pol.com.apppol.R;
import pol.com.apppol.hijo.HijoActivity;

public class HijoDetalleActivity extends AppCompatActivity {
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //No modificar el warning
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Captura el id del hijo desde la clase HijoActivity
        id = getIntent().getStringExtra(HijoActivity.EXTRA_LAWYER_ID);
        //
        HijoDetalleFragment fragment = (HijoDetalleFragment) getSupportFragmentManager().findFragmentById(R.id.hijo_detalle_container);
        if (fragment == null) {
            fragment = HijoDetalleFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijo_detalle_container, fragment)
                    .commit();
        }
    }

    //Hace que el boton Mostrar Vacunas te mande a la siguiente actividad (MostrarVacunas)
    public void mostrarVacunas (View v){
        Intent intento = new Intent(this, MostrarVacunas.class);
        intento.putExtra("parametro", id);
        startActivity(intento);
    }

    //Hace que el boton de flechita atras de arriba te mande a la actividad anterior (HijoActivity)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
