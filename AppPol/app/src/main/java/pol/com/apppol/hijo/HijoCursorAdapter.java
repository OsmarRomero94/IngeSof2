package pol.com.apppol.hijo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pol.com.apppol.R;
import pol.com.apppol.data.HijoContract.HijoEntry;

/**
 * Adaptador
 */

public class HijoCursorAdapter extends CursorAdapter{
    public HijoCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_hijo, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(HijoEntry.NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(HijoEntry.AVATAR_URI));

        // Setup.
        nameText.setText(name);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop();
    }
}
