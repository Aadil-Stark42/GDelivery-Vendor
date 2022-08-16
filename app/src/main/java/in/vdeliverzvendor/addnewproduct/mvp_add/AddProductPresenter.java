package in.vdeliverzvendor.addnewproduct.mvp_add;


import java.io.File;

import in.vdeliverzvendor.addnewproduct.model_add.AddProductResponse;

public class AddProductPresenter implements  AddProductContract.GetAddproductIntractor.OnFinishedListener,AddProductIntract.OnAddProductListener {

    AddProductContract addProductContract;
    AddProductIntract addProductIntract;
    String TAG = AddProductPresenter.class.getSimpleName();

    public AddProductPresenter(AddProductContract addProductContract, AddProductIntract addProductIntract){
        this.addProductContract = addProductContract;
        this.addProductIntract = addProductIntract;
    }

    public void validateDetails(String strShopId, String strShopCatId, String strProductId, String strName, String strDesc,
                                String strCuId, String strVarId, File file){
        addProductIntract.directValidation(strShopId,strShopCatId,strProductId,strName,strDesc,strCuId,strVarId,file,this);

    }


    @Override
    public void onFinished(AddProductResponse generalResponse) {
        addProductContract.addproduct_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        addProductContract.addproduct_failure(error_msg);

    }

    @Override
    public void do_logout() {
        addProductContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (addProductContract!=null)
            addProductIntract.getAddproductAPICall(this);

    }

    @Override
    public void onError(String msg) {
        addProductContract.addproduct_failure(msg);

    }
}
