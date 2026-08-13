const collectionService = require('../services/collectionService');

async function initiatePayment(req, res, next) {
  try {
    const { amount, currency, payerPhone, payerMessage, payeeNote } = req.body;

    if (!amount || !payerPhone) {
      return res.status(400).json({ error: 'amount and payerPhone are required' });
    }

    const result = await collectionService.requestToPay({
      amount,
      currency: currency || 'EUR', // sandbox uses EUR by default
      payerPhone,
      payerMessage: payerMessage || 'Payment via MoMo Mini App',
      payeeNote: payeeNote || 'Thank you',
    });

    res.status(202).json({
      message: 'Payment request sent to payer for approval',
      referenceId: result.referenceId,
    });
  } catch (err) {
    next(err);
  }
}

async function checkPaymentStatus(req, res, next) {
  try {
    const { referenceId } = req.params;
    const status = await collectionService.getTransactionStatus(referenceId);
    res.status(200).json(status);
  } catch (err) {
    next(err);
  }
}

module.exports = { initiatePayment, checkPaymentStatus };
