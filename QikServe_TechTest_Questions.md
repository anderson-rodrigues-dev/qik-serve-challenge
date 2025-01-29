# **QikServe Engineer Technical Test**

## **Follow-Up Questions and Answers**

### **1. How much time did you spend on the test? What would you add if you had more time?**
I spent approximately **32 hours** developing the technical test.

If I had more time, I would add:
- **Advanced monitoring with Grafana dashboards**: To integrate and analyze the collected metrics more effectively.
- **Load testing**: Using tools like Apache JMeter to validate the system's scalability.
- **WireMock multi-tenancy**: Further automating the configuration to handle multiple POS simultaneously.

---

### **2. What was the most useful feature added in the latest version of your chosen language? Include a code snippet demonstrating its usage.**
The most useful feature was the implementation of **Sequenced Collections** in Java 21. This new interface allows retrieval of the first and last elements, simplifying logic.

**Code snippet used in the project:**
```java
SequencedCollection<String> classNameParts = List.of(ex.getClass().getName().split("\\$"));

String statusName = classNameParts.getLast();
String normalizedStatusName = statusName.replaceAll("([A-Z])", "_$1").toUpperCase().substring(1);

HttpStatus status = HttpStatus.valueOf(normalizedStatusName);
```

This snippet was used to standardize the status codes returned with API errors.

---

### **3. What did you find most difficult?**
- **Handling complex promotions**: Implementing logic for multiple types of promotions and ensuring they were applied correctly with different combinations of items.
- **Distributed Tracing**: Integrating OpenTelemetry and Zipkin required adjustments to Docker Compose and deployment configurations.

---

### **4. What mechanism did you implement to track production issues? What could you improve?**

#### **Implemented:**
- **Distributed Tracing with OpenTelemetry and Zipkin**:
  - Each request generates spans that can be tracked in Zipkin.
  - Custom span logic was added to monitor critical operations.

#### **What could be improved:**
- **Adopt Grafana**: To build more detailed dashboards based on collected spans.
- **Integrate structured logs**: Send logs with span context to tools like Elasticsearch.

---

### **5. Explain your interpretation of the requirements list and what was delivered or not delivered and why.**

#### **Mandatory Requirements:**
1. **Java API**:
   - Consume the provided WireMock server.
   - Make the retrieved content usable.
   - Handle promotions as specified:
     - Apply promotions to products when applicable.
     - Ensure customer savings are displayed.
   - Support prices expressed in pennies (cents).

2. **Automated Testing**:
   - Unit and integration tests.
   - Ensure sufficient coverage to validate critical functionalities.

3. **Checkout System**:
   - Calculate the total cost of a "shopping cart."
   - Handle any combination of items and promotions.
   - Ensure items can be added in any order.
   - Facilitate the introduction of new prices or promotion types in the future.

---

#### **Recommended:**
1. **Hosted API**:
   - Make the API available in a testable environment (optional but appreciated).
2. **Internationalization**:
   - Add support for multiple languages for product names.

---

#### **Optional:**
1. **WireMock Multi-Tenancy**:
   - Support multiple WireMocks representing different POS.
   - Allow varied data formats mapped to the same API.

---

#### **Not Required:**
1. **Graphical Interface**:
   - No need to build an HTML or frontend user interface.
   - Could be added only if the candidate wishes to impress.

---

#### **Deliveries:**
1. **API Development**:
   - Implemented a REST API that consumes WireMock and processes promotions.
   - Displayed unit and total savings for customers during checkout.

2. **Promotions:**
   - Created logic to identify and apply relevant promotions.
   - Ensured promotions are adaptable for future changes.

3. **Automated Testing:**
   - Unit tests using Mockito.
   - Integration tests using WireMock with Testcontainers + Docker.

4. **Tracing and Monitoring:**
   - Implemented distributed tracing (OpenTelemetry + Zipkin).

5. **Internationalization:**
   - Configured translated messages for product names.

6. **Deployment Pipelines:**
   - Implemented GitHub Actions pipelines to automate build and deploy to AWS.
   - Ensured the CI/CD process is replicable and efficient.

#### **Not Delivered:**
- Advanced WireMock multi-tenancy was not fully automated due to time constraints.
- A graphical interface was not developed; instead, the focus was on the backend.

---
