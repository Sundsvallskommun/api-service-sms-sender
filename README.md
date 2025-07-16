# SmsSender

_The service provides functionality to send SMS via external SMS gateways. Support is currently implemented for the following gateways:_

* Telia
* LinkMobility

_Please note that agreements with suppliers for each SMS gateway must be signed separately._

## Getting Started

### Prerequisites

- **Java 21 or higher**
- **Maven**
- **Git**
- **[Dependent Microservices](#dependencies)**

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Sundsvallskommun/api-service-sms-sender.git
cd api-service-sms-sender
```

2. **Configure the application:**

   Before running the application, you need to set up configuration settings.
   See [Configuration](#configuration)

   **Note:** Ensure all required configurations are set; otherwise, the application may fail to start.

3. **Ensure dependent services are running:**

   If this microservice depends on other services, make sure they are up and accessible. See [Dependencies](#dependencies) for more details.

4. **Build and run the application:**

   - Using Maven:

```bash
mvn spring-boot:run
```

- Using Gradle:

```bash
gradle bootRun
```

## Dependencies

This microservice does not depend on any other internal services. However, it does depend on external services for the provider(s) it intends to use:

- **Telia**
  - **Purpose:** Used to send SMS via Telia's gateway. An external agreement is required.
  - **Repository:** Service is provided by third party (Telia)
- **LinkMobility**
  - **Purpose:** Used to send SMS via LinkMobility's gateway. An external agreement is required.
  - **Repository:** Service is provided by third party (LinkMobility)

## API Documentation

Access the API documentation via:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Usage

### API Endpoints

See the [API Documentation](#api-documentation) for detailed information on available endpoints.

### Example Request

```bash


curl -X 'POST' \
  'https://localhost:8080/2281/send/sms' \
  -d '{
  "sender": {
    "name": "some sendername"
  },
  "mobileNumber": "+46701234567",
  "priority": "NORMAL",
  "message": "a message"
}'
```

## Configuration

Configuration is crucial for the application to run successfully. Ensure all necessary settings are configured in `application.yml`.

### Key Configuration Parameters

- **Server Port:**

```yaml
server:
  port: 8080
```

- **External Service URLs**

```yaml
provider:
  telia:
    base-url: <base-url>
    enabled: <boolean>
    priority: <integer>
    flash-capable: <boolean>
    oauth2:
      token-url: <token-url>
      client-id: <client-id>
      client-secret: <client-secret>
  linkmobility:
    base-url: <base-url>
    enabled: <boolean>
    priority: <integer>
    flash-capable: <boolean>
    platform-id: <platform-id>
    platform-partner-id: <platform-partner-id>
    basicauth:
      username: <username>
      password: <password>

```

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)

## 

&copy; 2021 Sundsvalls kommun
