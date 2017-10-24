package app.qadheeb.fatimah.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fatimah on 10/23/17.
 */

public class ProductAdapter extends ArrayAdapter<ProductsObject> {
    private DatabaseHandler db;
    private Context context;

    public ProductAdapter(Context context, List<ProductsObject> productList) {
        super(context, 0, productList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View productListView = convertView;
        db = new DatabaseHandler(context);
        if (productListView == null) {
            productListView = LayoutInflater.from(getContext()).inflate(R.layout.products_row, parent, false);
        }

        final ProductsObject currantProductList = getItem(position);
        TextView productName = productListView.findViewById(R.id.product_name);
        TextView productPrice = productListView.findViewById(R.id.product_price);
        final TextView productQuantity = productListView.findViewById(R.id.product_quantity);
        Button buyButton = productListView.findViewById(R.id.buy_button);

        productName.setText(currantProductList.getProductName());
        productPrice.setText(String.valueOf(currantProductList.getProductPrice()) + " SR");
        productQuantity.setText(String.valueOf(currantProductList.getProductQuantity()));

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currantProductList.getProductQuantity() == 0) {
                    currantProductList.setProductQuantity(0);
                    productQuantity.setText(String.valueOf(0));
                } else {
                    int newQuantity = currantProductList.getProductQuantity() - 1;
                    currantProductList.setProductQuantity(newQuantity);
                    productQuantity.setText(String.valueOf(newQuantity));
                    db.updateQuantity(currantProductList, newQuantity);
                }
            }
        });

        return productListView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
