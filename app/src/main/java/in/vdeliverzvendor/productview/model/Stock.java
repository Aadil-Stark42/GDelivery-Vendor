
package in.vdeliverzvendor.productview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {

    @SerializedName("stock_id")
    @Expose
    private String stockId;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("actual_price")
    @Expose
    private String actualPrice;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

}
