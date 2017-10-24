package app.qadheeb.fatimah.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProductsDetails extends AppCompatActivity {
    DatabaseHandler db;
    TextView productName;
    TextView productPrice;
    ImageView productImg;
    TextView productQuantity;
    TextView productSupplier;
    Button deleteButton;
    Button backButton;
    Button sumButton;
    Button subButton;
    int itemCode;
    ProductsObject products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        productName = (TextView) findViewById(R.id.product_name_detail);
        productPrice = (TextView) findViewById(R.id.product_price_detail);
        productImg = (ImageView) findViewById(R.id.product_img_detail);
        productQuantity = (TextView) findViewById(R.id.product_quantity_detail);
        productSupplier = (TextView) findViewById(R.id.product_supplier_detail);
        deleteButton = (Button) findViewById(R.id.delete);
        backButton = (Button) findViewById(R.id.back);
        sumButton = (Button) findViewById(R.id.sum);
        subButton = (Button) findViewById(R.id.sub);
        db = new DatabaseHandler(this);
        itemCode = getIntent().getIntExtra("Item code", 0);
        products = db.getSingleProduct(itemCode);

        productName.setText("Item Name: " + products.getProductName());
        productPrice.setText("Price: " + String.valueOf(products.getProductPrice()));
        productImg.setImageURI(Uri.parse(products.getProductImg()));
        productQuantity.setText(String.valueOf(products.getProductQuantity()));
        productSupplier.setText("Supplier Phone: " + products.getProductSupplier());


        productSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDialPadIntent = new Intent(Intent.ACTION_DIAL);
                openDialPadIntent.setData(Uri.parse("tel:" + products.getProductSupplier()));
                startActivity(openDialPadIntent);
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.getProductQuantity() == 0) {
                    products.setProductQuantity(0);
                    productQuantity.setText(String.valueOf(0));

                } else {
                    int newQuantity = products.getProductQuantity() - 1;
                    products.setProductQuantity(newQuantity);
                    productQuantity.setText(String.valueOf(newQuantity));
                    db.updateQuantity(products, newQuantity);
                }
            }
        });
        sumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = products.getProductQuantity() + 1;
                products.setProductQuantity(newQuantity);
                productQuantity.setText(String.valueOf(newQuantity));
                db.updateQuantity(products, newQuantity);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void deleteProduct(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteProduct(itemCode);
                        Toast.makeText(ProductsDetails.this, R.string.confirm_delete_message, Toast.LENGTH_LONG).show();
                        Intent returnToMainActivityIntent = new Intent(ProductsDetails.this, MainActivity.class);
                        startActivity(returnToMainActivityIntent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
        Intent returnToMainActivityIntent = new Intent(ProductsDetails.this, MainActivity.class);
        startActivity(returnToMainActivityIntent);    }
}
