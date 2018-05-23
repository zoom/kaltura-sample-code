/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import us.zoom.cms.domain.KalturaConfiguration;
import us.zoom.cms.domain.UserKalturaConfig;

/**
 * Created by kavithakannan on 2/22/18.
 */
@Mapper
public interface KalturaMapper {
    public int saveKaltura(KalturaConfiguration kalturaConfiguration);

    public KalturaConfiguration fetchKalturaConfiguration(@Param("zoomAccountId") String accountId);

    public int updateKalturaConfiguration(@Param("userName") String userName,@Param("userId") String userId,
                                          @Param("partnerId") int partnerId, @Param("administratorSecret") String administratorSecret,
                                          @Param("enableUpload") int enableUpload, @Param("categoryByZoomRecording") String categoryByZoomRecording,
                                          @Param("zoomAccountId") String zoomAccountId);

    public int saveUserKaltura(UserKalturaConfig userKalturaConfig);

    public UserKalturaConfig fetchUserKalturaConfiguration(@Param("userId") String userId);

    public int updateUserKaltura(@Param("userId") String userId,@Param("userConfigId") String userConfigId);


}
