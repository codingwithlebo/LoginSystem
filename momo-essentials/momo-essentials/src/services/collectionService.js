const axios = require('axios');
const { v4: uuidv4 } = require('uuid');
const momoConfig = require('../config/momoConfig');
const { getAccessToken } = require('./momoAuthService');

// Request-to-pay: prompts the payer's MoMo app for approval.
async function requestToPay({ amount, currency, payerPhone, payerMessage, payeeNote }) {
  const referenceId = uuidv4();
  const token = await getAccessToken();

  await axios.post(
    `${momoConfig.baseUrl}/collection/v1_0/requesttopay`,
    {
      amount: String(amount),
      currency,
      externalId: referenceId,
      payer: { partyIdType: 'MSISDN', partyId: payerPhone },
      payerMessage,
      payeeNote,
    },
    {
      headers: {
        Authorization: `Bearer ${token}`,
        'X-Reference-Id': referenceId,
        'X-Target-Environment': momoConfig.targetEnvironment,
        'Ocp-Apim-Subscription-Key': momoConfig.subscriptionKey,
        'Content-Type': 'application/json',
      },
    }
  );

  return { referenceId };
}

// Checks the status of a previously created request-to-pay transaction.
async function getTransactionStatus(referenceId) {
  const token = await getAccessToken();

  const response = await axios.get(
    `${momoConfig.baseUrl}/collection/v1_0/requesttopay/${referenceId}`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        'X-Target-Environment': momoConfig.targetEnvironment,
        'Ocp-Apim-Subscription-Key': momoConfig.subscriptionKey,
      },
    }
  );

  return response.data;
}

module.exports = { requestToPay, getTransactionStatus };
