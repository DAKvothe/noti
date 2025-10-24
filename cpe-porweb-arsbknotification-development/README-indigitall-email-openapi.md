# Indigitall Email OpenAPI — Instrucciones de uso (Hexagonal)

Este paquete incluye el contrato **OpenAPI 3** para el endpoint de **envío de correo** vía Indigitall (`/v2/email/send/list`, región **AM1**).

## 1) ¿Dónde colocar el archivo?
Copia `indigitall-email.yaml` en tu proyecto hexagonal en la ruta recomendada:

```
<repo-root>/src/main/resources/openapi/indigitall-email.yaml
```

> Nota: Tu `pom.xml` ya tiene configurado el plugin `openapi-generator-maven-plugin` para *otro* contrato (`src/main/resources/openapi.yaml`). **No lo sobrescribas**. Agregaremos una **segunda ejecución** para generar el **cliente** de Indigitall a partir de `openapi/indigitall-email.yaml`.

## 2) Agregar nueva ejecución del plugin en `pom.xml`

Dentro del bloque `<plugins>` del `pom.xml`, agrega **otra** `<execution>` para generar el **cliente Java WebClient** desde este contrato:

```xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>7.6.0</version>
  <executions>
    <!-- (Ejecuciones existentes: NO tocarlas) -->

    <!-- Nueva ejecución: cliente Indigitall Email -->
    <execution>
      <id>generate-indigitall-email-client</id>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <inputSpec>${project.basedir}/src/main/resources/openapi/indigitall-email.yaml</inputSpec>
        <generatorName>java</generatorName>
        <output>${project.build.directory}/generated-sources/indigitall-email-client</output>
        <apiPackage>com.santander.cpe.porweb.indigitall.email.api</apiPackage>
        <modelPackage>com.santander.cpe.porweb.indigitall.email.model</modelPackage>
        <invokerPackage>com.santander.cpe.porweb.indigitall.email.invoker</invokerPackage>
        <configOptions>
          <library>webclient</library>
          <dateLibrary>java8</dateLibrary>
          <hideGenerationTimestamp>true</hideGenerationTimestamp>
        </configOptions>
      </configuration>
    </execution>
  </executions>
</plugin>
```

Y asegúrate de **añadir las fuentes generadas** al classpath en `generate-sources` (si tu plugin no lo hace automáticamente):

```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>build-helper-maven-plugin</artifactId>
  <version>3.5.0</version>
  <executions>
    <execution>
      <id>add-openapi-indigitall-email-sources</id>
      <phase>generate-sources</phase>
      <goals>
        <goal>add-source</goal>
      </goals>
      <configuration>
        <sources>
          <source>${project.build.directory}/generated-sources/indigitall-email-client/src/main/java</source>
        </sources>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 3) Ejecutar la generación

### Opción A — Maven
Desde la raíz del repo:

```bash
mvn -DskipTests clean generate-sources
```

El código cliente quedará en:
```
target/generated-sources/indigitall-email-client/src/main/java
```

### Opción B — CLI Docker (alternativa)
Si prefieres no tocar el `pom.xml` todavía:

```bash
docker run --rm -v "$PWD:/local" openapitools/openapi-generator-cli:v7.6.0 generate   -i /local/src/main/resources/openapi/indigitall-email.yaml   -g java   -o /local/target/generated-sources/indigitall-email-client   --additional-properties library=webclient,apiPackage=com.santander.cpe.porweb.indigitall.email.api,modelPackage=com.santander.cpe.porweb.indigitall.email.model,invokerPackage=com.santander.cpe.porweb.indigitall.email.invoker,dateLibrary=java8,hideGenerationTimestamp=true
```

## 4) Configurar autenticación (ServerKey)

El contrato define `ServerKeyAuth` como **apiKey en header `Authorization`**. Tras generar el cliente, configura el prefijo y la clave en el `ApiClient`:

```java
import com.santander.cpe.porweb.indigitall.email.invoker.ApiClient;
import com.santander.cpe.porweb.indigitall.email.api.EmailApi;

ApiClient apiClient = new ApiClient();
apiClient.setBasePath("https://am1.api.indigitall.com");
apiClient.setApiKey("TU-UUID-DE-SERVERKEY");      // solo el UUID
apiClient.setApiKeyPrefix("ServerKey");           // prefijo requerido

EmailApi emailApi = new EmailApi(apiClient);
```

> Aconsejado: inyecta estos valores desde variables de entorno o `application.yml` y **no** los dejes hardcodeados.

## 5) Ejemplo de uso en tu capa Infra (Hexagonal)

```java
import com.santander.cpe.porweb.indigitall.email.api.EmailApi;
import com.santander.cpe.porweb.indigitall.email.model.SendEmailRequest;
import com.santander.cpe.porweb.indigitall.email.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class IndigitallEmailClientAdapter {

  private final EmailApi emailApi;

  public IndigitallEmailClientAdapter(EmailApi emailApi) {
    this.emailApi = emailApi;
  }

  public void send(Long campaignId, String email) {
    SendEmailRequest req = new SendEmailRequest()
        .campaignId(campaignId)
        .addContactsItem(new Contact().email(email));
    emailApi.sendEmailList(req); // nombre del método según operationId
  }
}
```

> Si tu operación requiere `scheduleAt`, `cc`, `vars` o `attachments`, agrega esos campos al DTO generado.

## 6) Validación rápida (curl)

```bash
curl --location --request POST 'https://am1.api.indigitall.com/v2/email/send/list'   --header "Authorization: ServerKey ${INDIGITALL_SERVER_KEY}"   --header 'Content-Type: application/json'   --header 'Accept: application/json'   --data '{
    "campaignId": 3836,
    "scheduleAt": "2025-10-17T05:00:00.000Z",
    "contacts": [{ "email": "user@example.com" }]
  }'
```

---

**Listo.** Coloca el YAML en `src/main/resources/openapi/`, añade la ejecución del plugin, ejecuta `mvn clean generate-sources` y utiliza el cliente generado desde tu adaptador Infra.