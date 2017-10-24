package app.qadheeb.fatimah.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by fatimah on 10/16/17.
 */

public class ProductsContract {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "ShopDatabase";

    static final String CREATE_TABLE = "CREATE TABLE " + ItemData.TABLE_NAME + "(" + ItemData.KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + ItemData.ITEM_NAME + " TEXT NOT NULL" + "," +
            ItemData.ITEM_PRICE + " INTEGER NOT NULL" + "," + ItemData.ITEM_QUANTITY + " INTEGER NOT NULL"
            + "," + ItemData.ITEM_IMG + " TEXT NOT NULL" + "," + ItemData.ITEM_SUPPLIER + " TEXT NOT NULL" + ")";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + ItemData.TABLE_NAME;

    public static abstract class ItemData implements BaseColumns {
        public static final String TABLE_NAME = "ItemData";
        public static final String KEY_ID = "id";
        public static final String ITEM_NAME = "name";
        public static final String ITEM_PRICE = "price";
        public static final String ITEM_QUANTITY = "quantity";
        public static final String ITEM_IMG = "img";
        public static final String ITEM_SUPPLIER = "supplier";

    }
}

