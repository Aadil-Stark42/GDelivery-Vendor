package in.vdeliverzvendor.deliveryboy_rating.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface DBoyRatingContract {

    void dboy_rate_success(GeneralResponse generalResponse);

    void dboy_rate__failure(String msg);

    void dashboard_logout();

    interface GetDBoyrateIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void dboy_rate_APICall(OnFinishedListener onFinishedListener);
    }

}
