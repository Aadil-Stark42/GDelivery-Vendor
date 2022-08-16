package in.vdeliverzvendor.addtoppings.mvp_getcat;


import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;

public class GetToppingCatPresenter  implements  GetToppingCatIntract.OntoppingcatListener, GetToppingCatContract.GettoppingCatIntractor.OnFinishedListener{

    GetToppingCatContract getToppingCatContract;
    GetToppingCatIntract getToppingCatIntract;
    String TAG = GetToppingCatPresenter.class.getSimpleName();


    public GetToppingCatPresenter(GetToppingCatContract getToppingCatContract, GetToppingCatIntract getToppingCatIntract){
        this.getToppingCatContract = getToppingCatContract;
        this.getToppingCatIntract = getToppingCatIntract;
    }

    public void validateDetails(){
        getToppingCatIntract.directValidation(this);
    }

    @Override
    public void onFinished(ToppingCatResponse toppingCatResponse) {
        getToppingCatContract.toppingcat_success(toppingCatResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        getToppingCatContract.toppingcat_failure(error_msg);

    }

    @Override
    public void do_logout() {
        getToppingCatContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (getToppingCatContract!=null)
            getToppingCatIntract.toppingcatAPICall(this);

    }

    @Override
    public void onError(String msg) {
        getToppingCatContract.toppingcat_failure(msg);

    }
}
