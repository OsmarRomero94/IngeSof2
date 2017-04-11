package pol.com.apppol.hijodetalle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
    private TextView mLawyerId;
    private String  mId;
    private CollapsingToolbarLayout mCollapsingView;
    //private ImageView mAvatar;
    private TextView mFechaNac;
    private TextView mLugarNac;
    private TextView mDomic;
    private TextView mSex;
    private DbHelper mLawyersDbHelper;


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
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        //mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mLawyerId = (TextView) root.findViewById(R.id.tv_id);
        mFechaNac = (TextView) root.findViewById(R.id.tv_fecha_nac);
        mLugarNac = (TextView) root.findViewById(R.id.tv_lugar_nac);
        mDomic= (TextView) root.findViewById(R.id.tv_domic);
        mSex = (TextView) root.findViewById(R.id.tv_sex);
        mLawyersDbHelper = new DbHelper(getActivity(), "Hijo.db", null, 1);
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
        mCollapsingView.setTitle(hijo.getNombre() + " " + hijo.getApellido());
        Glide.with(this)
                .load(Uri.parse(hijo.getAva_uri()))
                .centerCrop();
                //.into(mAvatar);
        mLawyerId.setText(hijo.getId());
        mFechaNac.setText(hijo.getFecha_nacimiento());
        mLugarNac.setText(hijo.getLugar_nacimiento());
        mDomic.setText(hijo.getDireccion());
        mSex.setText(hijo.getSexo());
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
