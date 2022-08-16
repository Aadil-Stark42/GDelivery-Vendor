package in.vdeliverzvendor.updateproduct.mvp;


import java.io.File;

import in.vdeliverzvendor.utils.GeneralResponse;

public class UpdateProductPresenter implements  UpdateProductContract.GetUpdateProductIntractor.OnFinishedListener,UpdateProductIntract.OnupdateproductListener {

    UpdateProductContract updateProductContract;
    UpdateProductIntract updateProductIntract;
    String TAG = UpdateProductPresenter.class.getSimpleName();


    public UpdateProductPresenter(UpdateProductContract updateProductContract, UpdateProductIntract updateProductIntract){
        this.updateProductContract = updateProductContract;
        this.updateProductIntract = updateProductIntract;
    }

    public void validateDetails(String strShopId,String strpid, String strShopCatId, String strProductId, String strName, String strDesc,
    String strCuId, String strVarId, File file){
        updateProductIntract.directValidation(strShopId,strpid,strShopCatId,strProductId,strName,strDesc,strCuId,strVarId,file,this);

    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        updateProductContract.updateproduct_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        updateProductContract.updateproduct_failure(error_msg);

    }

    @Override
    public void do_logout() {
        updateProductContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (updateProductContract!=null)
            updateProductIntract.updateproductAPICall(this);

    }

    @Override
    public void onError(String msg) {
        updateProductContract.updateproduct_failure(msg);
    }
}
