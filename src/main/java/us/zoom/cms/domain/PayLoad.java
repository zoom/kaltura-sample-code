/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kavithakannan on 2/16/18.
 */
public class PayLoad {
    Meeting meeting;

    @SerializedName("account_id")
    String accountId;

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
