package in.vdeliverzvendor.addnewproduct.mvp_get;


import in.vdeliverzvendor.addnewproduct.model_get.GetProductResponse;

public class GetProductPresenter implements GetProductContract.GetproductIntractor.OnFinishedListener,GetProductIntract.OnGetProductListener {

    GetProductContract getProductContract;
    GetProductIntract getProductIntract;
    String TAG = GetProductPresenter.class.getSimpleName();

    public GetProductPresenter(GetProductContract getProductContract, GetProductIntract getProductIntract){
        this.getProductContract = getProductContract;
        this.getProductIntract = getProductIntract;
    }

    public void validateDetails(){
        getProductIntract.directValidation(this);

    }

    @Override
    public void onFinished(GetProductResponse getProductResponse) {
        getProductContract.getproduct_success(getProductResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        getProductContract.getproduct_failure(error_msg);

    }

    @Override
    public void do_logout() {
        getProductContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (getProductContract!=null)
            getProductIntract.getproductAPICall(this);

    }

    @Override
    public void onError(String msg) {
        getProductContract.getproduct_failure(msg);
    }
}
