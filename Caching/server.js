const express = require('express');
const redis = require('redis');
const { createClient } = require('redis');
const axios = require('axios');
const moment = require('moment-timezone');

const app = express();
const port = process.env.PORT || 8080;

let redisClient;

// Creating the new redis client to store the caching data into
(async () => {
    redisClient = createClient();

    redisClient.on("error", (error) => console.error(`Error : ${error}`));

    await redisClient.connect();
    try {
        await initialDataInCache();
        console.log('Initial data cached successfully.');
    } catch (error) {
        console.error('Error caching initial data:', error);
    }

})();

// Fetching from database if not in cache
async function fetchApiData(video_id) {
    const apiResponse = await axios.get(
        `http://localhost:3000/videos/${video_id}`
    );
    console.log(apiResponse.data);
    console.log("Request sent to the API");
    return apiResponse.data;
}

// Initial fetch of videos that are 24 hours old
async function initialDataInCache() {
    let oneDayAgoDate = moment().tz('America/Chicago');
    //console.log(oneDayAgoDate.format('YYYY-MM-DD'));
    // Getting all video ids with a date older than 24hours
    const apiResponse = await axios.get(
        `http://localhost:3000/videos/caching/${oneDayAgoDate.format('YYYY-MM-DD')}`
    );
    console.log(apiResponse.data);
    for (let i = 0; i < apiResponse.data.length; i++) {
        console.log(apiResponse.data[i].video_id, apiResponse.data[i]);
        const key = toString(apiResponse.data[i].video_id);
        console.log(typeof(key), typeof(JSON.stringify(JSON.stringify(apiResponse.data[i]))));
        await redisClient.set(key, JSON.stringify(apiResponse.data[i]), { 'EX': 60 });
    };
}

async function getVideoData(req, res) {
    const video_id = req.params.video_id; //Should be replaced by video id
    console.log(video_id)
    let results;
    let isCached = false;

    try {
        // Checking cache for each video_id and collect results
        const cacheResults = await redisClient.get(video_id); // Should get the video id from the result 
        if (cacheResults) {
            isCached = true;
            results = JSON.parse(cacheResults);
        } else {
            results = await fetchApiData(video_id);
            if (results.length === 0) {
                throw "API returned an empty array";
            }
            console.log(typeof(video_id), typeof(JSON.stringify(results)));
            await redisClient.set(video_id, JSON.stringify(results), { 'EX': 60 }); // Testing the data to expire after 1 minute, Key, value, expiration time, key value will be changed to accomodate video id
        }

        res.send({
            fromCache: isCached,
            data: results,
        });
    } catch (error) {
        console.error(error);
        res.status(404).send("Data unavailable");
    }
}

app.get("/videos/:video_id", getVideoData);

app.listen(port, () => {
    console.log(`App listening on port ${port}`);
});