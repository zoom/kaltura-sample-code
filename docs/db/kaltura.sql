--
-- Table structure
--


-- User level Configuration for Kaltura ----

CREATE TABLE `zm_kaltura_user_configuration` (
  `userid` varchar(36) NOT NULL,
  `accountid` varchar(45) NOT NULL,
  `userconfigid` varchar(45) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,  PRIMARY KEY (`userid`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Account level Configuration for Kaltura ----

CREATE TABLE `zm_kaltura_configuration` (
  `zoomaccountid` varchar(36) NOT NULL,
  `userid` varchar(45) NOT NULL,
  `partnerid` int(11) NOT NULL,
  `administratorsecret` varchar(100) NOT NULL,
  `enableuploadrecording` tinyint(1) DEFAULT NULL,
  `categorybyzoomrecording` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
   PRIMARY KEY (`zoomaccountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
