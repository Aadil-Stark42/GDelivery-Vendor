package in.vdeliverzvendor.myearnings.mvp_withdrawdet;


import in.vdeliverzvendor.myearnings.model_withdrawdet.WithDrawDetailResponse;

public class WithDrawPresenter implements  WithDrawDetContract.GetwithdrawdetIntractor.OnFinishedListener,WithDrawDetIntract.OnWithDrawListener {

    WithDrawDetContract withDrawDetContract;
    WithDrawDetIntract withDrawDetIntract;
    String TAG = WithDrawPresenter.class.getSimpleName();


    public WithDrawPresenter(WithDrawDetContract withDrawDetContract, WithDrawDetIntract withDrawDetIntract){
        this.withDrawDetContract = withDrawDetContract;
        this.withDrawDetIntract = withDrawDetIntract;
    }

    public void validateDetails(String strWithDrawId){
        withDrawDetIntract.directValidation(strWithDrawId,this);

    }


    @Override
    public void onFinished(WithDrawDetailResponse withDrawDetailResponse) {
        withDrawDetContract.withdrawdet_success(withDrawDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        withDrawDetContract.withdrawdet_failure(error_msg);
    }

    @Override
    public void do_logout() {
        withDrawDetContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
            if (withDrawDetContract!=null)
                withDrawDetIntract.withdrawdetAPICall(this);
    }

    @Override
    public void onError(String msg) {
        withDrawDetContract.withdrawdet_failure(msg);
    }
}
