---
layout: home
title: Home
nav_order: 1
search_exclude: false
---

# Gradus Documentation
{: .fs-9 }

Unsure about the various functionalities of Gradus? Get started with our documentation.
{: .fs-6 .fw-300 }

[Get started now](#getting-started){: .btn .btn-blue .fs-5 .mb-4 .mb-md-0 .mr-2 } [View it on GitHub](https://github.com/yak-fumblepack/gradus){: .btn .text-blue-000 .fs-5 .mb-4 .mb-md-0 }

## Getting Started

### Dependencies 

This project uses Maven as the build tool. In the `pom.xml` file:

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.thymeleaf.extras</groupId>
	<artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-test</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.commonmark</groupId>
	<artifactId>commonmark</artifactId>
	<version>0.18.1</version>
</dependency>
```

## Running the application

Simply run the main method, no set up for the database is required.

```shell
$ mvn spring-boot:run
```

> **Note:** The database schemas are all lost when the application is terminated because it is in memory only. This is not suitable for a production environment.

Then go to [localhost:8080](http://localhost:8080) to view the application.


## License

This project is licensed under the Apache 2.0 License. Please refer to [LICENSE](https://github.com/yak-fumblepack/Gradus/blob/dev/LICENSE) for more details.