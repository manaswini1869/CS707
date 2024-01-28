DROP DATABASE IF EXISTS `707_DB`;
CREATE DATABASE `707_DB`;
USE `707_DB`;






CREATE TABLE study_data (
  video_id INT NOT NULL,
  video_name VARCHAR(150),
  blob_link VARCHAR(2083),
  PRIMARY KEY (video_id)
);


/* Dummy data */

INSERT INTO study_data (video_id, video_name, blob_link)
VALUES
(1,'Matteo Comedy', 'https://www.youtube.com/watch?v=3xsp2qz-i6c'),
(3,'Mac and Cheese', 'https://www.youtube.com/watch?v=QjAbAlLcnaQ'),
(4, 'Perfect Breakfast', 'https://www.youtube.com/watch?v=s6o8mjBkM-U');
















