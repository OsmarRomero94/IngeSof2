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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import pol.com.apppol.MostrarVacunas;
import pol.com.apppol.R;
import pol.com.apppol.data.Hijo;
import pol.com.apppol.data.HijoDbHelper;
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
    private ImageView mAvatar;
    private TextView mSex;
    private TextView mSpecialty;
    private TextView mBio;

    private HijoDbHelper mLawyersDbHelper;


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
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mLawyerId = (TextView) root.findViewById(R.id.tv_id);
        mSex = (TextView) root.findViewById(R.id.tv_sex);
        mSpecialty = (TextView) root.findViewById(R.id.tv_specialty);
        mBio = (TextView) root.findViewById(R.id.tv_bio);

        mLawyersDbHelper = new HijoDbHelper(getActivity());

        loadLawyer();

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
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

    private void showLawyer(Hijo hijo) {
        mCollapsingView.setTitle(hijo.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + hijo.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mLawyerId.setText(hijo.getId());
        mSex.setText(hijo.getSex());
        mSpecialty.setText(hijo.getBirth());
        mBio.setText(hijo.getBio());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getLawyerById(mId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Hijo(cursor));
            } else {
                showLoadError();
            }
        }
    }

}
