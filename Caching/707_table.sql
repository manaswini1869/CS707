DROP DATABASE IF EXISTS `707_DB`;
CREATE DATABASE `707_DB`;
USE `707_DB`;






CREATE TABLE study_data (
  video_id INT NOT NULL,
  video_name VARCHAR(150),
  blob_link VARCHAR(2083),
  creation_time TIMESTAMP DEFAULT CURRENT_TIME,
  PRIMARY KEY (video_id)
);

















