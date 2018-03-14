Projekt jest realizacj� zadania dotycz�cego czujnik�w znajduj�cych si� w silnikach.
Przyk�adowy plik, zawieraj�cy usystematyzowane informacje na temat sensor�w umieszony jest w serwisie github:
https://github.com/arekpilarski/Files/blob/master/sensors.yml
Plik ten znajduje si� r�wnie� w projekcie, w folderze SourceFile.


---------------------------
Potrzebne narz�dzia i oprogramowanie:
- system Linux
- narz�dzie maven
- java development kit
- narz�dzie curl

----------------------------
Instrukcja przygotowania:

1. Zainstalowa� narz�dzie maven, je�li nie jest zainstalowane.
	sudo apt install maven

2. Zainstalowa� openjdk, je�li nie jest zainstalowane (program zosta� napisany wykorzystuj�c openjdk w wersji 8)
	sudo apt install openjdk-8-jdk

3. Ustawi� zmienn� �rodowiskow� JAVA_HOME, np. w pliku /etc/environment
	Modyfikuj�c wspomniany plik, nale�y doda� do niego: 
	JAVA_HOME="/usr/lib/jvm/wersja-openjdk" ( np. JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64" )

   Po modyfikacji pliku etc/environment nale�y doda� w pliku ~/.bashrc komend�:
	source /etc/environment

4. Zainstalowa� narz�dzie curl
	sudo apt install curl

5. Rozpakowa� folder z projektem
	tar -xzvf RelayrProject.tar.gz


----------------------------
Kompilacja i uruchamianie:

0. Komendy nale�y wykonywa� b�d�c w folderze simple-service
	cd /path/to/simple-service ( np. cd /home/user/simple-service)

1. Aby skompilowa� program, nale�y u�y� komendy:
	mvn clean compile

2. Aby uruchomi� serwer, nale�y u�y� komendy:
	mvn exec:java lub mvn exec:java -Dexec.args="https://github.com/..."
	(np. mvn exec:java -Dexec.args="https://github.com/arekpilarski/Files/blob/master/sensors.yml")

Program mo�e przyj�� parametr, kt�rym jest link do pliku znajduj�cego si� w serwisie github.
Je�li nie zostanie podany �aden parametr, serwer uruchomi si� korzystaj�c z pliku znajduj�cego si� w projekcie.

----------------------------
Mo�liwe zapytania do serwera (realizacja zadania):
-Zapytania nale�y realizowa� z innego terminalu ni� ten, w kt�rym uruchomiono serwer-

1. W celu uzyskania listy silnik�w, kt�re dzia�aj� nieprawid�owo, nale�y u�y� komendy:
	curl -XGET "http://localhost:8080/resource/engines?pressure_threshold=VALUE1&temp_threshold=VALUE2"
	gdzie VALUE1 i VALUE2 to ca�kowite warto�ci liczbowe (granice).

2. W celu aktualizacji warto�ci czujnika, nale�y u�y� komendy:
	curl -XPOST "http://localhost:8080/resource/sensors/VALUE1" -H "Content-Type=application/json" -d '{"operation":"VALUE2","value":"VALUE3"}' 
	gdzie:
	VALUE1 - numer ID czujnika, kt�rego warto�� chcemy zaktualizowa�.
	VALUE2 - jedna z dost�pnych operacji: increment/set/decrement.
	VALUE3 - warto��, o jak� zwi�kszamy / na jak� ustawiamy / o jak� zmniejszamy, zale�nie od typu operacji.

3. Aby pobra� list� wszystkich sensor�w i wy�wietli� j� w formacie JSON nale�y u�y� komendy:
	curl -XGET "http://localhost/resource/sensors" | python -m json.tool


----------------------------
Uruchamianie test�w jednostkowych:

1. Aby uruchomi� wszystkie testy jednostkowe, b�d�c w folderze projektu simple-service, nale�y u�y� komendy:
	mvn test

2. W celu uruchomienia konkretnego testu jednostkowego, nale�y u�y� komendy:
	mvn -Dtest=MyResourceTest.java#nazwaTestu test
	gdzie nazwaTestu, to nazwa konkretnego testu jednostkowego, np:
	mvn -Dtest=MyResourceTest.java#testUpdateSensorMethod test




	