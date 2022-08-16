
package in.vdeliverzvendor.updatetstock.model_getstock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {

    @SerializedName("stock_id")
    @Expose
    private String stockId;
    @SerializedName("actual_price")
    @Expose
    private String actualPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("unit")
    @Expose
    private Object unit;
    @SerializedName("available_count")
    @Expose
    private String availableCount;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Object getUnit() {
        return unit;
    }

    public void setUnit(Object unit) {
        this.unit = unit;
    }

    public String getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(String availableCount) {
        this.availableCount = availableCount;
    }

}
