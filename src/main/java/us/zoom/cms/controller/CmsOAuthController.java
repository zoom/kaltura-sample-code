/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import us.zoom.cms.domain.*;
import us.zoom.cms.exception.BadServiceException;
import us.zoom.cms.jwt.TokenGenerator;
import us.zoom.cms.service.KalturaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by kavithakannan on 1/22/18.
 */
@Controller
public class CmsOAuthController {
    protected final static Logger logger = LoggerFactory.getLogger(CmsOAuthController.class);


    @Autowired
    KalturaService kalturaService;

    @Value("${kaltura.clientid}")
    String clientId;

    @Value("${kaltura.clientsecret}")
    String clientSecret;

    @Value("${kaltura.baseurl}")
    String baseUrl;

    @Value("${kaltura.redirecturl}")
    String redirectUrl;

    @Value("${kaltura.base.domain}")
    String baseDomain;


    @Value("${kaltura.verificationtoken}")
    String verificationToken;


    @RequestMapping(value = "/kaltura/recordingcompleted", method = RequestMethod.POST)
    @ResponseBody
        public void onRecordingCompleted(@RequestBody String jsonPayLoad, @RequestHeader(value = "authorization") String header) {
        logger.debug("Received PAY LOAD ");
        String jsonString = jsonPayLoad;



        logger.debug("Received Json Payload"+ jsonPayLoad);

        RecordingCompletePayload convertedObject = new Gson().fromJson(jsonString, RecordingCompletePayload.class);

        logger.debug("Converted Json Payload to Object ");

        String accountId = convertedObject.getPayLoad().getAccountId();
        String recordingToken = convertedObject.getRecordingToken();


        logger.debug("Retrieved Account Id"+ accountId);
        if (null != header && !header.equals(verificationToken)) {
            logger.debug("Invalid Verification Token");
            return;
        }

        KalturaConfiguration kC = kalturaService.getKalturaConfiguration(accountId);

        if (null == kC) {
            logger.debug("No Kaltura Configuration configured by the Admin " );
            return;
        }

        if(kC.getEnableUpload() != 1){
            logger.debug("Enable upload not set " );
            return;
        }

        Meeting meeting = convertedObject.getPayLoad().getMeeting();

        String hostEmail = meeting.getHostEmail();
        String hostId = meeting.getHostId();
        String topic = meeting.getTopic();
        meeting.getRecordingFiles().stream().forEach((recordingFile) -> {
            //create a thread for each file
            CompletableFuture<Void> completableFuture = new CompletableFuture<>();

            CompletableFuture.runAsync(() -> {
                int success = 0;
                String downloadUrl = recordingFile.getDownloadUrl();
                if (convertedObject.getRecordingToken() != null) {
                    downloadUrl = downloadUrl + "?access_token=" + convertedObject.getRecordingToken();
                }
                try {

                    KalturaUtil.uploadToKaltura(kC.getUserName(), kC.getUserId(),
                            kC.getPartnerId(), kC.getAdministratorSecret(), kC.getCategoryByZoomRecording(), downloadUrl
                           , topic, hostId, hostEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).thenApply((Void) -> {
                logger.debug("Completed " );
                return null;
            });

        });

    }

    @RequestMapping(value = "/kaltura/oauthtoken", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView parseToken(@RequestParam String code, ModelMap modelMap) throws IOException {
        //  String json = jsonPayLoad;

        //current user : https://api.zoom.us/v2/users/me  https://dev.zoom.us/v2/users/me?access_token=token

        logger.debug("Received Code "+code);
        // Create an instance of HttpClient.
        //String url = "https://dev.zoom.us/controller/token";
        HttpClient client = HttpClientBuilder.create().build();
        String accessToken = "";

        try {
            accessToken = fetchToken(client, code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadServiceException(HttpStatus.BAD_REQUEST, "Unable to get access token.");
        }

        //////////Get User Information //////////

        UserInfo userInfo = null;
        try {
            userInfo = fetchUserInformation(client, accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadServiceException(HttpStatus.BAD_REQUEST, "Unable to get User Info ");

        }

        /////////////Get User permissions//////////////
        boolean bln = false;
        try {
            bln = fetchPermissions(client, accessToken, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadServiceException(HttpStatus.BAD_REQUEST, "Unable to get User Permissions.");
        }

        String token = TokenGenerator
                .generateJWT(userInfo.getFirstName()+" " +userInfo.getLastName(), userInfo.getId(), userInfo.getAccountId(), bln);
        logger.debug("JWT token is "+ token);
        logger.debug("Has Permissions "+ bln);

        modelMap.addAttribute("userName", userInfo.getFirstName()+" " +userInfo.getLastName());

        modelMap.addAttribute("token", token);
        modelMap.addAttribute("isAdmin", bln);
        return new ModelAndView("welcome", modelMap);
    }



    private String fetchToken(HttpClient client, String code) throws Exception{

        ////////// Get Token //////////
        //rSi2pnSERWuXZCi73vN5ag:CxxavF2h7KKPYG4hAOOeGbZCTkqy0juH
        String encoding = Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes());
        HttpPost httppost = new HttpPost(baseUrl+"/oauth/token");
        httppost.setHeader("Authorization", "Basic " + encoding);



        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUrl));
        httppost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));


        HttpResponse response = client.execute(httppost);
        HttpEntity entity = response.getEntity();

        logger.debug("Response of getting code "+ response);

        logger.debug("Content "+ entity.getContent());

        InputStream instream = entity.getContent();
        String result = convertStreamToString(instream);
        // now you have the string representation of the HTML request
        logger.debug("RESPONSE: " + result);

        JsonObject address = new JsonParser()
                .parse(result).getAsJsonObject();

        logger.debug("Token Parsed "+ address);

        String accessToken = address.get("access_token").getAsString();

        logger.debug(""+accessToken);

        return accessToken;
    }

    private UserInfo fetchUserInformation(HttpClient client, String accessToken) throws  Exception{
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(baseDomain).setPath("/v2/users/me")
                .setParameter("access_token", accessToken);

        // Open API
        // recording:read recording:Edit permissions set up panopto configuration
        // Based on permission i need to show if user level configuratin or Account level configuraiton


        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpget = new HttpGet(uri);

        HttpResponse responseGet = client.execute(httpget);

        InputStream instream = responseGet.getEntity().getContent();

        String result = convertStreamToString(instream);

        UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);

        logger.debug("Users Info "+ result);

        return userInfo;
    }

    private boolean fetchPermissions(HttpClient client, String accessToken, UserInfo userInfo) throws Exception{
        URIBuilder builder = new URIBuilder();

        builder.setScheme("http").setHost(baseDomain).setPath("/v2/users/me/permissions")
                .setParameter("access_token", accessToken);
        URI uri = null;

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet patch = new HttpGet(uri);

        HttpResponse patchResponse = client.execute(patch);

        InputStream instream = patchResponse.getEntity().getContent();

        String result = convertStreamToString(instream);

        logger.debug("Permission result "+ result);


        UserPermissions userPermissions = new Gson().fromJson(result, UserPermissions.class);

        logger.debug("User Permissions "+ userPermissions.toString());

        boolean bln = canConfigureEventSubscription(userPermissions);
        logger.debug("Can Configure Event Subscription "+ bln);
        return bln;

    }

    private boolean canConfigureEventSubscription(UserPermissions userPermissions){

        if (userPermissions.getPermissions().contains("Recording:Read") && userPermissions.getPermissions().contains("Recording:Edit")) {
            return true;
        }

        return false;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    @RequestMapping("/")
    public String welcome(@RequestParam(value="user_id", required=false)
                                      String userId, Map<String, Object> model, HttpServletRequest request,
                          HttpServletResponse response) {
        model.put("message", "Welcome to Zoom Kaltura Connector ");
        model.put("userId", userId);


        String iRedirectUrl = baseUrl+"/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUrl;
        if (userId != null) {
            try {
                response.sendRedirect(iRedirectUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        } else {
            return "welcome";
        }


    }



}