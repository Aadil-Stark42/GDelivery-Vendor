
package in.vdeliverzvendor.productcategory.model_catlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("product_category_id")
    @Expose
    private String productCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("products_count")
    @Expose
    private String productsCount;

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(String productsCount) {
        this.productsCount = productsCount;
    }

}
