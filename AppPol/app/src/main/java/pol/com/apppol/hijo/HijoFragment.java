package pol.com.apppol.hijo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import pol.com.apppol.R;
import pol.com.apppol.data.HijoDbHelper;
import pol.com.apppol.hijodetalle.HijoDetalleActivity;

import static pol.com.apppol.data.HijoContract.HijoEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HijoFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;

    private HijoDbHelper mLawyersDbHelper;

    private ListView mLawyersList;
    private HijoCursorAdapter mLawyersAdapter;
    //private FloatingActionButton mAddButton;


    public HijoFragment() {
        // Required empty public constructor
    }

    public static HijoFragment newInstance() {
        return new HijoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijo, container, false);

        // Referencias UI
        mLawyersList = (ListView) root.findViewById(R.id.hijo_list);
        mLawyersAdapter = new HijoCursorAdapter(getActivity(), null);
        //mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mLawyersList.setAdapter(mLawyersAdapter);

        // Eventos
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(HijoEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        /*
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAddScreen();
            }
        });
        */

        getActivity().deleteDatabase(HijoDbHelper.DATABASE_NAME);

        // Instancia de helper
        mLawyersDbHelper = new HijoDbHelper(getActivity());

        // Carga de datos
        loadLawyers();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadLawyers();
                    break;
            }
        }
    }

    private void loadLawyers() {
        new LawyersLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }
    /*
    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditLawyerActivity.class);
        startActivityForResult(intent, AddEditLawyerActivity.REQUEST_ADD_LAWYER);
    }
    */
    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), HijoDetalleActivity.class);
        intent.putExtra(HijoActivity.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getAllLawyers();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mLawyersAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }



}
