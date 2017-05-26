package pol.com.apppol.hijodetalle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pol.com.apppol.R;
import pol.com.apppol.data.Hijo;
import pol.com.apppol.data.DbHelper;
import pol.com.apppol.hijo.HijoFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijoDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HijoDetalleFragment extends Fragment {
    private static final String ARG_LAWYER_ID = "lawyerId";
    private DbHelper mLawyersDbHelper;
    private String  mId;
    //Variables a mostrar
    private CollapsingToolbarLayout mCollapsingView;
    private TextView mLawyerId;
    private TextView mFechaNac;
    private TextView mNacionalidad;
    private TextView mLugarNac;
    private TextView mSex;
    private TextView mDomic;
    private TextView mTelefono;
    private TextView mAlergias;

    public HijoDetalleFragment() {
        // Required empty public constructor
    }

    public static HijoDetalleFragment newInstance(String lawyerId) {
        HijoDetalleFragment fragment = new HijoDetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_LAWYER_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijo_detalle, container, false);
        //Variables a mostrar
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mLawyerId = (TextView) root.findViewById(R.id.tv_id);
        mFechaNac = (TextView) root.findViewById(R.id.tv_fecha_nac);
        mNacionalidad = (TextView) root.findViewById(R.id.tv_nacionalidad);
        mLugarNac = (TextView) root.findViewById(R.id.tv_lugar_nac);
        mSex = (TextView) root.findViewById(R.id.tv_sex);
        mDomic= (TextView) root.findViewById(R.id.tv_domic);
        mTelefono = (TextView) root.findViewById(R.id.tv_telefono);
        mAlergias = (TextView) root.findViewById(R.id.tv_alergias);
        //Instancia del DbHelper
        mLawyersDbHelper = new DbHelper(getActivity(), "Hijo.db", null, 1,"");
        //Cargar el hijo solicitado
        loadHijo();
        return root;
    }

    private void loadHijo() {
        new GetHijoByIdTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HijoFragment.REQUEST_UPDATE_DELETE_LAWYER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showHijo(Hijo hijo) {
        //Agarrar las variables a mostrar
        mCollapsingView.setTitle(hijo.getNombre() + " " + hijo.getApellido());
        mLawyerId.setText(hijo.getId());
        mFechaNac.setText(hijo.getFecha_nacimiento());
        mNacionalidad.setText(hijo.getNacionalidad());
        mLugarNac.setText(hijo.getLugar_nacimiento());
        mSex.setText(hijo.getSexo());
        mDomic.setText(hijo.getDireccion());
        mTelefono.setText(hijo.getTelefono_contacto());
        mAlergias.setText(hijo.getAlergias());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(), "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetHijoByIdTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getHijoById(mId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showHijo(new Hijo(cursor));
            } else {
                showLoadError();
            }
        }
    }
}