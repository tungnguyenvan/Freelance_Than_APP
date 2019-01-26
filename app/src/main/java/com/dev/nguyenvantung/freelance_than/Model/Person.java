package com.dev.nguyenvantung.freelance_than.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("zalo")
    @Expose
    private String zalo;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("CMND")
    @Expose
    private String cMND;
    @SerializedName("SHK")
    @Expose
    private String sHK;
    @SerializedName("XNDS")
    @Expose
    private String xNDS;
    @SerializedName("GKS")
    @Expose
    private String gKS;
    @SerializedName("GKH")
    @Expose
    private String gKH;
    @SerializedName("khac1")
    @Expose
    private String khac1;
    @SerializedName("khac2")
    @Expose
    private String khac2;
    @SerializedName("khac3")
    @Expose
    private String khac3;
    @SerializedName("khac4")
    @Expose
    private String khac4;
    @SerializedName("khac5")
    @Expose
    private String khac5;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("argee")
    @Expose
    private String argee;
    @SerializedName("blood")
    @Expose
    private String blood;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_register")
    @Expose
    private String dateRegister;
    @SerializedName("status")
    @Expose
    private String status;

    public Person(String id, String name, String sex, String height, String weight, String phone, String zalo, String job, String birthday, String cMND, String sHK, String xNDS, String gKS, String gKH, String khac1, String khac2, String khac3, String khac4, String khac5, String address, String argee, String blood, String description, String dateRegister, String status) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.zalo = zalo;
        this.job = job;
        this.birthday = birthday;
        this.cMND = cMND;
        this.sHK = sHK;
        this.xNDS = xNDS;
        this.gKS = gKS;
        this.gKH = gKH;
        this.khac1 = khac1;
        this.khac2 = khac2;
        this.khac3 = khac3;
        this.khac4 = khac4;
        this.khac5 = khac5;
        this.address = address;
        this.argee = argee;
        this.blood = blood;
        this.description = description;
        this.dateRegister = dateRegister;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCMND() {
        return cMND;
    }

    public void setCMND(String cMND) {
        this.cMND = cMND;
    }

    public String getSHK() {
        return sHK;
    }

    public void setSHK(String sHK) {
        this.sHK = sHK;
    }

    public String getXNDS() {
        return xNDS;
    }

    public void setXNDS(String xNDS) {
        this.xNDS = xNDS;
    }

    public String getGKS() {
        return gKS;
    }

    public void setGKS(String gKS) {
        this.gKS = gKS;
    }

    public String getGKH() {
        return gKH;
    }

    public void setGKH(String gKH) {
        this.gKH = gKH;
    }

    public String getKhac1() {
        return khac1;
    }

    public void setKhac1(String khac1) {
        this.khac1 = khac1;
    }

    public String getKhac2() {
        return khac2;
    }

    public void setKhac2(String khac2) {
        this.khac2 = khac2;
    }

    public String getKhac3() {
        return khac3;
    }

    public void setKhac3(String khac3) {
        this.khac3 = khac3;
    }

    public String getKhac4() {
        return khac4;
    }

    public void setKhac4(String khac4) {
        this.khac4 = khac4;
    }

    public String getKhac5() {
        return khac5;
    }

    public void setKhac5(String khac5) {
        this.khac5 = khac5;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArgee() {
        return argee;
    }

    public void setArgee(String argee) {
        this.argee = argee;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
