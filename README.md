# Kaltura Connector #
   
   This is a CMS connector specifically generates Metadata required for Kaltura to upload recordings into Kaltura platform. 
   

# Getting Started #
Download the project.

    Edit the application-dev.properties file :  
         Enter your App's Development Client Id, Client Secret, Database settings. 
    
    Edit the application-prod.properties file : 
          Enter your App's Production Client Id, Client Secret, Database settings. 
    
    Run CmsConnectorApplication


#Prerequisites #
     JDK 8.171

#### Check jdk version ####

    sudo java -version

#### Database ####

    MySql - run the db scripts under docs/scripts


####Framework used #####
  The project is build using SpringBoot framework. 
  Make sure all the dependencies are loaded as per pom.xml.
  
  
  
 #Example#
   ##### Sample Recording Complete Event Payload parsing and populating Kaltura MetaData ###### 

        String jsonString = "{"payload":{"account_id":"754467d1-69f2-47c8-ba05-6b1f1d8d39d8","meeting":{"duration":1,"start_time":"2018-05-07T19:01:50Z","timezone":"","share_url":"https://example.com/recording/share/examplefile","recording_files":[{"id":"9b70c332-22a3-40bc-b25d-ad26a701ad4e","meeting_id":"jP9gHqGiQGikkwT1n6PTkQ==","recording_start":"2018-05-07T18:52:56Z","recording_end":"2018-05-07T18:53:16Z","file_type":"MP4","file_size":1443512,"play_url":"https://example.com/recording/play/playfile","download_url":"https://example.com/recording/download/downloadfile","status":"completed"},{"id":"754467d1-69f2-47c8-ba05-6b1f1d8d39d8","meeting_id":"jP9gHqGiQGikkwT1n6PTkQ==","recording_start":"2018-05-07T18:52:56Z","recording_end":"2018-05-07T18:53:16Z","file_type":"M4A","file_size":137852,"play_url":"https://example.com/recording/play/recordingplayfile","download_url":"https://example.com/recording/download/downloadfile","status":"completed"},{"id":"6d4d1525-fbcf-4b57-a588-56ede2a941c1","meeting_id":"jP9gHqGiQGikkwT1n6PTkQ==","recording_start":"2018-05-07T18:52:56Z","recording_end":"2018-05-07T18:53:16Z","file_type":"","file_size":1365,"play_url":"https://example.com/recording/play/recordingplayfile","download_url":"https://example.com/recording/download/downloadfile","status":"completed"}],"total_size":1581364,"topic":"Bob Smith's Zoom Meeting","recording_count":2,"id":456584879,"uuid":"jP9gHqGiQGikkwT1n6PTkQ==","host_id":"SulPWPxdR0ayXUPes8Sd5g","host_email":"Bob.smith@zoom.com"}},"event":"recording_completed","download_token":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcHBsaWNhdGlvbiIsImF1ZCI6ImR6U0JDMFpUTXVsekptOHZ6SEFiQSIsImNsYWltIjp7ImFjY291bnRJZCI6ImR6STFNMExkUVQta1BjRER6NUszVFEiLCJ1c2VySWQiOiJTdWxQV1B4ZFIwYXlYVVBlczhTZDVnIn0sImV4cCI6MTUyNTgwNjIwMjI4NH0.-I_C5R2R1I_JjSYF7vZ9gNL2qlbAkxTZJkt4WGKZf7I"}";

        RecordingCompletePayload convertedObject = new Gson().fromJson(jsonString, RecordingCompletePayload.class);

        // Sample Kaltura Account Details 
        
        String userName = "Bob Smith"; # Your kaltura username 

        String userId = "bob.smith@zoom.us"; # Your kaltura userid

        int partnerId = samplepartnerid; # Your kaltura partnerid

        String administratorSecret = "sampleadmin"; # Your Kaltura administrator secret

        int enableUpload = 1;

        String categoryByZoomRecording = "1";
        
        // Payload Parsing and invoke kaltura upload
        
        Meeting meeting = convertedObject.getPayLoad().getMeeting();

        String hostEmail = meeting.getHostEmail();
        String hostId = meeting.getHostId();
        String topic = meeting.getTopic();
        meeting.getRecordingFiles().stream().forEach((recordingFile) -> {
            //create a thread for each file
            CompletableFuture<Void> completableFuture = new CompletableFuture<>();

            CompletableFuture.runAsync(() -> {
                int success = 0;
                try {
                    KalturaUtil.uploadToKaltura(userName, userId, partnerId, administratorSecret, categoryByZoomRecording,
                            recordingFile.getDownloadUrl(), topic, hostId, hostEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).thenApply((Void) -> {
                System.out.println("Completed" );
                return null;
            });

        });




# License #
This project is licensed under the MIT License.

