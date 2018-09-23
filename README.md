# Real Java Coffee shop

A online coffee shop project written in Java EE. [Project Repository](https://github.com/bupt-spirit/coffee-shop).

## Build

To build and run this project, you need:

- GlassFish 5.0 for Java EE 8 API
- JDK lower then 8u152 for SSL
- Maven higher then 3.2.5 for Sass
- MySql 8.0.12
- Proper MySql Connector/J 8.0.12
- A Sass compiler in path

Build:

```sh
mvn package
```

## Deploy

Run ```coffee-shop-main/src/main/resources/coffeeshop.setup/create.sql``` on your database, and a database named coffee_shop will be created.
Add a new data source ```jdbc/CoffeeShop``` to your glassfish.

Deploy the war package in ```coffee-shop-main/target``` to your glassfish server, start then application and login using default admin account - username: admin, password: admin. Switch to data tab, use the button to initialize demo data.

## LICENSE

This project is a group project for the Java EE course of BUPT SSE , you should not use any code from this project in your project of the course.

Any other uses of the codes of the project are licensed under MIT License.
