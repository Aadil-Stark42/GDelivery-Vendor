
package in.vdeliverzvendor.shopsearch.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shops {

    @SerializedName("pagination")
    @Expose
    private List<Pagination> pagination = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Pagination> getPagination() {
        return pagination;
    }

    public void setPagination(List<Pagination> pagination) {
        this.pagination = pagination;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
