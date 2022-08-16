package in.vdeliverzvendor.deliveryboy_rating.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public class DBoyRatingPresenter implements DBoyRatingIntract.OnNotifyListener,
        DBoyRatingContract.GetDBoyrateIntractor.OnFinishedListener{


    DBoyRatingContract dBoyRatingContract;
    DBoyRatingIntract dBoyRatingIntract;
    String TAG = DBoyRatingPresenter.class.getSimpleName();


    public DBoyRatingPresenter(DBoyRatingContract dBoyRatingContract, DBoyRatingIntract dBoyRatingIntract) {
        this.dBoyRatingContract = dBoyRatingContract;
        this.dBoyRatingIntract = dBoyRatingIntract;
    }

    public void validateDetails(String orderid, String rating, String comment){
        dBoyRatingIntract.directValidation(orderid,rating,comment,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        dBoyRatingContract.dboy_rate_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        dBoyRatingContract.dboy_rate__failure(error_msg);
    }

    @Override
    public void do_logout() {
        dBoyRatingContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(dBoyRatingContract != null) {
            dBoyRatingIntract.dboy_rate_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        dBoyRatingContract.dboy_rate__failure(msg);
    }


}
