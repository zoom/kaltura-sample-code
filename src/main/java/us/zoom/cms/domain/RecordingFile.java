/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by kavithakannan on 2/16/18.
 */
public class RecordingFile {
    String id;
    @SerializedName("meeting_id")
    String meetingId;
    @SerializedName("recording_start")
    Date recordingStartTime;
    @SerializedName("recording_end")
    Date recordingEndTime;
    @SerializedName("file_type")
    String fileType;
    @SerializedName("file_size")
    String fileSize;
    @SerializedName("play_url")
    String playUrl;
    @SerializedName("download_url")
    String downloadUrl;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public Date getRecordingStartTime() {
        return recordingStartTime;
    }

    public void setRecordingStartTime(Date recordingStartTime) {
        this.recordingStartTime = recordingStartTime;
    }

    public Date getRecordingEndTime() {
        return recordingEndTime;
    }

    public void setRecordingEndTime(Date recordingEndTime) {
        this.recordingEndTime = recordingEndTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
