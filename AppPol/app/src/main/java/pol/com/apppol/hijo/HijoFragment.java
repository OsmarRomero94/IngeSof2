package pol.com.apppol.hijo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import pol.com.apppol.R;
import pol.com.apppol.SignInActivity;
import pol.com.apppol.data.DbHelper;
import pol.com.apppol.hijodetalle.HijoDetalleActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static java.lang.Boolean.TRUE;
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
        String id_usuario = getArguments().getString("id_usuario");
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
        // Instancia del DbHelper
        mLawyersDbHelper = new DbHelper(getActivity(), "Hijo.db", null, 1, id_usuario);
        // Carga de datos
        loadHijos();
        return root;
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

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), HijoDetalleActivity.class);
        intent.putExtra(HijoActivity.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }
}
