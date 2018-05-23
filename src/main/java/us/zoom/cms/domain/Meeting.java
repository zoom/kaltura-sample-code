/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kavithakannan on 2/16/18.
 */
public class Meeting {

    int duration;
    @SerializedName("start_time")
    String startTime;

    String timezone;

    @SerializedName("host_email")
    String hostEmail;

    String topic;

    @SerializedName("host_id")
    String hostId ;



    @SerializedName("recording_files")
    List<RecordingFile> recordingFiles;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<RecordingFile> getRecordingFiles() {
        return recordingFiles;
    }

    public void setRecordingFiles(List<RecordingFile> recordingFiles) {
        this.recordingFiles = recordingFiles;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

}
