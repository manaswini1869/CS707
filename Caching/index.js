var mysql = require('mysql');
var express = require('express');
var uuid = require('nodejs-unique-numeric-id-generator');
var bodyParser = require("body-parser");
var redis = require('redis');

// Connecting to mysql

var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Jace@143',
    database: '707_db'

})

var app = express();
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

connection.connect(function (error) {
    if (error) {
        console.error('error connecting :' + error.stack);
        return;
    }
    console.log('Connected as id :' + connection.threadId);
});

connection.query(`select * from study_data`, (err, result, fields) => {
    if (err) {
        return console.log(err);
    }
    return console.log(result);
})

// add video
app.post('/videos/add/', (req, res, next) => {
    var post_data = req.body;
    console.log(post_data);

    var video_id = uuid.generate(new Date().toJSON());
    var video_name = post_data.video_name;
    var blob_link = post_data.blob_link;
    var video_size = post_data.video_size;
    var creation_time = new Date();
    console.log("Printing data", video_id, video_name, blob_link, video_size);

    var query = 'INSERT INTO `study_data` (`video_id`, `video_name`, `blob_link`, `video_size`, `creation_time`) VALUES (?, ?, ?, ?,?)';
    var values = [video_id, video_name, blob_link, video_size, creation_time];

    connection.query(query, values, function (err, result) {
        if (err) {
            console.log('[MySQL ERROR]', err);
            return;
        }
        else {
            res.json('Video Added');
            return;
        }
    });

});

// get video from database by id

app.get('/videos/:video_id', function (req, res) {
    var video_id = req.params.video_id;
    var query = 'SELECT * FROM study_data WHERE video_id=?';
    connection.query(query, [video_id], function (err, results) {
        if (!err)
            res.send(JSON.stringify(results));
        else
            console.log("Error Selecting : %s ", err);
    })
})

// get videos from database based on the creation_date, creation timezone is different to sql  timezone  so we need to convert it 

app.get('/videos/caching/:creation_date', function (req, res) {
    //var date = new Date(parseInt(req.params.creation_date)).toISOString()
    console.log("Function called??")
    var creation_date = req.params.creation_date;
    var query = "SELECT * FROM study_data WHERE DATE(creation_time) LIKE ?"
    connection.query(query, [creation_date], function (err, results) {
        if (!err)
            res.send(JSON.parse(JSON.stringify(results)));
        else
            console.log("Error Selecting : %s ", err);
    })
});


// Delete a Video from the Database

app.delete("/videos/delete/:video_id", function (req, res) {
    var video_id = req.params.video_id;
    var query = "DELETE FROM study_data WHERE video_id = ?";
    connection.query(query, [video_id], function (err, results) {
        if (!err) {
            res.send({ message: 'Deleted Successfully' });
        } else {
            console.log(err);
        }
    });
});

app.listen(3000, () => console.log("Server is running on port 3000"));
