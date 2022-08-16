package in.vdeliverzvendor.dashboard.mvp;


import in.vdeliverzvendor.dashboard.model_dashboard.DashBoardResponse;

public interface DashboardContract {

    void dashboard_success(DashBoardResponse dashBoardResponse);

    void dashboard_failure(String msg);

    void dashboard_logout();

    interface GetDashboardIntractor {

        interface OnFinishedListener {
            void onFinished(DashBoardResponse dashBoardResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getdashboardAPICall(GetDashboardIntractor.OnFinishedListener onFinishedListener);
    }
}
