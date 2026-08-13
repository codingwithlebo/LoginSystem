const express = require('express');
const router = express.Router();
const paymentRoutes = require('./paymentRoutes');

router.get('/health', (req, res) => res.json({ status: 'ok' }));
router.use('/payments', paymentRoutes);

module.exports = router;
