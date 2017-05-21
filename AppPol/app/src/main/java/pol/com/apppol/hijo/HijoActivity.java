package pol.com.apppol.hijo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;

import java.util.Date;

import pol.com.apppol.R;

import static java.lang.Boolean.TRUE;

public class HijoActivity extends AppCompatActivity {
    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";
    public static final int NOTIF_ALERTA_ID = 55;
    public static boolean cargada = false;
    public static boolean notificar = false;
    protected String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //recibe parametro de singin
        id_usuario=getIntent().getExtras().getString("id_usuario");
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
        if (!cargada){
            //Calculo de vacuna proxima //Aca falta un metodo que agarre las fechas de la bd
            String[] fechasVacunas = {
                    "20/04/2017",//Poner fecha actual para probar notificacion
                    //Abajo van 2 dias antes de las fechas de vacunacion cargada en la base de datos
                    "06/03/2016",
                    "06/05/2016",
                    "06/07/2016",
                    "06/09/2016",
                    "06/03/2017",
                    "06/06/2017",
                    "06/09/2017",
                    "06/03/2020"};
            Date d = new Date();
            CharSequence fechaActual  = DateFormat.format("dd/MM/yyyy", d.getTime());
            for (String fechasVacuna : fechasVacunas) {
                if (fechasVacuna.equals(fechaActual)){
                    notificar = true;
                    break;
                }
            }
            if (notificar){
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
