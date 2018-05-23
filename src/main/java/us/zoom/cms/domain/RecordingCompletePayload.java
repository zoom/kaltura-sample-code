/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

/**
 * Created by kavithakannan on 2/16/18.
 */
public class RecordingCompletePayload extends BasePayload {
    private PayLoad payload;

    public PayLoad getPayLoad() {
        return payload;
    }

    public void setPayLoad(PayLoad payLoad) {
        this.payload = payLoad;
    }
}





