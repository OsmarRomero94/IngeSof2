package pol.com.apppol.hijo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pol.com.apppol.R;

public class HijoActivity extends AppCompatActivity {

    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HijoFragment fragment = (HijoFragment)
                getSupportFragmentManager().findFragmentById(R.id.hijo_container);

        if (fragment == null) {
            fragment = HijoFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijo_container, fragment)
                    .commit();
        }
    }

}
