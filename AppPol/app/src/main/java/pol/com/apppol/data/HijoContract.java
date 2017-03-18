package pol.com.apppol.data;

import android.provider.BaseColumns;
/**
 * Created by Romero on 17/03/2017.
 */

public class HijoContract {
    public static abstract class HijoEntry implements BaseColumns{
        public static final String TABLE_NAME ="hijo";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String BIRTH = "birth";
        public static final String SEX = "sex";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";
    }
}
