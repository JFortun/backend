<h1 align="center">
   <div>Backend POC</div>
</h1>

## Table of Contents

- [Overview](#overview)
- [Requisites](#requisites)
- [Run the app](#run-the-app)

## Overview

This repository contains a backend POC using Spring Boot, WebFlux and Data

## Requisites

Backend is built with:

- Maven 3.9.6 (included in the wrapper)
- [Java JDK 21](https://adoptium.net/)
- [Spring Web Flux](https://docs.spring.io/spring-framework/reference/web-reactive.html)
- [Spring Data R2DBC](https://docs.spring.io/spring-data/relational/reference/r2dbc.html)

## Install and test the artifact

```shell
./mvnw clean install
```

## Run the app

```shell
./mvnw spring-boot:run
```

## Test the app

```shell
curl -X GET -H "Content-type: application/json" "http://localhost:8080/prices?requestDateTime=2020-06-14T10:00:00&productId=35455&brandId=1"
```
