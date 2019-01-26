package com.dev.nguyenvantung.freelance_than.Common;

import com.dev.nguyenvantung.freelance_than.Retrofit.DataClient;

public class Common {
    //public static final String HOST = "http://192.168.0.124/";
    public static final String HOST = "http://hienthan.com/";
    public static final String WEB_HOST = HOST + "dang-ky-hien-than/";
    //public static final String WEB_HOST = HOST + "Freelance_Than_Service/";
    public static final String WEB_API = "api.php";
    public static final String WEB_IMAGE = WEB_HOST + "public/img/";

    //Intent
    public static final String INTENT_PERSON = "INTENT_PERSON";
    public static final String INTENT_PERSON_ID = "INTENT_PERSON_ID";
    public static final String INTENT_LINK_IMAGE = "INTENT_LINK_IMAGE";

    //contrller
    public static final String CONTROLLER = "c";
    public static final String ACTION = "a";
    public static final String CONTROLLER_ADMIN = "admin";
    public static final String CONTROLLER_PERSON = "Person";
    public static final String CONTROLLER_UPLOADIMAGE = "Up";


    //action
    public static final String ACTION_SIGNIN = "signin";
    public static final String ACTION_GET_PERSON_FROM_DATE = "getPersonFromDate";
    public static final String ACTION_DELETE_PERSON = "deletePerson";
    public static final String ACTION_GET_PERSON_STATUS = "getPersonStatus";
    public static final String ACTION_SEARCH_PERSON = "searchPerson";
    public static final String ACTION_ACTIVE_PERSON = "activePerson";
    public static final String ACTION_ORDERBY_PERSON = "oderByPerson";
    public static final String ACTION_UPLOADIMAGE = "uploadImage";
    public static final String ACTION_UPDATE_PERSON = "updatePerson";
    public static final String ACTION_GET_PERSON_FROM_BLOOD_SEX = "getPersonFromSexAndBlood";
    public static final String ACTION_GET_PERSON_FROM_SEX = "getPersonFromSex";
    public static final String ACTION_UPDATE_IMAGE_PERSON = "updateImagePerson";
    public static final String ACTION_UPDATE_TOKEN = "updateToken";
    public static final String ACTION_GET_TOTAL_PERSON = "getTotalPerson";
    public static final String ACTION_GET_TOTAL_ALL = "getTotalPersonFromStatus";
    public static final String ACTION_GET_PERSON_RANGE_DATE = "getPersonRangeDate";

    //request to server
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DATE_REGISTER = "date_register";
    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String FIND = "find";
    public static final String BLOOD = "blood";
    public static final String SEX = "sex";
    public static final String KEY = "key";
    public static final String FROMDAY = "from-day";
    public static final String TODAY = "to-day";
    public static final String DATAIMAGE = "data_image";
    public static final String TOKEN = "token";

    //share preference
    public static final String PREFERENCES_LOGIN = "LOGIN";
    public static final String PREFERENCE_SIGNED = "PREFERENCE_SIGNED";
    public static final String PREFERENCES_LOGIN_USERNAME = "LOGIN_USERNAME";
    public static final String PREFERENCES_LOGIN_PASSWORD = "LOGIN_PASSWORD";


    //retrofit
    public static DataClient DATA_CLIENT;
}
