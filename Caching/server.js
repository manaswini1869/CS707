const express = require('express');
const redis = require('redis');
const { createClient } = require('redis');
const axios = require('axios');

const app = express();
const port = process.env.PORT || 3000;

let redisClient;

// Creating the new redis client to store the caching data into
(async () => {
  redisClient = createClient();

  redisClient.on("error", (error) => console.error(`Error : ${error}`));

  await redisClient.connect();
  redisClient.flushAll('ASYNC', () => console.log('Database flushed'));
})();

async function fetchApiData() {
    const apiResponse = await axios.get(
      `https://jsonplaceholder.typicode.com/users`
    );
    // const apiResponse = await axios.get(
    //     ``       To be replaced by the endpoint from rama's API 
    //   );
    console.log(apiResponse.data); // Data should be parsed based on the response from rama
    console.log("Request sent to the API");
    return apiResponse.data;
  }
  
async function getVideoData(req, res) {
    const users = req.params.users; //Should be replaced by video id
    console.log(users)
    let results;
    let isCached = false;
    
    try {
        // Checking cache for each userID and collect results
        const cacheResults = await redisClient.get(users); // Should get the video id from the result 
        if (cacheResults) {
            isCached = true;
            results = JSON.parse(cacheResults);
        } else {
            results = await fetchApiData();
        if (results.length === 0) {
            throw "API returned an empty array";
        }
            await redisClient.set(users, JSON.stringify(results), {'EX': 60}); // Testing the data to expire after 1 minute, Key, value, expiration time, key value will be changed to accomodate video id
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
    
app.get("/users/:users", getVideoData);

app.listen(port, () => {
  console.log(`App listening on port ${port}`);
});