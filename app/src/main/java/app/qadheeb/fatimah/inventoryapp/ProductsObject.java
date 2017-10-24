package app.qadheeb.fatimah.inventoryapp;

/**
 * Created by fatimah on 10/9/17.
 */

public class ProductsObject {
    private String productName;
    private int productPrice;
    private String productImg;
    private int productQuantity;
    private int id;

    private String productSupplier;

    public ProductsObject(int id, String productName, int productPrice, String productImg, int productQuantity, String productSupplier) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
    }

    public ProductsObject(String productName, int productPrice, String productImg, int productQuantity, String productSupplier) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductImg() {
        return productImg;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

}
