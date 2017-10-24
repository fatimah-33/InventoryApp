package app.qadheeb.fatimah.inventoryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProducts extends AppCompatActivity {

    EditText productNameEditText;
    EditText productPriceEditText;
    EditText productQuantityEditText;
    TextView productImgTextView;
    EditText supplierInfoEditText;
    Button saveButton;
    Button takeImgButton;
    String productName = null;
    int productPrice = 0;
    int productQuantity = 0;
    String productImg = null;
    String supplierInfo = null;
    Uri imageFile;
    String imgPath;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        productNameEditText = (EditText) findViewById(R.id.add_product_name_edit_text);
        productPriceEditText = (EditText) findViewById(R.id.add_product_price_edit_text);
        productQuantityEditText = (EditText) findViewById(R.id.add_quantity_edit_text);
        productImgTextView = (TextView) findViewById(R.id.add_img_text_view);
        supplierInfoEditText = (EditText) findViewById(R.id.add_supplier_edit_text);
        saveButton = (Button) findViewById(R.id.save_button);
        takeImgButton = (Button) findViewById(R.id.add_img_button);
        db = new DatabaseHandler(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takeImgButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    public void takeImg(View view) {
        Intent intentOpenCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = Uri.fromFile(getOutputMediaFile());
        intentOpenCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageFile);
        startActivityForResult(intentOpenCamera, 100);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takeImgButton.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imgPath = imageFile.toString();
                productImgTextView.setText(R.string.picture_taken_good);
            }
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "InventoryApp");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    public void saveButton(View view) {
        if (productNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_product_name, Toast.LENGTH_LONG).show();
        } else {
            productName = productNameEditText.getText().toString();
        }
        if (productPriceEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_product_price, Toast.LENGTH_LONG).show();
        } else {
            productPrice = Integer.parseInt(productPriceEditText.getText().toString());
        }
        if (productQuantityEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_product_quantity, Toast.LENGTH_LONG).show();
        } else {
            productQuantity = Integer.parseInt(productQuantityEditText.getText().toString());
        }
        if (productImgTextView.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_product_img, Toast.LENGTH_LONG).show();
        } else {
            productImg = imgPath;
        }
        if (supplierInfoEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_supplier_info, Toast.LENGTH_LONG).show();
        } else {
            supplierInfo = supplierInfoEditText.getText().toString();
        }
        if (productName == null || productPrice == 0 || productQuantity == 0 ||
                productImg == null || supplierInfo == null) {
            Toast.makeText(this, R.string.error_missing_data, Toast.LENGTH_SHORT).show();
        } else {
            db.insert(new ProductsObject(productName, productPrice, productImg, productQuantity, supplierInfo));
            Toast.makeText(this, R.string.success_save_data, Toast.LENGTH_LONG).show();
            Intent returnToMainActivity = new Intent(AddProducts.this, MainActivity.class);
            startActivity(returnToMainActivity);
        }
    }
}
