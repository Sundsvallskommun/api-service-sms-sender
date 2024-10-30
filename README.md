# SmsSender

## Leverantör

Sundsvalls Kommun

## Beskrivning

SmsSender används för att skicka SMS via extern SMS-gateway. För tillfället finns det implementerat stöd för användning av följande gateways:

* Telia
* LinkMobility

Observera att avtal med leverantör för respektive SMS-gateway måste tecknas.

## Tekniska detaljer

### Konfiguration

|                Miljövariabel                |                                Beskrivning                                 |
|---------------------------------------------|----------------------------------------------------------------------------|
| **Inställningar för Telias SMS-gateway**                                                                                ||
| `provider.telia.enabled`                    | Flagga för att aktivera/inaktivera gateway                                 |
| `provider.telia.priority`                   | Prioritet för gateway                                                      |
| `provider.telia.flash-capable`              | Flagga för att ange om gateway ska kunna användas för att skicka flash-SMS |
| `provider.telia.base-url`                   | API-URL                                                                    |
| `provider.telia.oauth2.token-url`           | URL för att hämta OAuth2-token                                             |
| `provider.telia.oauth2.client-id`           | OAuth2-klient-id                                                           |
| `provider.telia.oauth2.client-secret`       | OAuth2-klient-nyckel                                                       |
| **Inställningar för LinkMobilitys SMS-gateway**                                                                         ||
| `provider.linkmobility.enabled`             | Flagga för att aktivera/inaktivera gateway                                 |
| `provider.linkmobility.priority`            | Prioritet för gateway                                                      |
| `provider.linkmobility.flash-capable`       | Flagga för att ange om gateway ska kunna användas för att skicka flash-SMS |
| `provider.linkmobility.base-url`            | API-URL                                                                    |
| `provider.linkmobility.basicauth.username`  | Användarnamn                                                               |
| `provider.linkmobility.basicauth.password`  | Lösenord                                                                   |
| `provider.linkmobility.platform-id`         | Plattforms-id                                                              |
| `provider.linkmobility.platform-partner-id` | Plattforms-partner-id                                                      |

### Paketera och starta tjänsten

Tjänsten kan paketeras genom:

```
mvn package
```

Starta med:

```
java -jar target/api-service-sms-sender-<VERSION>.jar
```

### Bygga och starta tjänsten med Docker

Bygg en Docker-image av tjänsten:

```
mvn spring-boot:build-image
```

Starta en Docker-container:

```
docker run -i --rm -p 8080:8080 evil.sundsvall.se/ms-sms-sender:latest
```

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-sms-sender&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-sms-sender)

## 

Copyright (c) 2021 Sundsvalls kommun
