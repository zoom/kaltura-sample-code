/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.zoom.cms.domain.KalturaConfiguration;
import us.zoom.cms.domain.UserKalturaConfig;
import us.zoom.cms.mapper.KalturaMapper;

/**
 * Created by kavithakannan on 2/22/18.
 */
@Service
public class KalturaServiceImpl implements KalturaService {
    @Autowired
    KalturaMapper kalturaMapper;


    @Override
    public int saveKalturaConfiguration(KalturaConfiguration kalturaConfiguration) {
        return kalturaMapper.saveKaltura(kalturaConfiguration);

    }

    @Override
    public KalturaConfiguration getKalturaConfiguration(String accountId) {
        return kalturaMapper.fetchKalturaConfiguration(accountId);
    }

    @Override
    public int updateKaltura(String userName, String userId, int partnerId, String administratorSecret, int enableUpload, String categoryByZoomRecording, String zoomAccountID) {
        return  kalturaMapper.updateKalturaConfiguration(userName,userId,partnerId,administratorSecret,enableUpload,categoryByZoomRecording, zoomAccountID);
    }

    @Override
    public int saveUserKalturaConfiguration(UserKalturaConfig userKalturaConfig) {
        return kalturaMapper.saveUserKaltura(userKalturaConfig);

    }
    @Override
    public UserKalturaConfig getUserKalturaConfiguration(String userId) {
        return kalturaMapper.fetchUserKalturaConfiguration(userId);
    }

    @Override
    public int updateUserKalturaConfiguration(String userId, String userConfigId) {
        return kalturaMapper.updateUserKaltura(userId, userConfigId);

    }

}
