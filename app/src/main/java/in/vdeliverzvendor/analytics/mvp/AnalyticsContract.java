package in.vdeliverzvendor.analytics.mvp;


import in.vdeliverzvendor.analytics.model.AnalyticsResponse;

public interface AnalyticsContract {

    void analytics_success(AnalyticsResponse analyticsResponse);

    void analytics_failure(String msg);

    void dashboard_logout();

    interface GetanalyticsIntractor {

        interface OnFinishedListener {
            void onFinished(AnalyticsResponse analyticsResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void analyticsAPICall(GetanalyticsIntractor.OnFinishedListener onFinishedListener);
    }
}
