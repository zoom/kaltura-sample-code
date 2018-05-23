/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.controller;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.zoom.cms.domain.KalturaConfiguration;
import us.zoom.cms.domain.UserKalturaConfig;
import us.zoom.cms.exception.ValidationException;
import us.zoom.cms.jwt.TokenParser;
import us.zoom.cms.service.KalturaService;

import java.util.Map;

/**
 * Created by kavithakannan on 2/20/18.
 */
@RequestMapping("/api/v1")
@RestController
public class KalturaController {

    @Autowired
    KalturaService kalturaService;

    @RequestMapping(value = "/kaltura/saveConfig", method = RequestMethod.POST)
    @ResponseBody
    public String createKalturaConfiguration(@RequestParam String username, @RequestParam String userid,
                                             @RequestParam String partnerid,@RequestParam String adminsecret,
                                             @RequestParam String enableupload, @RequestParam String category, @RequestParam String token)  {
        if (null == username || null == userid || null == partnerid || null == adminsecret
                || null == enableupload || null == category || null == token) {
            throw new ValidationException("Request Parameter Null ");
        }

        Map claims = TokenParser.verifyToken(token);
        Claim claim = (Claim)claims.get("accountId");
        String zoomAccountId = claim.asString();

        if(null == zoomAccountId) {
            throw new ValidationException("Token does not contain accountId ");
        }
        int eU =(new Boolean(enableupload)) ? 1 : 0;
        Integer ca = (new Boolean(category)) ? 1 : 0;
        Integer pId = new Integer(partnerid);
        KalturaConfiguration kC = kalturaService.getKalturaConfiguration(zoomAccountId);

        if(kC == null ) {

            KalturaConfiguration kalturaConfiguration = new KalturaConfiguration();
            kalturaConfiguration.setEnableUpload(eU);
            kalturaConfiguration.setCategoryByZoomRecording(ca.toString());
            kalturaConfiguration.setUserName(username);
            kalturaConfiguration.setUserId(userid);
            kalturaConfiguration.setPartnerId(pId);
            kalturaConfiguration.setAdministratorSecret(adminsecret);
            kalturaConfiguration.setZoomAccountId(zoomAccountId);

            kalturaService.saveKalturaConfiguration(kalturaConfiguration);

        } else {
            kalturaService.updateKaltura(username,userid, new Integer(partnerid), adminsecret,eU, ca.toString(), zoomAccountId);
        }
        return "success";
    }

    @RequestMapping(value = "/kaltura/fetch", method = RequestMethod.GET)
    @ResponseBody
    public KalturaConfiguration getKalturaConfiguration(@RequestParam String token ) {

        Map claims = TokenParser.verifyToken(token);
        Claim claim = (Claim)claims.get("accountId");
        String accountId = claim.asString();

        if (null == accountId) {
            throw new ValidationException("Account Id is null");
        }
        return kalturaService.getKalturaConfiguration(accountId);
    }


    @RequestMapping(value = "/kaltura/user/saveConfig", method = RequestMethod.POST)
    @ResponseBody
    public String createUserKalturaConfiguration(@RequestParam String userConfigId, @RequestParam String token) {

        Map claims = TokenParser.verifyToken(token);
        Claim claimAccount = (Claim)claims.get("accountId");
        Claim claimUser = (Claim)claims.get("userId");
        String accountId = claimAccount.asString();
        String userId = claimUser.asString();

        if (null == accountId || null == userId){
            throw new ValidationException("Invalid Token");
        }
        if (null == userConfigId) {
            throw new ValidationException("Request Parameter Null ");
        }

        UserKalturaConfig kC = kalturaService.getUserKalturaConfiguration(userId);
        if(kC == null ) {
            UserKalturaConfig userKalturaConfig = new UserKalturaConfig();

            userKalturaConfig.setUserConfigId(userConfigId);
            userKalturaConfig.setUserId(userId);
            userKalturaConfig.setAccountId(accountId);

            kalturaService.saveUserKalturaConfiguration(userKalturaConfig);

        } else {
            kalturaService.updateUserKalturaConfiguration(userId, userConfigId);

        }
        return "success";
    }

    @RequestMapping(value = "/kaltura/user/fetch", method = RequestMethod.GET)
    @ResponseBody
    public UserKalturaConfig getKalturaUserConfiguration(@RequestParam String token ) {
        Map claims = TokenParser.verifyToken(token);
        Claim claimUser = (Claim)claims.get("userId");
        String userId = claimUser.asString();
        if (null == userId){
            throw new ValidationException("Invalid Token");
        }

        return kalturaService.getUserKalturaConfiguration(userId);
    }
}
