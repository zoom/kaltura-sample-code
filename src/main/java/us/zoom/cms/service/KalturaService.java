/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.service;

import us.zoom.cms.domain.KalturaConfiguration;
import us.zoom.cms.domain.UserKalturaConfig;

/**
 * Created by kavithakannan on 2/22/18.
 */
public interface KalturaService {
    public int saveKalturaConfiguration(KalturaConfiguration kalturaConfiguration);
    public KalturaConfiguration getKalturaConfiguration(String accountId);

    public int updateKaltura(String userName, String userId, int partnerId, String administratorSecret,
                             int enableUpload, String categoryByZoomRecording, String zoomAccountId);
    public int saveUserKalturaConfiguration(UserKalturaConfig userKalturaConfig);
    public UserKalturaConfig getUserKalturaConfiguration(String userId);
    public int updateUserKalturaConfiguration(String userId, String userConfigId);
    }
