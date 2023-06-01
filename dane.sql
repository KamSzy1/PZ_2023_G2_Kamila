use firma;

insert into statuses values (1,"Zrealizowano");
insert into statuses values (2,"Do realizacji");
insert into statuses values (3,"W trakcie");
insert into statuses values (4,"Po terminie");

insert into positions values (1,"Administrator","Posiada uprawnienia do zarządzania pracownikami, do dodawania zadań oraz może generować raporty");
insert into positions values (2,"Kierownik","Przydziela pracowników do zadań, może generować raporty");
insert into positions values (3,"Pracownik","Posiada uprawnienia do zmiany statusu zadań");

insert into login values (1,"email","207023ccb44feb4d7dadca005ce29a64");
insert into login values (2,"email1","6d932c406fa15164ee48ff5a52f81dae");
insert into login values (3,"email2","ed71c5d55af657bc2413020e5580d4dd");
insert into login values (4,"email3","daa9bda719032ae88abadb9cda4aa846");
insert into login values (5,"email4","4bc0550cd0afc7bbe97be48a36303f6e");

insert into users values (1,"Patryk","Wyczawski","Urzednicza 2/9","32-032","Poznań","123456789",3,"token",1,1);
insert into users values (2,"Kamila","Szydełko","Warszawska 2","31-111","Kraków","123123123",1,"token1",2,2);
insert into users values (3,"Kacper","Dziuba","Podwisłocze 31","11-111","Rzeszów","321321321",3,"token2",3,3);
insert into users values (4,"Lidia","Pacyna","Lwowska 307","21-021","Radom","123459876",2,"token3",2,4);
insert into users values (5,"Weronika","Nowacka","Mieszka 1","11-111","Rzeszów","987654321",2,"token4",1,5);

insert into tasks values (1,"Funkcje pracownika","Tworzenie funkcjonalności panelu pracownika",3,1);
insert into tasks values (2,"Tworzenie dokumentacji","Utworzenie podstawowego zarysu projektu",1,1);
insert into tasks values (3,"Testy jednostkowe","Utworzenie testów jednostkowych",2,3);
insert into tasks values (4,"Optymalizacja wydajności serwera"," Zadanie polegające na analizie i zoptymalizowaniu wydajności serwera w celu zapewnienia szybkiego działania aplikacji oraz zwiększenia przepustowości i stabilności systemu.",2,2);
insert into tasks values (5,"Testowanie aplikacji mobilnych","zadanie polegające na przeprowadzeniu testów funkcjonalnych i niezawodnościowych aplikacji mobilnych na różnych platformach, w tym iOS i Android.",2,4);