package pol.com.apppol.hijo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import pol.com.apppol.Notificaciones;
import pol.com.apppol.R;
import pol.com.apppol.Utilidades;
import pol.com.apppol.data.DbHelper;
import pol.com.apppol.data.Hijo;
import pol.com.apppol.hijodetalle.HijoDetalleActivity;

import static pol.com.apppol.data.EstructuraHijo.HijoEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HijoFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;

    private DbHelper mLawyersDbHelper;
    private HijoCursorAdapter mLawyersAdapter;

    public HijoFragment() {
        // Required empty public constructor
    }

    public static HijoFragment newInstance() {
        return new HijoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijo, container, false);
        // Referencias UI
        ListView mLawyersList = (ListView) root.findViewById(R.id.hijo_list);
        mLawyersAdapter = new HijoCursorAdapter(getActivity(), null);
        // Setup
        mLawyersList.setAdapter(mLawyersAdapter);
        // Eventos
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(currentItem.getColumnIndex(HijoEntry.ID));
                showDetailScreen(currentLawyerId);
            }
        });
        //
        getActivity().deleteDatabase(DbHelper.DATABASE_NAME);
        // Instancia de helper
        mLawyersDbHelper = new DbHelper(getActivity(), "Hijo.db", null, 1);
        // Carga de datos
        loadHijos();
        //Crea las notificaciones solo la primera vez que corre la aplicacion
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            loadNotificaciones();
            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
        return root;
    }

    private void loadNotificaciones() {
        Utilidades util = new Utilidades();

        Cursor cursor = DbHelper.getHijoById("1");
        ArrayList<Integer> meses = getMesesNoAplicados("1");
        cursor.moveToFirst();
        Hijo hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nacimiento(), meses.get(i));
            new Notificaciones(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();

        cursor = DbHelper.getHijoById("2");
        meses = getMesesNoAplicados("2");
        cursor.moveToFirst();
        hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nacimiento(), meses.get(i));
            new Notificaciones(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();

        cursor = DbHelper.getHijoById("3");
        meses = getMesesNoAplicados("3");
        cursor.moveToFirst();
        hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nacimiento(), meses.get(i));
            new Notificaciones(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();

        cursor = DbHelper.getHijoById("4");
        meses = getMesesNoAplicados("4");
        cursor.moveToFirst();
        hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nacimiento(), meses.get(i));
            new Notificaciones(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();
    }

    private ArrayList<Integer> getMesesNoAplicados(String idHijo) {
        Cursor cursor = DbHelper.getNoAplicadas(idHijo);
        ArrayList<Integer> lista = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getInt(8));
            } while (cursor.moveToNext());
        }
        HashSet<Integer> hs = new HashSet<>(lista);
        lista.clear();
        lista.addAll(hs);

        Collections.sort(lista);
        return lista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadHijos();
                    break;
            }
        }
    }

    private void loadHijos() {
        new HijosLoadTask().execute();
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), HijoDetalleActivity.class);
        intent.putExtra(HijoActivity.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    private class HijosLoadTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getAllHijos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mLawyersAdapter.swapCursor(cursor);
            }
        }
    }


}
