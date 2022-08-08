# REST

Usługa REST odbierająca i zapisująca informacje o pozycjach urządzeń w formacie: <br><br>
{
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'deviceid': 12345, 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'latitude': 505430, 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'longitude': 1423412
    <br> 
} <br>

## Baza danych
Do przechowywania danych została użyta baza H2 <br> 

### Struktura bazy
Baza danych składa się z dwóch tabel: Device oraz Position. <br> 
Tabela Device odpowiada za zarejestrowanie urządzeń, których ID są podawane przy wprowadzaniu pozycji. <br> 

#### Pola w tabeli Device to:
- id - automatycznie generowane id nowego urządzenia będące kluczem głównym
- devicename - ciąg znaków będący nazwą urządzenia (np. 'JohnsPhone')
- devicetype - ciąg znaków będący typem urządzenia (np. 'phone')

#### Pola w tabeli Position to:
- id - automatycznie generowane id nowej pozycji będące kluczem głównym
- deviceid - id urządzenia, do którego przypisana jest dana pozycja, będące kluczem obcym
- latitude - szerokość geograficzna
- longitude - długość geograficzna

Zakładany format dla długości i szerokości geograficznej to format DD (decimal degrees), przyjmowana jest więc jedynie długość z przedziału -180 do 180 oraz szerokość z przedziału -90 do 90, baza nie dopuszcza do wprowadzenia położenia spoza przedziału.

Ze względu na strukturę bazy możliwe jest wprowadzenie pozycji jedynie dla istniejącego urządzenia, pozycje z 'deviceid' nieznanym dla bazy nie będą traktowane jako poprawne. <br>
Aby usługa traktowała 'deviceid' jako dowolne i zapisywała wszystkie przesłane pozycje należałoby zmienić strukturę bazy, usunąć klucz obcy w tabeli pozycji, jednak usługa jest napisana z założeniem takiej struktury bazy jak przedstawiono powyżej. <br>
Modyfikacja wiązałaby się jedynie z usunięciem części funkcjonalności.

Po uruchomieniu usługi dostęp do bazy można uzyskać wchodząc na adres: http://localhost:8080/h2-console <br>
W celu użycia konsoli należy podać: 
<br>w  polu JDBC URL: jdbc:h2:mem:testdb, 
<br> w polu User Name: sa

Podczas uruchomienia do bazy są wprowadzane przykładowe dane umożliwające przegląd zawartości.
<br> <br>

### Klasy
**Device** oraz **Position** to proste klasy encji odpowiadające encjom w bazie danych.

Z uwagi na fakt, że klasa **Device** posiada listę pozycji, którą nie w każdym przypadku będziemy chcieli widzieć, dodane są również klasy DTO oraz DTOMapper dla Device. Umożliwią one kontrolę przesyłanych danych.

DeviceService - odpowiada za wykonywanie operacji na bazie danych, na tabeli Device, pozwala również na utworzenie listy urządzeń wraz z odpowiadającymi im pozycjami <br>
PositionService - odpowiada za wykonywanie operacji na bazie danych, na tabeli Position

## Endpointy
Usługa zawiera dwa kontrolery: <br>
DeviceController - odpowiadający za obsługę endpointu http://localhost:8080/devices <br>
PositionController - odpowiadający za obsługę endpointu http://localhost:8080/positions

Endpoint **/devices** udostępnia listę urządzeń ze stronicowaniem przy podaniu parametru 'pageIndex', brak podania numeru strony odpowiada wyświetleniu domyśnej strony pierwszej (**pageIndex=0**), rozmiar strony obejmuje trzy urządzenia.

Endpoint **/devices/{id}** udostępnia dane o urządzeniu o podanym id wraz z listą jego zapisanych pozycji.

Endpoint **/devices/positions** udostępnia, podobnie jak endpoint **/devices**, listę urządzeń z podziałem na strony, jednak jest to lista zawierająca też zapisane pozycje dla każdego urządzenia.

Wykonanie **POST** na endpoincie **/devices** spowoduje dodanie nowego urządzenia od bazy danych.
<br>
Przyjmowany format danych: <br><br>
{
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'devicename': 'somedevice', 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'devicetype': 'sometype'
    <br> 
} <br><br>
#### W przypadku podania id podczas POST'a nastąpi jedna z dwóch sytuacji:
- Jeśli nie ustnieje urządzenie o podanym id to urządzenie zostanie dodane, jednak otrzymane id zostanie zignorowane, zamiast tego urządzenie otrzyma pierwsze wolne id, które zostanie podane w odpowiedzi
- Jeśli urządzenie o podanym id istnieje to odpowiedź zostanie zwrócona z kodem 403 Forbidden, a urządzenie nie zostanie dodane

Endpoint **/positions** udostępnia listę pozycji ze stronicowaniem przy podaniu parametru 'pageIndex', brak podania numeru strony odpowiada wyświetleniu domyśnej strony pierwszej (**pageIndex=0**), rozmiar strony obejmuje dziesięć pozycji.

Endpoint **/positions/{id}** udostępnia dane o pozycji o podanym id.

Wykonanie **POST** na endpoincie **/positions** spowoduje dodanie nowej pozycji od bazy danych.
<br>
Przyjmowany format danych: <br><br>
{
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'deviceid': 12345, 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'latitude': 50.5430, 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;
    'longitude': 142.3412
    <br> 
} <br><br>

- W przypadku podania id podczas POST'a nastąpi sytuacja analogiczna jak w przypadku POST'a na endpoint /devices. <br>
- W przypadku podania deviceid, którego nie ma w bazie danych, zostanie zwrócona odpowiedź z kodem 404 Not Found, a pozycja nie zostanie dodana.
- W przypadku podania szerokości lub długości geograficznej spoza dopuszczalnego przedziału (długość od -180 do 180, szerokość od -90 do 90) zostanie zwrócona odpowiedź z kodem 400 Bad Request, a pozycja nie zostanie dodana.

