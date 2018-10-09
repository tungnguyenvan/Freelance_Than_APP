package com.dev.nguyenvantung.freelance_than.Retrofit;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Admin;
import com.dev.nguyenvantung.freelance_than.Model.Message;
import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    //=========================== Login ==========================
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Admin> signin(@Field(Common.CONTROLLER) String controller,
                              @Field(Common.ACTION) String action,
                              @Field(Common.USER_NAME) String user_name,
                              @Field(Common.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Message> updateToken(@Field(Common.CONTROLLER) String controller,
                              @Field(Common.ACTION) String action,
                              @Field(Common.ID) int id,
                              @Field(Common.TOKEN) String token);

    //////////////////////////////////////////////////////////////
    //=========================== Person ==========================
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonFromDate(@Field(Common.CONTROLLER) String controller,
                                         @Field(Common.ACTION) String action,
                                         @Field(Common.DATE_REGISTER) String date_register);

    //delete

    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Message> deletePerson(@Field(Common.CONTROLLER) String controller,
                               @Field(Common.ACTION) String action,
                               @Field(Common.ID) int id);

    //getdata status
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonStatus(@Field(Common.CONTROLLER) String Controller,
                                       @Field(Common.ACTION) String action,
                                       @Field(Common.STATUS) int status);

    //Search Person
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> searchPerson(@Field(Common.CONTROLLER) String controller,
                                    @Field(Common.ACTION) String action,
                                    @Field(Common.FIND) String find);

    //active person
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Message> activePerson(@Field(Common.CONTROLLER) String controller,
                               @Field(Common.ACTION) String action,
                               @Field(Common.ID) int id, @Field(Common.STATUS) int status);

    //get person sorted
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonSorted(@Field(Common.CONTROLLER) String controller,
                                       @Field(Common.ACTION) String action,
                                       @Field(Common.STATUS) int status, @Field(Common.BLOOD) int blood);
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonSorted(@Field(Common.CONTROLLER) String controller,
                                       @Field(Common.ACTION) String action,
                                       @Field(Common.DATE_REGISTER) String date_register,
                                       @Field(Common.BLOOD) int blood);


    //Update person
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Message> updatePerson(@Field(Common.CONTROLLER) String controller,
                               @Field(Common.ACTION) String action,
                               @Field("id") int id,
                               @Field("name") String name,
                               @Field("sex") String sex,
                               @Field("height") String height,
                               @Field("weight") String weight,
                               @Field("phone") String phone,
                               @Field("zalo") String zalo,
                               @Field("job") String job,
                               @Field("birthday") String birthday,
                               @Field("CMND") String CMND,
                               @Field("SHK") String SHK,
                               @Field("XNDS") String XNDS,
                               @Field("GKS") String GKS,
                               @Field("GKH") String GKH,
                               @Field("khac1") String khac1,
                               @Field("khac2") String khac2,
                               @Field("khac3") String khac3,
                               @Field("khac4") String khac4,
                               @Field("khac5") String khac5,
                               @Field("address") String address,
                               @Field("argee") String argee,
                               @Field("blood") String blood,
                               @Field("description") String description,
                               @Field("date_register") String date_register,
                               @Field("status") String status);

    //get person from blood and sex
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonFromSexAndBlood(@Field(Common.CONTROLLER) String controller,
                                                @Field(Common.ACTION) String action,
                                                @Field(Common.SEX) String sex,
                                                @Field(Common.BLOOD) int blood,
                                                @Field(Common.STATUS) int status);
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonFromSexAndBlood(@Field(Common.CONTROLLER) String controller,
                                                @Field(Common.ACTION) String action,
                                                @Field(Common.SEX) String sex,
                                                @Field(Common.BLOOD) int blood,
                                                @Field(Common.DATE_REGISTER) String date_register);

    //get Person from sex
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonFromSex(@Field(Common.CONTROLLER) String controller,
                                        @Field(Common.ACTION) String action,
                                        @Field(Common.SEX) String sex,
                                        @Field(Common.STATUS) int status);
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<List<Person>> getPersonFromSex(@Field(Common.CONTROLLER) String controller,
                                        @Field(Common.ACTION) String action,
                                        @Field(Common.SEX) String sex,
                                        @Field(Common.DATE_REGISTER) String date_register);

    //update Image person
    @FormUrlEncoded
    @POST(Common.WEB_API)
    Call<Message> updateImagePerson(@Field(Common.CONTROLLER) String controller,
                                    @Field(Common.ACTION) String action,
                                    @Field(Common.KEY) String key,
                                    @Field(Common.DATAIMAGE) String data_image,
                                    @Field(Common.ID) int id);


    //===================== UPLOAD IMAGE ============================
    @Multipart
    @POST(Common.WEB_API)
    Call<Message> upImage (@Part(Common.CONTROLLER) RequestBody controller,
                           @Part(Common.ACTION) RequestBody action,
                           @Part MultipartBody.Part photo);
}
