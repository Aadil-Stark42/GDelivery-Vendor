
package in.vdeliverzvendor.addtoppings.model_getcat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title {

    @SerializedName("title_id")
    @Expose
    private String  titleId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
