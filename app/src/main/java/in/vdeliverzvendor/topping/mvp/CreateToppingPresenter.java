package in.vdeliverzvendor.topping.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public class CreateToppingPresenter implements  CreateToppingContract.Getcreatetopping_Intractor.OnFinishedListener,CreateToppingIntract.OncreatetoppingListener {

    CreateToppingContract createToppingContract;
    CreateToppingIntract createToppingIntract;
    String TAG = CreateToppingPresenter.class.getSimpleName();


    public CreateToppingPresenter(CreateToppingContract createToppingContract, CreateToppingIntract createToppingIntract){
        this.createToppingContract = createToppingContract;
        this.createToppingIntract = createToppingIntract;
    }

    public void validateDetails(String strname){
        createToppingIntract.directValidation(strname,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        createToppingContract.createtopping_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        createToppingContract.createtopping_failure(error_msg);
    }

    @Override
    public void do_logout() {
        createToppingContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (createToppingContract!=null)
            createToppingIntract.createtoppingAPICall(this);
    }

    @Override
    public void onError(String msg) {
        createToppingContract.createtopping_failure(msg);
    }
}
