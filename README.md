# Таксопарк

Лабораторна робота 1. Консольний додаток на Java.

Ієрархія легкових автомобілів (`Sedan`, `Hatchback`, `Minivan` успадковуються
від абстрактного `Car`), таксопарк, вартість автопарку, сортування за
витратою палива, пошук авто в діапазоні швидкості.

## Структура

```
src/main/java/com/taxipark
├── Main.java            точка входу
├── model                Car, Sedan, Hatchback, Minivan
├── io                   CarReader, FileCarReader, CarParser
├── service              TaxiPark
└── ui                   ConsoleMenu
```

Автомобілі завантажуються з файлу `cars.csv`.
Формат рядка: `TYPE;brand;model;price;fuel;maxSpeed;extra`
(`extra`: об'єм багажника для SEDAN, кількість дверей для HATCHBACK,
кількість місць для MINIVAN).

## Запуск

Потрібні JDK 17+ і Maven.

```
mvn test                  # запуск тестів (JUnit 4 + Mockito)
mvn package
java -jar target/taxi-park-1.0.jar [шлях до файлу]
```
