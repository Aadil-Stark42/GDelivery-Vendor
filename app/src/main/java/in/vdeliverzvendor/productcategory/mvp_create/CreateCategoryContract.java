package in.vdeliverzvendor.productcategory.mvp_create;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface CreateCategoryContract {

    void createcategory_success(GeneralResponse generalResponse);

    void createcategory_failure(String msg);

    void dashboard_logout();

    interface GetCreateCategoryIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void createcategoryAPICall(GetCreateCategoryIntractor.OnFinishedListener onFinishedListener);
    }
}
