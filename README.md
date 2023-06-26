# Projekt zaliczeniowy z przedmiotu: _**Programowanie zespołowe**_ 
### Temat projektu: System do zarządzania zadaniami w firmie informatycznej.

### Skład grupy:
* Lidia Pacyna
* Kamila Szydełko
* Weronika Nowacka
* Kacper Dziuba
* Patryk Wyczawski

### Specyfikacja projektu:
#### Cel projektu:
Stworzenie systemu dla firmy informatycznej.

#### Cele szczegółowe: 
1. Zaprojektowanie funkcjonalnego i prostego w użytku oprogramowania umożliwiającego organizację zadań.
2. Rozwinięcie umiejętności pracy zespołowej w procesie tworzenia oprogramowania.
3. Doskonalenie umiejętności tworzenia aplikacji desktopowych.

#### Funkcjonalności:
* Rejestracja oraz logowanie się do systemu.
* Tworzenie i zarządzanie zadaniami.
* Dodawanie nowych pracowników.
* Sortowanie zadań oraz pracowników.
* Zmiana danych osobowych pracowników. 
* Zmiana statusu zadań.
* Generowanie raportów.

### Interfejs systemu
#### Strona główna
![Main](https://github.com/wnowacka01/PZ_2023_G2_Weronika/assets/82800494/0d5c9f46-7aa0-432d-a708-666e4cb7c95a)

#### Panel administratora
![Admin](https://github.com/wnowacka01/PZ_2023_G2_Weronika/assets/82800494/ea011b65-66be-4ef6-bc65-a90a57cfaa55)

#### Moduł dodawania zadań
![Task](https://github.com/wnowacka01/PZ_2023_G2_Weronika/assets/82800494/22022343-783a-4afd-91ff-e8c76d73f99b)

### Baza danych
####	Diagram ERD
![ERD](https://github.com/wnowacka01/PZ_2023_G2_Weronika/assets/82800494/65f28120-4b38-4aaf-aab7-bbc04fc4fa57)

#### Informacje o połączeniu z bazą danych
<p>Host = "localhost"</p>
<p>Nazwa bazy danych = "firma"</p>
<p>użytkownik = "root"</p>
<p>hasło = ""</p>

<p>Połączenie z bazą danych MySQL jest zrealizowane przy pomocy JDBC.</p>

#### Inne przydatne informacje dotyczące bazy danych:
- MySQL w Xamppie uruchomiony jest na porcie 3306.
- Apache w Xamppie uruchomiony jest na portach 80 oraz 443.

### Wykorzystane technologie:
* Java 18
* Baza danych MySQL
* JavaFX 
* JUnit 5
* fontawesome
* iText
 
### Potrzebne nazwy użytkowników do uzyskania innych funkcjonalności aplikacji:
* Konto administratora: Login: email1@wp.pl, hasło: haslo1
* Konto kierownika: Login: email3@wp.pl, hasło: haslo3
* Konto pracownika: Login: email2@wp.pl, hasło: haslo2
