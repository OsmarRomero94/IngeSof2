package pol.com.apppol.hijo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import pol.com.apppol.R;
import pol.com.apppol.SignInActivity;
import pol.com.apppol.data.DbHelper;
import pol.com.apppol.data.Fecha;
import pol.com.apppol.data.Hijo;

import static java.lang.Boolean.TRUE;

public class HijoActivity extends AppCompatActivity {
    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";
    public static final int NOTIF_ALERTA_ID = 55;
    protected String id_usuario;
    public static boolean cargada = false;
    public static boolean notificar = false;
    String servidor = "http://10.13.14.10:8084";
    ArrayList<String> fechas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_usuario = getIntent().getExtras().getString("id_usuario");
        Bundle bundle = new Bundle();
        bundle.putString("id_usuario", id_usuario);
        HijoFragment fragment = (HijoFragment) getSupportFragmentManager().findFragmentById(R.id.hijo_container);
        //Cargar fragmento si no exite
        if (fragment == null) {
            fragment = HijoFragment.newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijo_container, fragment)
                    .commit();
        }

        if (!cargada) {
            DbHelper x = new DbHelper(getApplicationContext(), "Hijo.db", null, 1,"");
            //No modificar el warning
            fechas = x.obtener_fechas();
            ////alculo de vacuna proxima //Aca falta un metodo que agarre las fechas de la bd
           String[] fechasVacunas = {
                    "2017-06-10",
                   };

            Date d = new Date();
            CharSequence fechaActual = DateFormat.format("yyyy-MM-dd", d.getTime());
            for (int i = 0; i<fechas.size();i++) {
                if (fechas.get(i).equals(fechaActual)) {
                    notificar = true;
                    break;
                }
            }
            if (notificar) {
                //Construir la notificacion
                Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.jeringa)
                                .setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.drawable.jeringa)).getBitmap()))
                                .setContentTitle("AppPol")
                                .setContentText("Proxima vacuna en 2 dias")
                                .setSound(sonido)
                                .setAutoCancel(TRUE);
                //Donde ira al presionar la notificacion
                Intent intento = new Intent(this, HijoFragment.class);
                PendingIntent contIntent = PendingIntent.getActivity(this, 0, intento, 0);
                mBuilder.setContentIntent(contIntent);
                //Lanzar la notificacion
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
            }
            cargada = true;
        }

    }


}
