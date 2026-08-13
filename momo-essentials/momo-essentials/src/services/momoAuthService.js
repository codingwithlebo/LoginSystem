const axios = require('axios');
const momoConfig = require('../config/momoConfig');

// Fetches a short-lived access token from the MTN MoMo Collections API.
// Docs: https://momodeveloper.mtn.com -> Collections -> Create access token
async function getAccessToken() {
  const credentials = Buffer.from(
    `${momoConfig.apiUser}:${momoConfig.apiKey}`
  ).toString('base64');

  const response = await axios.post(
    `${momoConfig.baseUrl}/collection/token/`,
    {},
    {
      headers: {
        Authorization: `Basic ${credentials}`,
        'Ocp-Apim-Subscription-Key': momoConfig.subscriptionKey,
      },
    }
  );

  return response.data.access_token;
}

module.exports = { getAccessToken };
