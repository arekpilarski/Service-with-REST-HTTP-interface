Projekt jest realizacja zadania dotyczacego czujnikow znajdujacych sie w silnikach.
Przykladowy plik, zawierajacy usystematyzowane informacje na temat sensorow umieszony jest w serwisie github:
https://github.com/arekpilarski/Files/blob/master/sensors.yml
Plik ten znajduje sie rowniez w projekcie, w folderze SourceFile.


---------------------------
Potrzebne narzedzia i oprogramowanie:
- system Linux
- narzedzie maven
- java development kit
- narzedzie curl

----------------------------
Instrukcja przygotowania:

1. Zainstalowac narzedzie maven, jesli nie jest zainstalowane.
	sudo apt install maven

2. Zainstalowac openjdk, jesli nie jest zainstalowane (program zostal napisany wykorzystujac openjdk w wersji 8)
	sudo apt install openjdk-8-jdk

3. Ustawic zmienna srodowiskowa JAVA_HOME, np. w pliku /etc/environment
	Modyfikujac wspomniany plik, nalezy dodac do niego: 
	JAVA_HOME="/usr/lib/jvm/wersja-openjdk" ( np. JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64" )

   Po modyfikacji pliku etc/environment nalezy dodac w pliku ~/.bashrc komende:
	source /etc/environment

4. Zainstalowac narzedzie curl
	sudo apt install curl

5. Rozpakowac folder z projektem
	tar -xzvf RelayrProject.tar.gz


----------------------------
Kompilacja i uruchamianie:

0. Komendy nalezy wykonywac bedac w folderze simple-service
	cd /path/to/simple-service ( np. cd /home/user/simple-service)

1. Aby skompilowac program, nalezy uzyc komendy:
	mvn clean compile

2. Aby uruchomic serwer, nalezy uzyc komendy:
	mvn exec:java lub mvn exec:java -Dexec.args="https://github.com/..."
	(np. mvn exec:java -Dexec.args="https://github.com/arekpilarski/Files/blob/master/sensors.yml")

Program moze przyjac parametr, ktorym jest link do pliku znajdujacego sie w serwisie github.
Jesli nie zostanie podany zaden parametr, serwer uruchomi sie korzystajac z pliku znajdujacego sie w projekcie.

----------------------------
Mozliwe zapytania do serwera (realizacja zadania):
-Zapytania nalezy realizowac z innego terminalu niz ten, w ktorym uruchomiono serwer-

1. W celu uzyskania listy silnikow, ktore dzialaja nieprawidlowo, nalezy uzyc komendy:
	curl -XGET "http://localhost:8080/resource/engines?pressure_threshold=VALUE1&temp_threshold=VALUE2"
	gdzie VALUE1 i VALUE2 to calkowite wartosci liczbowe (granice).

2. W celu aktualizacji wartosci czujnika, nalezy uzyc komendy:
	curl -XPOST "http://localhost:8080/resource/sensors/VALUE1" -H "Content-Type=application/json" -d '{"operation":"VALUE2","value":"VALUE3"}' 
	gdzie:
	VALUE1 - numer ID czujnika, ktorego wartosc chcemy zaktualizowac.
	VALUE2 - jedna z dostepnych operacji: increment/set/decrement.
	VALUE3 - wartosc, o jaka zwiekszamy / na jaka ustawiamy / o jaka zmniejszamy, zaleznie od typu operacji.

3. Aby pobrac liste wszystkich sensorow i wyswietlic ja w formacie JSON nalezy uzyc komendy:
	curl -XGET "http://localhost/resource/sensors" | python -m json.tool


----------------------------
Uruchamianie testow jednostkowych:

1. Aby uruchomic wszystkie testy jednostkowe, bedac w folderze projektu simple-service, nalezy uzyc komendy:
	mvn test

2. W celu uruchomienia konkretnego testu jednostkowego, nalezy uzyc komendy:
	mvn -Dtest=MyResourceTest.java#nazwaTestu test
	gdzie nazwaTestu, to nazwa konkretnego testu jednostkowego, np:
	mvn -Dtest=MyResourceTest.java#testUpdateSensorMethod test




	
