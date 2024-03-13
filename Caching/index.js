var mysql = require('mysql');
var express = require('express');
var uuid = require('uuid');
var  bodyParser = require("body-parser");
var redis = require('redis');

// const { createClient } = require('redis');
// const app = express();
// const port = process.env.PORT || 3000;






//Connecting to mysql




// var connection = mysql.createConnection({
//     host: 'localhost',
//     user: 'root',
//     password: '',
//     database: '707_db'

// })

// var app = express();
// app.use(bodyParser.json()); // support json encoded bodies
// app.use(bodyParser.urlencoded({ extended: true }));

// connection.connect(function (error) {
//     if (error){
//       console.error('error connecting :' + error.stack);
//       return;
//     }
//     console.log('Connected as id :'+ connection.threadId);
//   });

//   connection.query(`select * from study_data`, (err, result, fields)=>{
//     if(err){
//         return console.log(err);
//     }
//     return console.log(result);
// })

// app.post('/add/', (req, res, next)=> {
//     var post_data = req.body;
//     console.log(post_data);

//     var video_id = generateRandomId();
//     var video_name = post_data.video_name;
//     var blob_link = post_data.blob_link;
//     var video_size = post_data.video_size;
//     console.log("Printing data", video_id, video_name, blob_link, video_size);

//     connection.query('SELECT * FROM study_data WHERE video_id = ?', [video_id], function(err, result, fields) {
//         if (err) {
//             console.log('[MySQL ERROR]', err);
//             return;
//         }
    
//         // If a record with the generated video_id exists, generate a new one recursively
//         if (result && result.length) {
//             console.log('Generated video_id already exists, generating a new one...');
//             video_id = generateRandomId(); // Generate a new random video_id
//             // Recursively call the query to check the new video_id
//             connection.query('SELECT * FROM study_data WHERE video_id = ?', [video_id], function(err, result, fields) {
//                 if (err) {
//                     console.log('[MySQL ERROR]', err);
//                     return;
//                 }
//                 if (result && result.length) {
//                     console.log('Newly generated video_id already exists, retrying...');
//                     // If the new video_id still exists, recursively call the function again
//                     return generateUniqueVideoId();
//                 } else {
//                     // If the new video_id does not exist, proceed with the insertion
//                     insertVideo();
//                 }
//             });
//         } else {
//             // If the generated video_id does not exist, proceed with the insertion
//             insertVideo();
//         }
//     });

//     function insertVideo() {
//         var query = 'INSERT INTO `study_data` (`video_id`, `video_name`, `blob_link`, `video_size`) VALUES (?, ?, ?, ?)';
//         var values = [video_id, video_name, blob_link, video_size];
    
//         connection.query(query, values, function(err, result) {
//             if (err) {
//                 console.log('[MySQL ERROR]', err);
//                 return;
//             }
//             else{
//                 res.json('Video Added');
//                 return;
//             }
//         });
//     }
    
//     function generateRandomId() {
//         return Math.floor(Math.random() * 1000000); // Adjust the range as needed
//     }
// });
