package com.application.refinary.services;



import com.application.refinary.model.EmergencyServiceModel;
import com.application.refinary.model.HouseKeepingModel;
import com.application.refinary.model.LaundryModel;
import com.application.refinary.pojo.alltickets.AllTicketDetailsPojo;
import com.application.refinary.pojo.authenticateMobile.AuthenticateMobile;
import com.application.refinary.pojo.carservice.CarServicePojo;
import com.application.refinary.pojo.emergencyservice.EmergencyServicePojo;
import com.application.refinary.pojo.hoteldirectory.HotelDirectoryPojo;
import com.application.refinary.pojo.housekeeping.HouseKeepingPojo;
import com.application.refinary.pojo.laundry.LaundryPojo;
import com.application.refinary.pojo.laundrydeliverytype.LaundryDeliveryTypePojo;
import com.application.refinary.pojo.laundryticket.LaundryOrderPojo;
import com.application.refinary.pojo.loginmpin.LoginMpin;
import com.application.refinary.pojo.mystay.MyStayPojo;
import com.application.refinary.pojo.navigation.NavigationPojo;
import com.application.refinary.pojo.news.NewsResponse;
import com.application.refinary.pojo.notification.NotificationPojo;
import com.application.refinary.pojo.profile.ProfilePojo;
import com.application.refinary.pojo.sightseeing.SightseeingPojo;
import com.application.refinary.pojo.ticketcreation.HouseKeepingTicket;
import com.application.refinary.pojo.ticketdetails.TicketsDetailsPojo;
import com.application.refinary.pojo.ticketresponse.TicketResponse;
import com.application.refinary.pojo.weather.Weather;
import com.application.refinary.pojo.wineanddine.WineAndDinePojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIMethods {

    // User-Auth

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/login/")
    Call<LoginMpin> loginMpin(@Body Map map);

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/registration")
    Call<AuthenticateMobile> regUser(@Body Map<String, String> map);

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/otp-verification")
    Call<AuthenticateMobile> verifyOTP(@Body Map map);

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/resend-otp/")
    Call<AuthenticateMobile> resendOTP(@Body Map map);

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/create-mpin/")
    Call<AuthenticateMobile> createMpin(@Body Map map);

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","Content-Type: application/json"})
    @POST("v1/guests/reset-mpin")
    Call<AuthenticateMobile> userAuthentication(@Body Map map);

    // Navigation menu

    @Headers({"Authorization: Basic ZXhvbmVjb3JlOmV4b25lY29yZQ==","locationUUID: 4a353709-9230-484d-a38f-37772064bec5","Content-Type: application/json"})
    @GET("/v1/mobile-navigation")
    Call<NavigationPojo> navigationAPI(@HeaderMap Map<String, String> token);


    // Modules

    @Headers({"Content-Type: application/json"})
    @GET("/v1/news")
    Call<NewsResponse> newsApi(@HeaderMap Map<String, String> token, @Query("order") String order, @Query("page") Integer pagenumber,@Query("limit") Integer limitnumber);

    // Weather

    @Headers({"Content-Type: application/json"})
    @GET("/v1/weather")
    Call<Weather> WeatherAPI(@HeaderMap Map<String, String> token);

    // Housekeeping

    @Headers({"Content-Type: application/json"})
    @GET("/v1/housekeeping")
    Call<HouseKeepingPojo> housKeepingAPI(@HeaderMap Map<String, String> token);

    //SightSeeing


    @Headers({"Content-Type: application/json"})
    @GET("/v1/sightseeing")
    Call<SightseeingPojo> sightSeeingApi(@HeaderMap Map<String, String> token, @Query("order") String order, @Query("page") Integer pagenumber, @Query("limit") Integer limitnumber);


    // My-Stay
    @Headers({"Content-Type: application/json"})
    @POST("/v1/guests/reservations")
    Call<MyStayPojo> myStayAPI(@HeaderMap Map<String, String> token);


    //hotel directory

    @Headers({"Content-Type: application/json"})
    @GET("/v1/directory-services")
    Call<HotelDirectoryPojo> hotelDirectoryAPI(@HeaderMap Map<String, String> token);

    //Wine and Dine

    @Headers({"Content-Type: application/json"})
    @GET("/v1/wine-and-dine")
    Call<WineAndDinePojo> wineAndDineAPI(@HeaderMap Map<String, String> token);

    //Car service

    @Headers({"Content-Type: application/json"})
    @GET("/v1/car-rental")
    Call<CarServicePojo> carServiceAPI(@HeaderMap Map<String, String> token);

    //Laundry Service

    @Headers({"Content-Type: application/json"})
    @GET("/v1/laundry")
    Call<LaundryPojo> laundryAPI(@HeaderMap Map<String, String> token);

    @Headers({"Content-Type: application/json"})
    @GET("/v1/laundry/delivery-type")
    Call<LaundryDeliveryTypePojo> deliveryTypeAPI(@HeaderMap Map<String, String> token);

    //Notifications

    @Headers({"Content-Type: application/json"})
    @GET("/v1/notifications")
    Call<NotificationPojo> notificationAPI(@HeaderMap Map<String, String> token);

    //Profile
    @Headers({"Content-Type: application/json"})
    @GET("/v1/guests/profile")
    Call<ProfilePojo> profileAPI(@HeaderMap Map<String, String> token);


    @Headers({"Content-Type: application/json"})
    @PATCH("/v1/guests/profile")
    Call<ProfilePojo> putProfileDeatils(@HeaderMap Map<String, String> headers, @Body Map<String, String> map);

    @Multipart
    @PATCH("/v1/guests/profile")
    Call<ProfilePojo> editUser(@HeaderMap  Map<String, String> headers,@Body Map<String, String> map,
                        @Part("file\"; filename=\"pp.png\" ") RequestBody file,
                               @Part("FirstName") RequestBody fname,
                               @Part("lastName") RequestBody lastName);

    @Multipart
    @PATCH("/v1/guests/profile")
    Call<ProfilePojo> updateProfile(@HeaderMap Map<String, String> headers,
                                    @Part("firstName") RequestBody fname,
                                    @Part("lastName") RequestBody lname,
                                    @Part("email") RequestBody email,
                                    @Part MultipartBody.Part image,
                                    @Part("gender") RequestBody gender,
                                    @Part("maritalStatus") RequestBody mstatus,
                                    @Part("dateOfBirth") RequestBody dob,
                                    @Part("spouseDateOfBirth") RequestBody spousedob,
                                    @Part("anniversaryDate") RequestBody anniversary
                                    );



    // Ticket Creation


    @Headers({"Content-Type: application/json"})
    @POST("/v1/guests/create-order")
    Call<TicketResponse> ticketCreationAPI(@HeaderMap Map<String, String> token, @Body HouseKeepingModel houseKeepingModel);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/guests/create-order")
    Call<TicketResponse> emergencyServiceAPI(@HeaderMap Map<String, String> token, @Body EmergencyServiceModel emergencyServiceModel);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/guests/create-order")
    Call<TicketResponse> laundryTicketCreationAPI(@HeaderMap Map<String, String> token, @Body LaundryModel laundryModel);


    //Call an engineer

    @Headers({"Content-Type: application/json"})
    @GET("/v1/emergency-services")
    Call<EmergencyServicePojo> emergencyServiceAPI(@HeaderMap Map<String, String> token);


    //Ticket List
    @Headers({"Content-Type: application/json"})
    @GET("/v1/guests/get-orders")
    Call<AllTicketDetailsPojo> getallticketDetailsAPI(@HeaderMap Map<String, String> token,
                                                   @Query("locationUUID") String locationUUID,
                                                   @Query("bookingConfNo") String bookingConfNo,
                                                      @Query("page") Integer page,
                                                      @Query("limit") Integer limit,
                                                      @Query("order") String order,
                                                      @Query("orderStatus") String orderStatus);


    // Single Ticket Details

    @Headers({"Content-Type: application/json"})
    @GET("/v1/guests/get-order")
    Call<TicketsDetailsPojo> getticketDetailsAPI(@HeaderMap Map<String, String> token,
                                                 @Query("locationUUID") String locationUUID,
                                                 @Query("bookingConfNo") String bookingConfNo,
                                                 @Query("orderUUID") String order_uuid);


}
