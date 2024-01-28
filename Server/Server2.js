const Fs = require('fs')
const Path = require('path')
const Axios = require('axios')
const sql = require('mssql')
const http = require('http')
const util = require('util')
const express = require('express')
var request = require('request')
var progress = require('request-progress')
const URL = require('url').URL;

var app = express();

app.get('/:id', function (req, res) {
    var exists = Fs.existsSync('./' + req.params.id + '.mp4')
    if (!exists) {
        console.log('File Does not Exist')
    }
    else {
        console.log('Sending Chunks');
        res.sendFile(Path.resolve(__dirname, req.params.id + '.mp4'))
    }
})

app.listen(5000, async function() {
    console.log("Listening");
})
