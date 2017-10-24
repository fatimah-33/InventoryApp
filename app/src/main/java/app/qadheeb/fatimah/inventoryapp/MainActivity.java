package app.qadheeb.fatimah.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_IMG;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_NAME;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_PRICE;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_QUANTITY;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.ITEM_SUPPLIER;
import static app.qadheeb.fatimah.inventoryapp.ProductsContract.ItemData.KEY_ID;

public class MainActivity extends AppCompatActivity {
    ProductAdapter productAdapter;
    private ListView listView;
    List<ProductsObject> productsObjectsList;
    DatabaseHandler db;
    Button addProduct;
    TextView noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_product);
        addProduct = (Button) findViewById(R.id.add_new_product);
        noResult = (TextView) findViewById(R.id.no_result);
        db = new DatabaseHandler(this);
        productsObjectsList = new ArrayList<>();
        Cursor c = db.readData();
        if (c.getColumnCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    productsObjectsList.add(new ProductsObject(
                            c.getInt(c.getColumnIndex(KEY_ID)),
                            c.getString(c.getColumnIndex(ITEM_NAME)),
                            c.getInt(c.getColumnIndex(ITEM_PRICE)),
                            c.getString(c.getColumnIndex(ITEM_IMG)),
                            c.getInt(c.getColumnIndex(ITEM_QUANTITY)),
                            c.getString(c.getColumnIndex(ITEM_SUPPLIER))));
                }
                while (c.moveToNext());
            }
            productAdapter = new ProductAdapter(this, productsObjectsList);
            listView.setAdapter(productAdapter);
            if (productsObjectsList.isEmpty())
                noResult.setText(R.string.no_result);

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemSelected = productAdapter.getItem(i).getId();
                Intent openDetailsActivityIntent = new Intent(MainActivity.this, ProductsDetails.class);
                openDetailsActivityIntent.putExtra("Item code", itemSelected);
                startActivity(openDetailsActivityIntent);
            }

        });
    }

    public void addNewProduct(View view) {
        Intent openAddProductsActivityIntent = new Intent(MainActivity.this, AddProducts.class);
        startActivity(openAddProductsActivityIntent);
    }
}
