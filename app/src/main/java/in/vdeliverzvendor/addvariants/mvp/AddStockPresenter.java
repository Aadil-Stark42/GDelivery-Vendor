package in.vdeliverzvendor.addvariants.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public class AddStockPresenter implements  AddStockContract.GetaddstockIntractor.OnFinishedListener,AddStockIntract.OnaddstockListener {

    AddStockContract addStockContract;
    AddStockIntract addStockIntract;
    String TAG = AddStockPresenter.class.getSimpleName();


    public AddStockPresenter(AddStockContract addStockContract, AddStockIntract addStockIntract){
        this.addStockContract = addStockContract;
        this.addStockIntract = addStockIntract;
    }

    public void validateDetails(String strPId,String strActualPrice,String strSellingPrice,String strAvailable,String strSize,String strUnit,String strVariant){
        addStockIntract.directValidation(strPId,strActualPrice,strSellingPrice,strAvailable,strSize,strUnit,strVariant,this);
    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        addStockContract.addstock_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        addStockContract.addstock_failure(error_msg);

    }

    @Override
    public void do_logout() {
        addStockContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (addStockContract!=null)
            addStockIntract.addstockAPICall(this);

    }

    @Override
    public void onError(String msg) {
        addStockContract.addstock_failure(msg);

    }
}
