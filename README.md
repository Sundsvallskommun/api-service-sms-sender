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

Konfiguration sker i filen `src/main/resources/application.properties` genom att sätta nedanstående properties till önskade värden:

<table width="100%">
  <tbody>
    <tr>
      <td colspan="2"><strong>Generella inställningar</strong></td>
    </tr>
    <tr>
      <td><code>integration.sms.provider</code></td>
      <td>Vilken SMS-gateway som ska användas<br />(<code>telia</code> eller <code>linkmobility</code>)</td>
    </tr>
    <tr>
      <td colspan="2"><strong>Inställningar för Telias SMS-gateway</strong></td>
    </tr>
    <tr>
      <td><code>integration.telia.apiUrl</code></td>
      <td>API-URL</td>
    </tr>
    <tr>
      <td><code>integration.telia.tokenUrl</code></td>
      <td>URL för att hämta OAuth2-token</td>
    </tr>
    <tr>
      <td><code>integration.telia.clientId</code></td>
      <td>OAuth2-klient-id</td>
    </tr>
    <tr>
      <td><code>integration.telia.clientSecret</code></td>
      <td>OAuth2-klient-nyckel</td>
    </tr>
    <tr>
      <td colspan="2"><strong>Inställningar för LinkMobilitys SMS-gateway</strong></td>
    </tr>
    <tr>
      <td><code>integration.linkmobility.apiUrl</code></td>
      <td>API-URL</td>
    </tr>
    <tr>
      <td><code>integration.linkmobility.username</code></td>
      <td>Användarnamn</td>
    </tr>
    <tr>
      <td><code>integration.linkmobility.password</code></td>
      <td>Lösenord</td>
    </tr>
    <tr>
      <td><code>integration.linkmobility.platformId</code></td>
      <td>Plattforms-id</td>
    </tr>
    <tr>
      <td><code>integration.linkmobility.platformPartnerId</code></td>
      <td>Plattforms-partner-id</td>
    </tr>
  </tbody>
</table>


### Paketera och starta tjänsten

Paketera tjänsten som en körbar JAR-fil genom:

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


Copyright &copy; 2022 Sundsvalls Kommun