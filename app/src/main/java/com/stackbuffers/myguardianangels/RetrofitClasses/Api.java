package com.stackbuffers.myguardianangels.RetrofitClasses;

import com.google.gson.JsonObject;
import com.stackbuffers.myguardianangels.Models.Accept_Request_Response;
import com.stackbuffers.myguardianangels.Models.Angel_Delete_Request;
import com.stackbuffers.myguardianangels.Models.Angel_Request_Response;
import com.stackbuffers.myguardianangels.Models.CountryResponse;
import com.stackbuffers.myguardianangels.Models.GetProfile_Response;
import com.stackbuffers.myguardianangels.Models.Google_Signup_Response;
import com.stackbuffers.myguardianangels.Models.GuardianAngel_Edit_Response;
import com.stackbuffers.myguardianangels.Models.Login_Response;
import com.stackbuffers.myguardianangels.Models.SignUp_Response;
import com.stackbuffers.myguardianangels.Models.StoreAudio_Response;
import com.stackbuffers.myguardianangels.Models.Update_Password_Response;
import com.stackbuffers.myguardianangels.Models.UploadImage;
import com.stackbuffers.myguardianangels.Models.Verify_Email_Response;
import com.stackbuffers.myguardianangels.Models.Verify_Resend_Response;
import com.stackbuffers.myguardianangels.Models.VideoUpload.VideoUpload;
import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidenceResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("user/login")
    Call<Login_Response> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @Multipart
    @POST("video/store")
    Call<VideoUpload> VideoUpload(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody user_id,
            @Part("duration") RequestBody duration,
            @Part("format") RequestBody format,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude
    );


    @Multipart
    @POST("audio/store")
    Call<VideoUpload> AudioUpload(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody user_id,
            @Part("duration") RequestBody duration,
            @Part("format") RequestBody format,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude
    );


    @FormUrlEncoded
    @POST("user/avatar")
    Call<JsonObject> updateAvatar(
            @Field("avatar") String avatar,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user/avatar/url")
    Call<JsonObject> getAvatar(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user/register")
    Call<SignUp_Response> signup(
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("user/password/reset")
    Call<Verify_Resend_Response> forget(
            @Field("email") String email

    );

    @FormUrlEncoded
    @POST("user/get_profile")
    Call<GetProfile_Response> getProfile(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user/email/varify/resend")
    Call<Verify_Email_Response> verify(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/password/update")
    Call<Update_Password_Response> updatePassword(
            @Field("old_password") String old_password,
            @Field("user_id") String user_id,
            @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("user/update")
    Call<GetProfile_Response> updateProfile(
            @Field("dob") String dob,
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("emergency_contact") String emergency_contact,
            @Field("gender") String gender,
            @Field("country") String country,
            @Field("city") String city,
            @Field("address") String address,
            @Field("timePeriod") String timePeriod);
    @FormUrlEncoded
    @POST("user/avatar")
    Call<UploadImage> updateImg(
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("user/update")
    Call<GetProfile_Response> updateProfile_(
            @Field("dob") String dob,
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("gender") String gender,
            @Field("country") String country,
            @Field("city") String city,
            @Field("address") String address,
            @Field("timePeriod") String timePeriod);

    @FormUrlEncoded
    @POST("angel/request/store")
    Call<Angel_Request_Response> addAngel(

            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("relation") String relation

    );

    @FormUrlEncoded
    @POST("angel/my/angel/lists")
    Call<Angel_Request_Response> requestToAngel(
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("angel/my/angel/requests")
    Call<Angel_Request_Response> showRequestToAngel(
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("angel/request/status")
    Call<Accept_Request_Response> acceptRequest(
            @Field("record_id") int record_id
    );

    @FormUrlEncoded
    @POST("angel/delete/request?user_")
    Call<Angel_Delete_Request> deleteRequest(

            @Field("record_id") String record_id

    );

    @FormUrlEncoded
    @POST("get/country")
    Call<CountryResponse> getCountries(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("get/city")
    Call<CountryResponse> getCities(
            @Field("country_id") String country_id
    );

    @FormUrlEncoded
    @POST("angel/request/update")
    Call<GuardianAngel_Edit_Response> editAngel(
            @Field("record_id") String record_id,
            @Field("user_id") String user_id,
            @Field("angel_id") String angel_id,
            @Field("angel_name") String angel_name,
            @Field("angel_email") String angel_email,
            @Field("angel_relation") String angel_relation

    );

    //Guardian Angel Delete
    @FormUrlEncoded
    @POST("angel/delete/request")
    Call<Angel_Delete_Request> deleteGuardianAngel(
            @Field("record_id") int record_id
    );


    @Multipart
    @POST("audio/store")
    Call<StoreAudio_Response> storeAudio(

            @Part("user_id") RequestBody user_id,
            @Part("duration") RequestBody duration,
            @Part("format") RequestBody format,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude,
            @Part MultipartBody.Part file


    );

    @FormUrlEncoded
    @POST("social/register")
    Call<Google_Signup_Response> googleSignup(
            @Field("name") String name,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/social/login")
    Call<Login_Response> googleSignIn(

            @Field("email") String email

    );

    @FormUrlEncoded
    @POST("mix/video/audio/list")
    Call<MyEvidenceResponse> myEvidence(

            @Field("user_id") String userID

    );

    @FormUrlEncoded
    @POST("audio/delete")
    Call<JsonObject> deleteAudioEvd(
            @Field("user_id") String userID,
            @Field("audio_id") String audioID

    );

    @FormUrlEncoded
    @POST("video/delete")
    Call<JsonObject> deleteVideoEvd(
            @Field("user_id") String userID,
            @Field("video_id") String videoID

    );

    @FormUrlEncoded
    @POST("angel/get/data/audio/video")
    Call<MyEvidenceResponse> angelEvidence(

            @Field("user_id") String userID

    );

}

