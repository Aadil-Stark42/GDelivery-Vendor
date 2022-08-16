package in.vdeliverzvendor.dashboard.mvp;


import in.vdeliverzvendor.dashboard.model_dashboard.DashBoardResponse;

public class DashboardPresenter implements  DashboardContract.GetDashboardIntractor.OnFinishedListener,DashboardIntract.OndashboardListener {

    DashboardContract dashboardContract;
    DashboardIntract dashboardIntract;
    String TAG = DashboardPresenter.class.getSimpleName();


    public DashboardPresenter(DashboardContract dashboardContract, DashboardIntract dashboardIntract){
        this.dashboardContract = dashboardContract;
        this.dashboardIntract = dashboardIntract;
    }

    public void validateDetails(){
        dashboardIntract.directValidation(this);

    }


    @Override
    public void onFinished(DashBoardResponse dashBoardResponse) {
        dashboardContract.dashboard_success(dashBoardResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        dashboardContract.dashboard_failure(error_msg);

    }

    @Override
    public void do_logout() {
        dashboardContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (dashboardContract!=null)
            dashboardIntract.getdashboardAPICall(this);

    }

    @Override
    public void onError(String msg) {
        dashboardContract.dashboard_failure(msg);
    }
}
