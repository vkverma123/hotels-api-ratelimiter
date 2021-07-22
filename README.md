Hotel API Rate limit Service
===========================

## Description
Hotel API Rate limit is a Java based spring boot service that is created to provide a rate limiting feature and hosts two APIs to get hotels data by cityName and RoomType.
API contracts are :

GET /city/{cityName}?sortOption=ASC
GET /room/{roomType}?sortOption=DESC

I have used the standard sliding window Algorithm to implement the Rate limit feature.

## Requirements
* Install java 8
* Install maven 3.1.1+
* Install IDE: IntelliJ have the most support, but you are free to use whatever you like.

## Building

#### From IDE (Intellij):

Open as a Maven project and compile.

#### From Command Line:

```
mvn clean install
```

## Running the Application

Run or debug the app with the ```HotelsServiceApplication``` main class at the root of your Java package hierarchy

Open a browser and visit [https://localhost:8080/](https://localhost:8080/) for Swagger documentation.

=====

We can use Apache Jmeter to test the Rate Limit feature of the APIs.

## Tests

I have also added integration tests and unit tests.

## Snapshots

Please refer snapshots in snapshots directory.

## A Sample Successful Response.

`
{
"hotelsResponseList": [
{
"city": "Bangkok",
"hotelId": 1,
"room": "Deluxe",
"price": 1000
},
{
"city": "Amsterdam",
"hotelId": 4,
"room": "Deluxe",
"price": 2200
},
{
"city": "Ashburn",
"hotelId": 7,
"room": "Deluxe",
"price": 1600
},
{
"city": "Bangkok",
"hotelId": 11,
"room": "Deluxe",
"price": 60
},
{
"city": "Ashburn",
"hotelId": 12,
"room": "Deluxe",
"price": 1800
},
{
"city": "Bangkok",
"hotelId": 15,
"room": "Deluxe",
"price": 900
},
{
"city": "Ashburn",
"hotelId": 17,
"room": "Deluxe",
"price": 2800
},
{
"city": "Ashburn",
"hotelId": 21,
"room": "Deluxe",
"price": 7000
},
{
"city": "Amsterdam",
"hotelId": 23,
"room": "Deluxe",
"price": 5000
},
{
"city": "Ashburn",
"hotelId": 25,
"room": "Deluxe",
"price": 1900
},
{
"city": "Amsterdam",
"hotelId": 26,
"room": "Deluxe",
"price": 2300
}
],
"statusCode": 200,
"message": "Success"
}
`

