package pol.com.apppol.data;

import android.content.ContentValues;
import android.database.Cursor;

import pol.com.apppol.data.HijoContract.HijoEntry;

import java.util.UUID;
/**
 * Created by Romero on 17/03/2017.
 */

public class Hijo {
    private String id;
    private String name;
    private String birth;
    private String sex;
    private String bio;
    private String avatarUri;

    public Hijo(String id, String name,
                  String birth, String sex,
                  String bio, String avatarUri) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public Hijo(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(HijoEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(HijoEntry.NAME));
        birth = cursor.getString(cursor.getColumnIndex(HijoEntry.BIRTH));
        sex = cursor.getString(cursor.getColumnIndex(HijoEntry.SEX));
        bio = cursor.getString(cursor.getColumnIndex(HijoEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex(HijoEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijoEntry.ID, id);
        values.put(HijoEntry.NAME, name);
        values.put(HijoEntry.BIRTH, birth);
        values.put(HijoEntry.SEX, sex);
        values.put(HijoEntry.BIO, bio);
        values.put(HijoEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getSex() {
        return sex;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
