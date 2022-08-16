package in.vdeliverzvendor.createoutlet.mvp_getcuisine;


import in.vdeliverzvendor.createoutlet.model_getcuisine.CuisineListResponse;

public interface GetCuisineContract {

    void getcuisine_success(CuisineListResponse cuisineListResponse);

    void getcuisine_failure(String msg);

    void dashboard_logout();

    interface GetcuisineIntractor {

        interface OnFinishedListener {
            void onFinished(CuisineListResponse cuisineListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getcuisineCall(OnFinishedListener onFinishedListener);
    }
}
