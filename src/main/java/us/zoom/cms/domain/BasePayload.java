/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kavithakannan on 2/16/18.
 */
public abstract class BasePayload {
    String event;


    @SerializedName("download_token")
    String recordingToken;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRecordingToken() {
        return recordingToken;
    }

    public void setRecordingToken(String recordingToken) {
        this.recordingToken = recordingToken;
    }
}
