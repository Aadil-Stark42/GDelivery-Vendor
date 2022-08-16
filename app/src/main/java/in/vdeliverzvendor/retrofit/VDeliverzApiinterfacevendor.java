package in.vdeliverzvendor.retrofit;


import java.util.List;

import in.vdeliverzvendor.addnewproduct.model_add.AddProductResponse;
import in.vdeliverzvendor.addnewproduct.model_get.GetProductResponse;
import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;
import in.vdeliverzvendor.analytics.model.AnalyticsResponse;
import in.vdeliverzvendor.bankdetail.model_banklist.BankListResponse;
import in.vdeliverzvendor.bankdetail.model_withdraw.WithDrawResponse;
import in.vdeliverzvendor.createoutlet.model_getcuisine.CuisineListResponse;
import in.vdeliverzvendor.dashboard.model_dashboard.DashBoardResponse;
import in.vdeliverzvendor.login.model.LoginResponse;
import in.vdeliverzvendor.myearnings.model_orderhistory.OrderHistroyResponse;
import in.vdeliverzvendor.myearnings.model_withdrawdet.WithDrawDetailResponse;
import in.vdeliverzvendor.notification.model.NotificationListResponse;
import in.vdeliverzvendor.orders.model.OrdersListResponse;
import in.vdeliverzvendor.orders.model_ordredetails.OrderDetailResponse;
import in.vdeliverzvendor.productcategory.model_catlist.ProductCatListResponse;
import in.vdeliverzvendor.requestpayment.model.PaymentRequestResponse;
import in.vdeliverzvendor.shoplist.model.ShopListResponse;
import in.vdeliverzvendor.shopoverview.model.ShopDetailResponse;
import in.vdeliverzvendor.shopoverview.model_product.ShopProductListResponse;
import in.vdeliverzvendor.productlist.model.ProductListResponse;
import in.vdeliverzvendor.productview.model.ProductDetailResponse;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.slot.model_getdetail.SlotDetailResponse;
import in.vdeliverzvendor.slotlist.model.SlotListResponse;
import in.vdeliverzvendor.updatetopping.model_gettopping.ToppingDetailResponse;
import in.vdeliverzvendor.updatetstock.model_getstock.StockDetailResponse;
import in.vdeliverzvendor.userdetail.model.UserDetailResponse;
import in.vdeliverzvendor.utils.GeneralResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VDeliverzApiinterfacevendor {


    //Registration
    @FormUrlEncoded
    /*@Headers("Content-Type: application/json")*/
    @Headers({"Accept: application/json"})
    @POST("register")
    Call<SignupResponse> signup_api_call(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("mobile") String mobile,
                                         @Field("password") String password,
                                         @Field("confirm_password") String confirm_password);

    //send-otp
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("send-otp")
    Call<SignupResponse> sendotp_api_call(@Field("mobile") String mobile);


    //verify-otp
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("verify-otp")
    Call<GeneralResponse> verifymobnumotp_api_call(@Field("mobile") String mobile,
                                                   @Field("otp") String otp);


    //login
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("login")
    Call<LoginResponse> getloginApi(@Field("email") String stremail,
                                    @Field("password") String strpassword,
                                    @Field("fcm") String strfcm);

    //send-otp-mail
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("send-otp-mail")
    Call<SignupResponse> getotoforemail(@Field("email") String mobile);


    //verify-otp-mail
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("verify-mail-otp")
    Call<GeneralResponse> verifyemailotp(@Field("email") String stremail,
                                         @Field("otp") String otp);


    @Headers({"Accept: application/json"})
    @GET("products")
    Call<ProductListResponse> getproductlist(@Query("page") String page);


    @Headers({"Accept: application/json"})
    @GET("shops")
    Call<ShopListResponse> getshopList(@Query("page") String page);


    //productdetailview
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("product-detail")
    Call<ProductDetailResponse> getproductdetail(@Field("product_id") String strProductId);

    //delete product
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delete-product")
    Call<GeneralResponse> deleteproduct(@Field("product_id") String strProductId);


    //Product Status Change
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("change-status")
    Call<GeneralResponse> updateproductstatus(@Field("id") String strProductId,
                                              @Field("name") String strname,
                                              @Field("status") String strStatus);

    //shopdetailoverview
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("shop-detail")
    Call<ShopDetailResponse> getshopdetail(@Field("shop_id") String strshopId);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("shops-search")
    Call<ShopListResponse> getsearchlist(@Field("search") String strsearch,
                                         @Query("page") String page);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("products-search")
    Call<ProductListResponse> getproductsearchlist(@Field("search") String strsearch,
                                                   @Query("page") String page);

    //shopproductlist
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("shop-products")
    Call<ShopProductListResponse> getshopproduct(@Field("shop_id") String strshopId);


    //getproduct
    @Headers({"Accept: application/json"})
    @GET("product-create")
    Call<GetProductResponse> getproduct();


    //addproducts
    @Multipart
    @Headers({"Accept: application/json"})
    @POST("add-product")
    Call<AddProductResponse> addproduct(@Part("shop_id") RequestBody strshopId,
                                        @Part("category_id") RequestBody strShopCatId,
                                        @Part("sub_category_id") RequestBody strproductCatId,
                                        @Part("variety") RequestBody varid,
                                        @Part("name") RequestBody strname,
                                        @Part MultipartBody.Part file,
                                        @Part("description") RequestBody strDesc,
                                        @Part("cuisine_id") RequestBody strcuiId);


    //addstock
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("add-stock")
    Call<GeneralResponse> addproductstock(@Field("product_id") String strproductId,
                                          @Field("actual_price") String strActPrice,
                                          @Field("selling_price") String strSellingPrice,
                                          @Field("available") String strAvailable,
                                          @Field("size") String strSize,
                                          @Field("unit") String strUnit,
                                          @Field("variant") String strVar);


    //addstock
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("add-topping")
    Call<GeneralResponse> addtopping(@Field("product_id") String strproductId,
                                     @Field("price") String strActPrice,
                                     @Field("available") String strAvailable,
                                     @Field("title_id") String strtitleid,
                                     @Field("name") String strname,
                                     @Field("variety") String strVar);


    //gettoppingcat
    @Headers({"Accept: application/json"})
    @GET("titles")
    Call<ToppingCatResponse> gettoppingcat();


    @Headers({"Accept: application/json"})
    @GET("logout")
    Call<GeneralResponse> logout();


    //addproducts
    @Headers({"Accept: application/json"})
    @Multipart
    @POST("update-product")
    Call<GeneralResponse> updateprodcut(@Part("shop_id") RequestBody strshopId,
                                        @Part("product_id") RequestBody strproductid,
                                        @Part("category_id") RequestBody strShopCatId,
                                        @Part("sub_category_id") RequestBody strproductCatId,
                                        @Part("variety") RequestBody varid,
                                        @Part("name") RequestBody strname,
                                        @Part MultipartBody.Part file,
                                        @Part("description") RequestBody strDesc,
                                        @Part("cuisine_id") RequestBody strcuiId);

    //delete stock
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delete-stock")
    Call<GeneralResponse> deletestock(@Field("stock_id") String strstockid);

    //delete topping
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delete-topping")
    Call<GeneralResponse> deletetopping(@Field("topping_id") String strtoppingid);


    //Update Stock
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("stock-detail")
    Call<StockDetailResponse> getstockdetail(@Field("stock_id") String strstockid);


    //Update topping
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("topping-detail")
    Call<ToppingDetailResponse> gettoppingdetail(@Field("topping_id") String strtoppingid);


    //update stock
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("update-stock")
    Call<GeneralResponse> updatestcok(@Field("product_id") String strproductId,
                                      @Field("stock_id") String strstockid,
                                      @Field("actual_price") String strActPrice,
                                      @Field("selling_price") String strSellingPrice,
                                      @Field("available") String strAvailable,
                                      @Field("size") String strSize,
                                      @Field("unit") String strUnit,
                                      @Field("variant") String strVar);


    //update topping
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("update-topping")
    Call<GeneralResponse> updatetopping(@Field("product_id") String strproductId,
                                        @Field("topping_id") String strtoppingid,
                                        @Field("price") String strActPrice,
                                        @Field("available") String strAvailable,
                                        @Field("title_id") String strtitleid,
                                        @Field("name") String strname,
                                        @Field("variety") String strVar);


    @Headers({"Accept: application/json"})
    @GET("orders")
    Call<OrdersListResponse> getorderlist(@Query("page") String page);

    //Order Detail
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("order-detail")
    Call<OrderDetailResponse> getorderdetails(@Field("order_id") String strorderid);

    //Order Detail
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("order-review")
    Call<GeneralResponse> updateorderstatus(@Field("order_id") String strorderid,
                                            @Field("action") String straction);


    //addproductwithstock
    @Multipart
    @Headers({"Accept: application/json"})
    @POST("product-stocks")
    Call<GeneralResponse> addproductwithstock(@Part("shop_id") RequestBody strshopId,
                                              @Part("category_id") RequestBody strShopCatId,
                                              @Part("sub_category_id") RequestBody strproductCatId,
                                              @Part("variety") RequestBody varid,
                                              @Part("name") RequestBody strname,
                                              @Part MultipartBody.Part file,
                                              @Part("description") RequestBody strDesc,
                                              @Part("cuisine_id") RequestBody strcuiId,

                                              @Part("st_actual_price") RequestBody strstActPrice,
                                              @Part("st_selling_price") RequestBody strstSellingPrice,
                                              @Part("st_available") RequestBody strstAvailable,
                                              @Part("st_size") RequestBody strstSize,
                                              @Part("st_unit") RequestBody strstUnit,
                                              @Part("st_variant") RequestBody strstVar,

                                              @Part("title_id") RequestBody strtoptitleid,
                                              @Part("top_name") RequestBody strtopname,
                                              @Part("top_variety") RequestBody strtitleid,
                                              @Part("top_available") RequestBody strtopava,
                                              @Part("top_price") RequestBody strtopprice);


    @Headers({"Accept: application/json"})
    @GET("cuisines")
    Call<CuisineListResponse> getcuisinelist();


    @Multipart
    @Headers({"Accept: application/json"})
    @POST("shop-create")
    Call<GeneralResponse> createoutlet(
            @Part MultipartBody.Part shop_name,
            @Part MultipartBody.Part shop_email,
            @Part MultipartBody.Part shop_mobile,
            @Part MultipartBody.Part latitude,
            @Part MultipartBody.Part longitude,
            @Part MultipartBody.Part street,
            @Part MultipartBody.Part city,
            @Part MultipartBody.Part area,
            @Part MultipartBody.Part price,
            @Part MultipartBody.Part assign,
            @Part MultipartBody.Part opening_time,
            @Part MultipartBody.Part closing_time,
            @Part MultipartBody.Part comission,
            @Part MultipartBody.Part description,
            @Part MultipartBody.Part min_amount,
            @Part MultipartBody.Part radius,
            @Part("weekdays[]") List<String> weekdays,
            @Part("cuisines[]") List<Integer> cuisines,
            @Part MultipartBody.Part shop_image,
            @Part MultipartBody.Part banner_image);


    @Multipart
    @Headers({"Accept: application/json"})
    @POST("shop-update")
    Call<GeneralResponse> updateshop(
            @Part MultipartBody.Part shop_id,
            @Part MultipartBody.Part assign,
            @Part MultipartBody.Part opening_time,
            @Part MultipartBody.Part closing_time,
            @Part MultipartBody.Part description,
            @Part MultipartBody.Part min_amount,
            @Part MultipartBody.Part radius,
            @Part("weekdays[]") List<String> weekdays,
            @Part MultipartBody.Part shop_image,
            @Part MultipartBody.Part banner_image);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("payments")
    Call<PaymentRequestResponse> getpayemetrequest(@Field("shop_id") String strShopId,
                                                   @Query("page") String page);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("banks")
    Call<BankListResponse> getbanks(@Field("shop_id") String strShopId);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("add-bank")
    Call<GeneralResponse> createbank(@Field("shop_id") String strShopId,
                                     @Field("name") String strName,
                                     @Field("acc_no") String strAccountNum,
                                     @Field("city") String strCity,
                                     @Field("ifsc") String strifsc,
                                     @Field("branch") String strbranch,
                                     @Field("bank_name") String strbankname);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("request-withdrawal")
    Call<WithDrawResponse> requestwithdrw(@Field("shop_id") String strShopId,
                                          @Field("bank_id") String strBankId);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("orders-history")
    Call<OrderHistroyResponse> getorderhistory(@Field("shop_id") String strShopId);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("withdrawal-detail")
    Call<WithDrawDetailResponse> getwithdrawdet(@Field("withdrawal_id") String strwithdrawid);


    @Headers({"Accept: application/json"})
    @POST("dashboard")
    Call<DashBoardResponse> getdashboarddet();


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("slots")
    Call<SlotListResponse> getslotlist(@Field("shop_id") String strshopid);


    @Multipart
    @Headers({"Accept: application/json"})
    @POST("create-slot")
    Call<GeneralResponse> createslot(@Part MultipartBody.Part from,
                                     @Part MultipartBody.Part to,
                                     @Part MultipartBody.Part shop_id,
                                     @Part("weekdays[]") List<String> weekdays);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("slot-detail")
    Call<SlotDetailResponse> getslotdetail(@Field("slot_id") String strshopid);


    @Multipart
    @Headers({"Accept: application/json"})
    @POST("update-slot")
    Call<GeneralResponse> updateslot(@Part MultipartBody.Part from,
                                     @Part MultipartBody.Part to,
                                     @Part MultipartBody.Part shop_id,
                                     @Part MultipartBody.Part slot_id,
                                     @Part("weekdays[]") List<String> weekdays);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("slot-delete")
    Call<GeneralResponse> deleteslot(@Field("slot_id") String strshopid);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("product-categories")
    Call<ProductCatListResponse> getproductcat(@Field("shop_id") String strshopid,
                                               @Query("page") String page);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("category-products")
    Call<ProductListResponse> getcatproductlist(@Field("product_category_id") String strproductcatid,
                                                @Field("shop_id") String strShopId,
                                                @Query("page") String page);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("create-product-category")
    Call<GeneralResponse> createcategoryforproduct(@Field("name") String strname);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("analytics")
    Call<AnalyticsResponse> getanalyticsdata(@Field("year") String stryear,
                                             @Field("month") String strmonth,
                                             @Field("shop_id") String strshopid);


    @Headers({"Accept: application/json"})
    @GET("user")
    Call<UserDetailResponse> getuserdetail();

    @Multipart
    @Headers({"Accept: application/json"})
    @POST("user-update")
    Call<GeneralResponse> userupdate(@Part MultipartBody.Part mobile,
                                     @Part MultipartBody.Part name,
                                     @Part MultipartBody.Part email,
                                     @Part MultipartBody.Part profile_image);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("reset-password")
    Call<GeneralResponse> reserpassword(@Field("mobile") String strphonenum,
                                        @Field("password") String strpassword,
                                        @Field("confirm_password") String strconfirmpassword);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("add-title")
    Call<GeneralResponse> addtitle(@Field("name") String strname);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("order-search")
    Call<OrdersListResponse> getordersearchlist(@Query("page") String page,
                                                @Field("search") String strsearch);


    @Headers({"Accept: application/json"})
    @GET("notifications")
    Call<NotificationListResponse> getnotificationlist(@Query("page") String page);


    //Delivery boy rating
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("vendor-delivery-rating")
    Call<GeneralResponse> give_deliveryboy_rating(@Field("order_id") String order_id,
                                                  @Field("rating") String rating,
                                                  @Field("comment") String comment);


}
