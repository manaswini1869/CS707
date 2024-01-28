var fs = require('fs');
var crypto = require('crypto');
const prompt = require('prompt-sync')();

const name = prompt('File Name: ');
var key = '14189dc35ae35e75ff31d7502e245cd9bc7803838fbfd5c773cdcd79b8a28bbd';
var cipher = crypto.createCipher('aes-256-cbc', key);
var input = fs.createReadStream(`${name}.mp4`);
var output = fs.createWriteStream(`enc_${name}.mp4`);

input.pipe(cipher).pipe(output);

output.on('finish', function() {
  console.log('Encrypted file written to disk!');
});