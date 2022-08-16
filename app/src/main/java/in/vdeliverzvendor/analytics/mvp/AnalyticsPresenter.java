package in.vdeliverzvendor.analytics.mvp;


import in.vdeliverzvendor.analytics.model.AnalyticsResponse;

public class AnalyticsPresenter implements  AnalyticsContract.GetanalyticsIntractor.OnFinishedListener,AnalyticsIntract.OnanalyticsListener {

    AnalyticsContract analyticsContract;
    AnalyticsIntract analyticsIntract;
    String TAG = AnalyticsPresenter.class.getSimpleName();


    public AnalyticsPresenter(AnalyticsContract analyticsContract, AnalyticsIntract analyticsIntract){
        this.analyticsContract = analyticsContract;
        this.analyticsIntract = analyticsIntract;
    }

    public void validateDetails(String strmonth,String stryear,String strshop){
        analyticsIntract.directValidation(stryear,strmonth,strshop,this);

    }


    @Override
    public void onFinished(AnalyticsResponse analyticsResponse) {
        analyticsContract.analytics_success(analyticsResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        analyticsContract.analytics_failure(error_msg);
    }

    @Override
    public void do_logout() {
        analyticsContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (analyticsContract!=null)
            analyticsIntract.analyticsAPICall(this);
    }

    @Override
    public void onError(String msg) {
        analyticsContract.analytics_failure(msg);
    }
}
