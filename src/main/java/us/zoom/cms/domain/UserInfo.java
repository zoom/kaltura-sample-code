/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kavithakannan on 2/27/18.
 */
public class UserInfo {

    String id;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;


    String email;

    String type;

    String pmi;

    String timezone;

    String dept;

    @SerializedName("created_at")
    String createAt;

    @SerializedName("account_id")
    String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPmi() {
        return pmi;
    }

    public void setPmi(String pmi) {
        this.pmi = pmi;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
