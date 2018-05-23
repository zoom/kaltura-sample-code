/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.KalturaServiceTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.zoom.cms.domain.KalturaConfiguration;
import us.zoom.cms.service.KalturaService;

/**
 * Created by kavithakannan on 2/22/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class KalturaServiceTests {
    @Autowired
    KalturaService kalturaService;

    @Test
    public void createKalturaConfiguration() {
        KalturaConfiguration kalturaConfiguration = new KalturaConfiguration();

        kalturaConfiguration.setUserId("12345");
        kalturaConfiguration.setUserName("Kavitha KK");
        kalturaConfiguration.setZoomAccountId("Zoom12345");
        kalturaConfiguration.setAdministratorSecret("secret");
        kalturaConfiguration.setCategoryByZoomRecording("1");
        kalturaConfiguration.setEnableUpload(1);
        kalturaConfiguration.setPartnerId(1231123);

        try {
            int success = kalturaService.saveKalturaConfiguration(kalturaConfiguration);
            Assert.assertEquals(1, success);
        } catch(Exception ex){
            ex.printStackTrace();
        }




    }

    @Test
    public void updatePanoptoConfiguration() {
        String userName = "AJKMHJ";
        String userId = "12345634";
        String accountId = "Zoom12345";
        int partnerId = 3423;
        String zoomAccountId = "Zoom12345";
        int enableUpload = 1;
        String adminSecret = "sfsdfsdf";
        String category = "1";


        try {
            int success = kalturaService.updateKaltura(userName, userId, partnerId, adminSecret, enableUpload, category, zoomAccountId);
            Assert.assertEquals(1, success);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void fetchKalturaConfig() {
        String zoomAccountId = "Zoom12345";


        try {
            KalturaConfiguration kal = kalturaService.getKalturaConfiguration(zoomAccountId);
            Assert.assertNotNull(kal);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
