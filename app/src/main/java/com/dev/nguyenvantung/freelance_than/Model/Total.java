package com.dev.nguyenvantung.freelance_than.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total {
    @SerializedName("total")
    @Expose
    int total;

    public Total(int total) {
        this.total = total;
    }

    public Total() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
