# GraphQL Java Workshop Vorbereitungen

Hier findest Du alle Voraussetzungen und Vorbereitungen für meinen GraphQL Workshop.

## Voraussetzungen

**Für dein Laptop/PC**

Für die Übungen benötigst Du:

- JDK 11 (z.B. OpenJDK)
- Gradle
- Git
- eine IDE (z.B. IntelliJ oder Eclipse)
- Frei Ports: 5000 und 5010

**Während des Workshops**

- Da wir vor und während des Trainings ggf. noch Aktualisierungen installieren müssen, bitte sicherstellen, dass auch während des Trainings auf deinem Computer der Internet-Zugang (logisch, online-Schulung 🙃) funktioniert - aber auch **für git und gradle** (Proxies beachten!)
- **Ich freue mich, wenn Du während des Trainings deine Kamera an hast**, damit wir uns sehen können 🎥. Mikrofon hingegen bitte nur anmachen, wenn Du etwas sagen oder fragen möchtest (was Du natürlich jederzeit darfst!)
- W-LAN ist bequem, aber gerade bei (langen) Streamings ist ein Kabel-gebundenes Netzwerk stabiler als W-LAN, also im Zweifel lieber das Kabel einstecken (und W-LAN deaktivieren) 😊

# Installation und Vorbereitung des Workspaces für die Schulung

- Bitte führe die folgenden Schritte **vor dem Workshop** einmal aus, damit wir während des Workshops Zeit sparen.
- Hinweis: wahrscheinlich musst Du im Workshop das Repository erneut klonen und auch noch einmal `gradle build` ausführen, aber das sollten dann nur noch kleine Änderungen sein. Bitte stelle aber unbedingt sicher, dass Du auch während des Workshops Zugriff auf git und gradle hast (bzw. Internet-Zugang dafür)
- bitte achte darauf, das `code`-Verzeichnis zu verwenden (und **nicht** das `app`-Verzeichnis)

## Schritt 1: Repository klonen

```
git clone https://github.com/nilshartmann/graphql-java-workshop
```

## Schritt 2: User Service bauen und starten

Im Verzeichnis `code/userservice` den User Service bauen und starten:

```
cd code/userservice
./gradew clean bootRun
```

- Der User Service sollte nun laufen (Ausgabe auf der Konsole: `Started UserServiceApplication`)
- Der Service benötigt Port 5010, bitte darauf achten, dass der Port frei ist.
- Du kannst den Service testen, in dem Du `http://localhost:5010/users` im Browser aufrufst. Es sollte eine Liste mit User-(JSON-)Objekten zurückkommen

Da wir an diesem Service nicht arbeiten werden, brauchst Du den Code nicht in deine IDE zu importieren. Es reicht, wenn dieser Service während des Workshops läuft.

## Schritt 3: ProjectMgmtApp bauen und starten

Im Verzeichnis `code/backend` die Beispiel-Anwendung (ProjectMgmtApp) bauen und starten:

```
cd code/backend
./gradew clean bootRun
```

- Die Applikation sollte nun laufen (Ausgabe auf der Konsole: `SERVER RUNNING`)
- Der Service benötigt Port 5000, bitte darauf achten, dass der Port frei ist.
- Du kannst testen, ob die Anwendung korrekt läuft, in dem Du `http://localhost:5000/` im Browser aufrufst. Es sollte der GraphQL Playground erscheinen (solange keine Fehlermeldung erscheint, ist alles gut 😉)

**Importieren in die IDE**

In dieser Anwendung werden wir während des Workshops Übungen machen, deswegen solltest Du dieses Projekt auch in deine IDE importieren und sicherstellen, dass es dort compiliert und Du es von dort ggf. auch starten und debuggen kannst.
In IntelliJ sollte es reichen, den Ordner `code/backend` zu öffnen, um das (Gradle-)Projekt zu importieren.

## Fragen oder Probleme

Bei Fragen oder Problemen, kannst Du dich an mich wenden. Meine Kontaktdaten findest Du [auf meiner Homepage](https://nilshartmann.net).

![Greeting App](screenshot-example-app.png)
