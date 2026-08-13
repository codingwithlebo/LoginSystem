require('dotenv').config();

module.exports = {
  baseUrl: process.env.MOMO_BASE_URL,
  subscriptionKey: process.env.MOMO_COLLECTION_SUBSCRIPTION_KEY,
  apiUser: process.env.MOMO_COLLECTION_API_USER,
  apiKey: process.env.MOMO_COLLECTION_API_KEY,
  targetEnvironment: process.env.MOMO_TARGET_ENVIRONMENT || 'sandbox',
  callbackHost: process.env.MOMO_CALLBACK_HOST,
};
