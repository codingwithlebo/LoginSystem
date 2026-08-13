# momo-essentials

MoMo Mini App Hackathon 2026 — backend

## Structure

```
src/
  config/       # env-based config (MoMo credentials, etc.)
  routes/       # Express route definitions
  controllers/  # request handling, validation, response shaping
  services/     # MoMo API calls (auth, collections, disbursements)
  middleware/   # error handling, auth guards, logging
  models/       # data models (if a DB is added)
  utils/        # helpers
  app.js        # Express app setup
  server.js     # entry point
tests/
```

## Setup

1. `npm install`
2. Copy `.env.example` to `.env` and fill in your MTN MoMo Developer sandbox credentials from https://momodeveloper.mtn.com
3. `npm run dev`

## Endpoints

- `GET /api/health` — health check
- `POST /api/payments/pay` — initiate a request-to-pay (`{ amount, currency, payerPhone, payerMessage, payeeNote }`)
- `GET /api/payments/pay/:referenceId` — check transaction status
