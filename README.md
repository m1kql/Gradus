# Gradus

[![Build](https://github.com/yak-fumblepack/Gradus/actions/workflows/build.yml/badge.svg)](https://github.com/yak-fumblepack/Gradus/actions/workflows/build.yml)

![](gradus.gif)

A web application that merges note taking, assignments, and autograding into one platform for all students. 

Built with Spring boot as part of my ICS3U1 culminating.

Javadocs were not written for most methods as they were very self-explanatory, only methods inside of the controllers folder contains javadoc comments.


**Proposal submission**

```
My final project will be a web application that serves as a tool for students to organise their work

In the web application, there will be variables that temporarily store uuids, names, emails etc for processing, before being passed to the database then back to the server. In addition, arrays will be used for storing temporary data that we might fetch from APIs or from Todo lists. Conditionals are used for validation, most likely during login, and also for regular actions such as button clicking, and things such as that. File reading and writing can be for when the user wants to export their notes/todo lists as a pdf, and file reading can be for when the user would like the application to read their config file for their profile. 

Methods will definitely be present in a variety of places. Style and documentation will be maintained through a compiled javadoc as well as the README.md file. If needed, a web version of the docs can be created for setup, maintenance and installation.

I will need to learn about the Java Spring framework, though firebase auth and mysql database connection should be fairly easy to apply to Java. In addition, I will need to know the different libraries in Java for the different things I will be doing, such as auth, possible APIs, markdown to pdf, and also database stuff.

By tomorrow I will have completed a short tutorial on basic Java spring MVC, by Monday I will start creating the boilerplate for my application. Then by the start of winter break, login functionality will be included (through firebase auth using student tdsb email) with full validation, and then within the next few days it'll be more or less designed with a couple of pages and profile pages. Probably by the first week, profile loading, todo lists etc will be implemented. Then, most likely grades and those sort of things will be added. If permitting, elevated users can be added with the ability to create class codes and assign students assignments etc. Though that will possibly require some time to implement. 
```

## Running this application

```shell
$ git clone git@github.com:yak-fumblepack/Gradus.git
$ cd gradus
```

Using maven:

```shell
$ mvn spring-boot:run
```

View on [localhost:8080](http://localhost:8080)

# License

This project is licensed under Apache 2.0, refer to [LICENSE](LICENSE) for more details.