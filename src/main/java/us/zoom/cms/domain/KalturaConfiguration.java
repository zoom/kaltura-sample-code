/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import java.util.Date;

/**
 * Created by kavithakannan on 2/22/18.
 */
public class KalturaConfiguration {

    String userName;

    String userId;

    int partnerId;

    String administratorSecret;

    int enableUpload;

    String categoryByZoomRecording;

    String zoomAccountId;

    Date creationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getAdministratorSecret() {
        return administratorSecret;
    }

    public void setAdministratorSecret(String administratorSecret) {
        this.administratorSecret = administratorSecret;
    }

    public int getEnableUpload() {
        return enableUpload;
    }

    public void setEnableUpload(int enableUpload) {
        this.enableUpload = enableUpload;
    }

    public String getCategoryByZoomRecording() {
        return categoryByZoomRecording;
    }

    public void setCategoryByZoomRecording(String categoryByZoomRecording) {
        this.categoryByZoomRecording = categoryByZoomRecording;
    }

    public String getZoomAccountId() {
        return zoomAccountId;
    }

    public void setZoomAccountId(String zoomAccountId) {
        this.zoomAccountId = zoomAccountId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
