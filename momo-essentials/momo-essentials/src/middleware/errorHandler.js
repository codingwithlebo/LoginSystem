// Central error handler. MoMo API errors from axios carry response.data with details.
function errorHandler(err, req, res, next) {
  console.error(err.response?.data || err.message);

  const status = err.response?.status || 500;
  const message = err.response?.data?.message || 'Something went wrong';

  res.status(status).json({ error: message });
}

module.exports = errorHandler;
