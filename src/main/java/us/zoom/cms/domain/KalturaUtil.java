/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.domain;

import com.kaltura.client.KalturaClient;
import com.kaltura.client.KalturaConfiguration;
import com.kaltura.client.enums.KalturaBulkUploadType;
import com.kaltura.client.enums.KalturaSessionType;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.zoom.cms.service.KalturaService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class KalturaUtil {

	static final Logger logger = LoggerFactory.getLogger(KalturaUtil.class);

	@Autowired
	KalturaService autoWiredKalturaService;


	private static KalturaService kalturaService;

	private static String XML_URL_ATTRIBUTE_PATH = "//@url";
	
	private static String XML_USERID_PATH = "//userId";
	
	private static String XML_NAME_PATH = "//name";

	private static String XML_NAME_CATEGORY = "//category";

	private static String XML_NAME_TAG = "//tag";

	public final static String KALTURA_ENDPOINT = "http://www.kaltura.com/";
	
	private static int CONVERSION_PROFILE_ID = -1;
	
	private static String TEMPLATE_XML_PATH = "/kaltura/bulkUploadXml.xml";
	
	private static String TEMPLATE_XML_FILEDATANAME = "bulkUploadXml.xml";


	@Autowired
	public KalturaUtil(KalturaService kS) {
		KalturaUtil.kalturaService  = kS;
	}



	public static void uploadToKaltura(String userName, String userId, int partnerId, String adminSecret, String categoryByZoomRecording, String recordUrl, String fileName, String hostId, String hostEmail) throws Exception {
		System.out.println("In upload Kaltura");

		KalturaConfiguration config = new KalturaConfiguration();
		config.setEndpoint(KALTURA_ENDPOINT);
		config.setTimeout(10*1000);
		KalturaClient client = null;

		try {
			 client = new KalturaClient(config);
		} catch (Exception e){
			logger.error("Unable to get KalturaClient");
			e.printStackTrace();
		    return;
		}

		String ks = client.generateSession(adminSecret, userId, KalturaSessionType.USER, partnerId);
		client.setSessionId(ks);

		InputStream csvFileData = getRecordInputStream(recordUrl, userId, fileName, hostId, categoryByZoomRecording, hostEmail);
		long csvFileDataSize = csvFileData.available();
		
		client.getBulkUploadService().add(CONVERSION_PROFILE_ID,
				csvFileData, TEMPLATE_XML_FILEDATANAME, csvFileDataSize,
				KalturaBulkUploadType.XML, userName, fileName);


	}


	
	private static InputStream getRecordInputStream(String recordUrl, String userId, String fileName, String hostId, String categoryByZoomRecording, String hostEmail) {
		InputStream in = null;
		try {
			InputStreamReader isReader = new InputStreamReader(KalturaUtil.class.getResourceAsStream(TEMPLATE_XML_PATH),"UTF-8");
			SAXReader reader= new SAXReader();
			Document doc= reader.read(isReader);
			//update record url attribute
			updateUrlAttribute(doc, recordUrl, userId, fileName, hostId, categoryByZoomRecording, hostEmail);
			isReader.close();
			logger.info("update xml doc:" + doc.asXML());
			in = new ByteArrayInputStream(doc.asXML().getBytes("utf-8"));
			System.out.println("End Record Input Stream");
		} catch (Exception e) {
			logger.error("Kaltura upadte record xml fail ", e);
		} 
		return in;
	}
	
	@SuppressWarnings("unchecked")
	private static void updateUrlAttribute(Document doc, String recordUrl, String userId, String fileName, String hostId, String categoryByZoomRecording, String hostEmail) {



		UserKalturaConfig uC = kalturaService.getUserKalturaConfiguration(hostId);
		if(uC != null) {
			logger.debug("updateUrlAttribute host id " + hostId);
			String kalturaUserId = uC.getUserConfigId();
			logger.info("updateUrlAttribute set user id {}", kalturaUserId);
			if(StringUtils.isNotEmpty(kalturaUserId)){
				userId = kalturaUserId;
			}
		}

		List<Element> urlContentResource = doc.selectNodes(XML_URL_ATTRIBUTE_PATH);
		if(urlContentResource.size() != 0)  {
			Attribute urlAttr = (Attribute) urlContentResource.get(0);
			urlAttr.setValue(recordUrl);
		}
		
		List<Element> userIdElementList = doc.selectNodes(XML_USERID_PATH);
		if(userIdElementList.size() != 0)  {
			Element userIdElement = userIdElementList.get(0);
			userIdElement.setText(userId);
		}
		
		List<Element> nameElementList = doc.selectNodes(XML_NAME_PATH);
		if(nameElementList.size() != 0)  {
			Element nameElement = nameElementList.get(0);
			nameElement.setText(fileName);
		}

		List<Element> categoryElementList = doc.selectNodes(XML_NAME_CATEGORY);
		if(categoryElementList.size() != 0)  {
			logger.debug("categoryByZoomRecording is " + categoryByZoomRecording);
			if("1".equals(categoryByZoomRecording)) {
				Element nameElement = categoryElementList.get(0);
				if (uC != null && StringUtils.isNotEmpty(hostEmail)) {
					nameElement.setText(hostEmail);
				} else {
					nameElement.setText(userId);
				}
			}else{
				Element nameElement = categoryElementList.get(0);
				nameElement.setText("Zoom Recordings");
			}
		}

		List<Element> tagElementList = doc.selectNodes(XML_NAME_TAG);
		if(tagElementList.size() != 0)  {
			Element nameElement = tagElementList.get(0);
			String kalturaUserId = null;
			if (uC != null) {
				kalturaUserId = uC.getUserConfigId();
			}
			logger.info("set tag  {}", kalturaUserId);
			if(StringUtils.isNotEmpty(kalturaUserId)){
				nameElement.setText(kalturaUserId);
			}else {
				if (uC != null && StringUtils.isNotEmpty(hostEmail)) {
					nameElement.setText(hostEmail);
				} else {
					nameElement.setText(userId);
				}
			}
		}
	}
}

