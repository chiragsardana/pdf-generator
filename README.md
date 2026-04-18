# 📄 Invoice PDF Generator API (A5 Support)

This API generates a **PDF invoice** from a JSON request.
It supports **A5 page size**, multi-page splitting, GST handling, and dynamic item rendering.

---

## 🚀 Endpoint

```
POST http://localhost:8080/api/invoice/generate
```

* **Method:** POST
* **Content-Type:** `application/json`
* **Response:** `application/pdf` (binary PDF file)

---

## 📦 Request Body (Sample)

> ⚠️ Note: Comments (`//`) are for explanation only.
> Remove them when sending the actual request.

```json
{
  "invoiceNumber": "INV-2026-001",
  "date": "11 Apr 2026",

  // 👤 Customer Details
  "customerName": "Chirag Sardana",
  "customerPhone": "8684076590",

  // 💳 Payment Details
  "paymentMethod": "UPI",
  "paymentReference": "UPI-982374923",

  // 💰 Total Amount (Final)
  "total": 672.60,

  // 🧾 GST Details (Optional)
  // If provided and > 0, GST columns & totals will be shown
  // "gstTotal": "102.60",
  // "totalbeforegst": "570.00",

  // 📄 Page Configuration
  "pageSize": "A5",       // Use "A5" for half A4 size
  "itemMaxSize": 17,      // Max items per page

  // 📋 Items List
  "items": [
    {
      "name": "Farmhouse Pizza",
      "itemId": "PZ001",
      "qty": 2,
      "qtyDetails": "per piece",
      "price": 250,
      "discount": 50,

      // Optional GST per item
      // "gstRate": 18,
      // "priceAndGstRate": 295,

      "amount": 450
    },
    {
      "name": "Cold Coffee",
      "itemId": "CC002",
      "qty": 1,
      "qtyDetails": "per glass",
      "price": 120,
      "discount": 0,

      // "gstRate": 18,
      // "priceAndGstRate": 141.60,

      "amount": 120
    }
  ]
}
```

---

## 📄 A5 Page Behavior

* Setting:

  ```json
  "pageSize": "A5"
  ```

  generates a **half A4 sized invoice**.

* `itemMaxSize` controls:

  * Maximum number of items per page
  * Default recommended: **17 items**

---

## 🔁 Multi-Page Logic

* If items exceed `itemMaxSize`:

  * PDF is split into multiple pages
  * Pages are merged into a single PDF

* If **GST is present (`gstTotal > 0`)**:

  * Last **3 rows are reserved** for totals
  * Final page layout adjusts automatically

---

## 💡 Optional Fields Behavior

| Field             | Behavior                     |
| ----------------- | ---------------------------- |
| `gstTotal`        | Enables GST column + summary |
| `totalbeforegst`  | Shows pre-GST total          |
| `gstRate`         | Shows GST % per item         |
| `priceAndGstRate` | Shows price with GST         |

---

## 📤 Response

* Returns a **PDF file (byte stream)**
* Can be:

  * Downloaded
  * Previewed in browser
  * Saved to disk

---

## 🛠 Example (cURL)

```bash
curl -X POST http://localhost:8080/api/invoice/generate \
-H "Content-Type: application/json" \
-d @request.json \
--output invoice.pdf
```

---

## ⚠️ Important Notes

* Do **not send comments (`//`)** in actual JSON
* Ensure numeric values are valid (avoid `"null"` strings)
* Keep `items` array non-empty

---

## ✅ Features

* ✔ A5 / A4 page support
* ✔ Dynamic pagination
* ✔ GST handling
* ✔ Header repeat on each page
* ✔ Footer totals only on last page
* ✔ PDF merge for multi-page

---

## 📌 Summary

This API provides a flexible way to generate **professional invoice PDFs** with:

* Clean layout
* Dynamic data
* Smart pagination

---

**You're ready to generate invoices 🚀**
