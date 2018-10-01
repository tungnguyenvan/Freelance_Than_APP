package com.dev.nguyenvantung.freelance_than.Common;

import com.dev.nguyenvantung.freelance_than.Retrofit.DataClient;

public class Common {
    public static final String HOST = "http://192.168.1.13/";
//    public static final String HOST = "http://192.168.0.47/";
    public static final String WEB_HOST = HOST + "mvc-develop/";
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
    public static final String CONTROLLER_UPLOADIMAGE = "UploadImage";


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

    //request to server
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DATE_REGISTER = "date_register";
    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String FIND = "find";
    public static final String BLOOD = "blood";



    //retrofit
    public static DataClient DATA_CLIENT;
}
