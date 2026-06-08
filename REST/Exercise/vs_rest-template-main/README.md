# JAX-RS-Service-Template

Für eine Übungsaufgabe siehe [UEBUNG.md](UEBUNG.md).

## Schritt 1: Geschäftsobjekte definieren

Im Package `entity` werden die Geschäftsobjekte und Nachrichtendatentypen als einfache
Java-Klassen definiert.

Folgende Anforderungen sind dabei einzuhalten:

1. Es gibt einen Konstruktor ohne Parameter
2. Für alle Attribute, die auch später in den XML- und JSON-Objekten enthalten sein sollen, gibt es ein Getter-Setter-Pärchen
3. Für eine Serialisierung in XML benötigt die Klasse die Annotationen `@XmlRootElement` und `@XmlAccessorType(XmlAccessType.FIELD)`

## Schritt 2: Serviceklasse implementieren und annotieren

Alle annotierten Klassen im Package `service` werden automatisch beim Start des Servers (Klasse `Main`)
erkannt und sind als REST-Services erreichbar.

## Schritt 3: Server einrichten und starten

Der Port (hier `8080`) und der Kontext-Pfad (hier `api`) können über das Attribut `BASE_URI` in der Klasse `Main`
angepasst werden.

Die `main`-Methode startet den Server. Ab dann ist der Service erreichbar.

Als Testclients können Browser-Add-Ons (z. B. "RESTer") oder der HTTP-Client in IntelliJ genutzt werden. 