package in.vdeliverzvendor.createoutlet.mvp_getcuisine;


import in.vdeliverzvendor.createoutlet.model_getcuisine.CuisineListResponse;

public class GetCuisinePresenter implements  GetCuisineContract.GetcuisineIntractor.OnFinishedListener,
        GetCuisineIntract.OnCuisineListener {

    GetCuisineContract cuisineContract;
    GetCuisineIntract cuisineIntract;
    String TAG = GetCuisinePresenter.class.getSimpleName();


    public GetCuisinePresenter(GetCuisineContract cuisineContract, GetCuisineIntract cuisineIntract){
        this.cuisineContract = cuisineContract;
        this.cuisineIntract = cuisineIntract;
    }

    public void validateDetails(){
        cuisineIntract.directValidation(this);

    }


    @Override
    public void onFinished(CuisineListResponse cuisineListResponse) {
        cuisineContract.getcuisine_success(cuisineListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        cuisineContract.getcuisine_failure(error_msg);
    }

    @Override
    public void do_logout() {
        cuisineContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (cuisineContract!=null)
            cuisineIntract.getcuisineCall(this);

    }

    @Override
    public void onError(String msg) {
        cuisineContract.getcuisine_failure(msg);

    }
}
