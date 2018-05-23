/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import java.util.Date;

/**
 * Created by kavithakannan on 3/5/18.
 */
public class UserKalturaConfig {
    String userId;
    String userConfigId;
    String accountId;
    Date   creationTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserConfigId() {
        return userConfigId;
    }

    public void setUserConfigId(String userConfigId) {
        this.userConfigId = userConfigId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
