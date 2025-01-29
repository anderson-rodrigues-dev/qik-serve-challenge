# **Checkout API**

## **Introduction**

This repository contains the **Checkout API**, a Java-based solution that processes shopping cart calculations and applies promotions dynamically. The API is integrated with a mock server (**WireMock**) that simulates the backend for product data and promotions.

---

## **WireMock**

The **WireMock** server provides a mock backend with the following endpoints:

1. **List All Products**  
   **GET** `/products`  
   Example Response:

   ```json
   [
       {"id": "Dwt5F7KAhi", "name": "Amazing Pizza!", "price": 1099},
       {"id": "PWWe3w1SDU", "name": "Amazing Burger!", "price": 999}
   ]
   ```

2. **Product Information**  
   **GET** `/products/{productId}`  
   Example Response:

   ```json
   {
       "id": "Dwt5F7KAhi",
       "name": "Amazing Pizza!",
       "price": 1099,
       "promotions": [
           {"id": "ibt3EEYczW", "type": "QTY_BASED_PRICE_OVERRIDE", "required_qty": 2, "price": 1799}
       ]
   }
   ```

---

## **Checkout API**

The **Checkout API** provides a single endpoint to calculate the checkout details based on the provided cart items.

### **Endpoint**

- **POST** `/api/checkout`

### **Request Body**

A list of products and their quantities:

```json
[
    {"productId": "Dwt5F7KAhi", "quantity": 2},
    {"productId": "PWWe3w1SDU", "quantity": 1}
]
```

### **Response**

The calculated checkout summary, including individual and total savings:

```json
{
    "items": [
        {
            "productId": "Dwt5F7KAhi",
            "name": "Amazing Pizza!",
            "unitPrice": 1099,
            "quantity": 2,
            "grossPrice": 2198,
            "discount": 399,
            "calculatedPrice": 1799
        },
        {
            "productId": "PWWe3w1SDU",
            "name": "Amazing Burger!",
            "unitPrice": 999,
            "quantity": 1,
            "grossPrice": 999,
            "discount": 0,
            "calculatedPrice": 999
        }
    ],
    "subtotal": 3197,
    "totalSavings": 399,
    "totalPrice": 2798
}
```

---

## **Logic and Technical Highlights**

### **Logic**

- **Promotion Handling:** The API dynamically applies multiple types of promotions (e.g., quantity-based discounts, percentage-based discounts).
- **Calculation Workflow:**
  1. Retrieves product details from WireMock.
  2. Identifies and applies applicable promotions.
  3. Calculates gross price, discounts, and final prices for each item.

### **Technical Highlights**

- **Distributed Tracing:** OpenTelemetry and Zipkin were integrated to trace API requests and monitor performance.
- **Internationalization (i18n):** Product names are translated into English, Portuguese, and Spanish based on request headers.
- **Integration Testing:** WireMock was integrated with Testcontainers for realistic testing scenarios.
- **Pipelines:** CI/CD pipelines were implemented using GitHub Actions for automated deployment to AWS.

---

## **Sequence Diagram**

<img src="https://github.com/anderson-rodrigues-dev/qik-serve-challenge/blob/master/CheckoutSequenceDiagram.png" alt="Sequence Diagram">

---

## **API URL**

[Production URL](http://ec2-54-197-15-89.compute-1.amazonaws.com) (HTTP)

### **Application Ports**

- **Checkout API:** Running on port `80`. If you access the root path (`/`), you will be redirected to the Swagger documentation.
- **Zipkin:** Running on port `9411`.

---

## Follow-Up Questions and Answers
[Answers Link](https://github.com/anderson-rodrigues-dev/qik-serve-challenge/blob/master/QikServe_TechTest_Questions.md)

---

## **Contact**

- **Name:** Anderson Alves  
- **Email:** [and.rt@hotmail.com](mailto:and.rt@hotmail.com)
- **LinkedIn:** [linkedin.com/in/anderson-rod](https://linkedin.com/in/anderson-rod/)
