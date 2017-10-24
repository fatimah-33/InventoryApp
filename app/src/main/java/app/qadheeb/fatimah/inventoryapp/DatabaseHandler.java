package app.qadheeb.fatimah.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static app.qadheeb.fatimah.inventoryapp.ProductsContract.CREATE_TABLE;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.DATABASE_NAME;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.DATABASE_VERSION;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.DROP_TABLE;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_IMG;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_NAME;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_PRICE;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_QUANTITY;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_SUPPLIER;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.KEY_ID;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.TABLE_NAME;


/**
 * Created by fatimah on 9/16/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void insert(ProductsObject productsObject) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, productsObject.getProductName());
        contentValues.put(ITEM_PRICE, productsObject.getProductPrice());
        contentValues.put(ITEM_QUANTITY, productsObject.getProductQuantity());
        contentValues.put(ITEM_IMG, productsObject.getProductImg());
        contentValues.put(ITEM_SUPPLIER, productsObject.getProductSupplier());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public Cursor readData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] projection = {
                KEY_ID,
                ITEM_NAME,
                ITEM_PRICE,
                ITEM_QUANTITY,
                ITEM_IMG,
                ITEM_SUPPLIER};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    public void deleteProduct(int keyProduct) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, KEY_ID + " Like?", new String[]{String.valueOf(keyProduct)});
        sqLiteDatabase.close();
    }

    public ProductsObject getSingleProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                KEY_ID,
                ITEM_NAME,
                ITEM_PRICE,
                ITEM_IMG,
                ITEM_QUANTITY,
                ITEM_SUPPLIER};
        Cursor cursor = db.query(TABLE_NAME, projection, KEY_ID + " Like?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProductsObject productItem = new ProductsObject(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                cursor.getString(5));
        return productItem;
    }

    public void updateQuantity(ProductsObject productsObject, int productQuantity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_QUANTITY, productQuantity);
        sqLiteDatabase.update(TABLE_NAME, contentValues, KEY_ID + " LIKE ?", new String[]{String.valueOf(productsObject.getId())});
        sqLiteDatabase.close();
    }

}
