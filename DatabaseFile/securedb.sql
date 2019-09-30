-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 30, 2019 at 06:57 PM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `securedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `client_details`
--

DROP TABLE IF EXISTS `client_details`;
CREATE TABLE IF NOT EXISTS `client_details` (
  `CD_Customer_Number` int(255) NOT NULL AUTO_INCREMENT,
  `CD_SSN_Number` varchar(255) NOT NULL,
  `CD_Password` varchar(255) NOT NULL,
  `CD_Forename` varchar(255) NOT NULL,
  `CD_Surname` varchar(255) NOT NULL,
  `CD_Date_Of_Birth` varchar(255) NOT NULL,
  `CD_Address` varchar(255) NOT NULL,
  `CD_Post_Code` varchar(255) NOT NULL,
  `CD_Phone_Number` varchar(255) NOT NULL,
  `CD_Email_Address` varchar(255) NOT NULL,
  `TS_ID` int(255) DEFAULT NULL,
  PRIMARY KEY (`CD_Customer_Number`),
  KEY `TS_ID` (`TS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_details`
--

INSERT INTO `client_details` (`CD_Customer_Number`, `CD_SSN_Number`, `CD_Password`, `CD_Forename`, `CD_Surname`, `CD_Date_Of_Birth`, `CD_Address`, `CD_Post_Code`, `CD_Phone_Number`, `CD_Email_Address`, `TS_ID`) VALUES
(1, '1', '$2y$10$.1xNanbWcFM7oiGugMqeoukPrMzPhigbFmjie13R/gBLFflyHMGxq', 'John', 'Doe', '24/05/1990', '55 Lane Avenue', 'CO46SQ', '074801479', 'mail@mail.com', NULL),
(41, '2', '$2y$10$KGapJLs8krolcmK.P63th.uN5LmSi/K4ItuVr75CWInBb5boggfiW', 'Jane', 'Doe', '23/07/1990', '72 Does Lane', 'CO42SQ', '587345983', 'mail@mail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `contact_account`
--

DROP TABLE IF EXISTS `contact_account`;
CREATE TABLE IF NOT EXISTS `contact_account` (
  `CD_Customer_Number` int(255) NOT NULL,
  `CA_Account_Owner` varchar(255) NOT NULL,
  `CA_Contact_Reference` varchar(255) NOT NULL,
  `CF_Account_Number` int(255) NOT NULL,
  `CF_Sort_Code` varchar(255) NOT NULL,
  `CA_Account_Name` varchar(255) NOT NULL,
  `CA_Account_Balance` int(255) NOT NULL,
  `CA_ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`CA_ID`),
  KEY `CD_Customer_Number` (`CD_Customer_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contact_account`
--

INSERT INTO `contact_account` (`CD_Customer_Number`, `CA_Account_Owner`, `CA_Contact_Reference`, `CF_Account_Number`, `CF_Sort_Code`, `CA_Account_Name`, `CA_Account_Balance`, `CA_ID`) VALUES
(1, 'John Doe', '1', 766540, '1', 'Savings Account A', 666284, 7),
(41, 'Jane Doe', '57e5c31', 644646, '78-53-31', 'Savings Account A', 850114, 8);

-- --------------------------------------------------------

--
-- Table structure for table `time_stamp`
--

DROP TABLE IF EXISTS `time_stamp`;
CREATE TABLE IF NOT EXISTS `time_stamp` (
  `TS_ID` int(255) NOT NULL AUTO_INCREMENT,
  `TS_Current_Logon_Time` varchar(255) DEFAULT NULL,
  `TS_Last_Logon` varchar(255) DEFAULT NULL,
  `TS_Last_Unsuccessful_Logon` varchar(255) DEFAULT NULL,
  `CD_Customer_Number` int(255) NOT NULL,
  PRIMARY KEY (`TS_ID`),
  UNIQUE KEY `CD_Customer_Number` (`CD_Customer_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `time_stamp`
--

INSERT INTO `time_stamp` (`TS_ID`, `TS_Current_Logon_Time`, `TS_Last_Logon`, `TS_Last_Unsuccessful_Logon`, `CD_Customer_Number`) VALUES
(7, '16-09-2019 06:30:13', '16-09-2019 05:59:49', '22-09-2019 16:49:29', 1),
(8, '16-09-2019 06:47:52', '16-09-2019 06:45:54', '16-09-2019 06:45:39', 41);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `contact_account`
--
ALTER TABLE `contact_account`
  ADD CONSTRAINT `contact_account_ibfk_1` FOREIGN KEY (`CD_Customer_Number`) REFERENCES `client_details` (`CD_Customer_Number`);

--
-- Constraints for table `time_stamp`
--
ALTER TABLE `time_stamp`
  ADD CONSTRAINT `time_stamp_ibfk_1` FOREIGN KEY (`CD_Customer_Number`) REFERENCES `client_details` (`CD_Customer_Number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
