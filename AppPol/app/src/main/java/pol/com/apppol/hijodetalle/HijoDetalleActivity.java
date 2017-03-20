package pol.com.apppol.hijodetalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import pol.com.apppol.MostrarVacunas;
import pol.com.apppol.R;
import pol.com.apppol.hijo.HijoActivity;

public class HijoDetalleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(HijoActivity.EXTRA_LAWYER_ID);

        HijoDetalleFragment fragment = (HijoDetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.hijo_detalle_container);
        if (fragment == null) {
            fragment = HijoDetalleFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijo_detalle_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hijo_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void siguiente (View v){

        Intent intento = new Intent(this, MostrarVacunas.class);
        //intento.putExtra("parametro",posi);
        startActivity(intento);

    }

}
