package pol.com.apppol.hijo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import pol.com.apppol.R;
import pol.com.apppol.data.EstructuraHijo.HijoEntry;

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
        TextView fechaText = (TextView) view.findViewById(R.id.tv_fecha);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(HijoEntry.NOMBRE));
        String ape = cursor.getString(cursor.getColumnIndex(HijoEntry.APELLIDO));
        String nacimiento = cursor.getString(cursor.getColumnIndex(HijoEntry.FECHA_NACIMIENTO));
        String sexo = cursor.getString(cursor.getColumnIndex(HijoEntry.SEXO));
        String avatarUri;
        if (sexo.equals("M")){
            avatarUri = "hombre.png";
        } else {
            avatarUri = "mujer.png";
        }
        // Setup.
        nameText.setText(name+" "+ape);
        fechaText.setText(nacimiento);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });
    }
}
