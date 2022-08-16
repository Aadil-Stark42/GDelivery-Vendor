package in.vdeliverzvendor.updatetopping.mvp_gettopping;


import in.vdeliverzvendor.updatetopping.model_gettopping.ToppingDetailResponse;

public class GetToppingPresenter implements GetToppingContract.GettoppingIntractor.OnFinishedListener,GetToppingIntract.OnToppingListener{

    GetToppingContract getToppingContract;
    GetToppingIntract getToppingIntract;
    String TAG = GetToppingPresenter.class.getSimpleName();


    public GetToppingPresenter(GetToppingContract getToppingContract, GetToppingIntract getToppingIntract){
        this.getToppingContract = getToppingContract;
        this.getToppingIntract = getToppingIntract;
    }

    public void validateDetails(String strtoppingid){
        getToppingIntract.directValidation(strtoppingid,this);

    }


    @Override
    public void onFinished(ToppingDetailResponse toppingDetailResponse) {

        getToppingContract.gettopping_success(toppingDetailResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        getToppingContract.gettopping_failure(error_msg);

    }

    @Override
    public void do_logout() {
        getToppingContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (getToppingContract!=null)
            getToppingIntract.getstockAPICall(this);
    }

    @Override
    public void onError(String msg) {
        getToppingContract.gettopping_failure(msg);
    }
}
