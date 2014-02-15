Data SQL
========

Data SQL is a simple JDBC wrapper focussed on simplicity and agility. With Data SQL writting code to access relational data from java becomes easy and the code more clear.

Benefits
========

* Ligth JDBC wrapper with small footprint.
* Execute SQL statements with just one line of code.
* Very flexible to define parameters and results of SQL statements.
* Micro ORM capabilities with support for POJO objects, JSON and also Maps or Object arrays.
* Simply to integrate with your existing code that access database.

Usage
=====

* Creating and using a `Session` object from a `DataSource` object:

        try (Session session = new Session(datasource.getConnection())) {
            // Here goes your database code
        }

This code uses Java 7 syntax. Because the `Session` object implements `Autocloseable`, the `try` block will automatically close the connection requested to the `Datasource` object.
 
* Executing simple statements

        try (Session session = new Session(datasource.getConnection())) {
            // Insert a record
            session.exec(new QueryArray("INSERT INTO TESTTABLE(ID, CODE, NAME) VALUES (?, ?, ?)"),
                "record one", "code one", "name one");
            // Find a record
            Object[] record = session.find(new QueryArray("SELECT ID, CODE, NAME FROM TESTTABLE WHERE CODE = ?"), "code one");
            // list records
            List<Object[]> records = session.list(new QueryArray("SELECT ID, CODE, NAME FROM TESTTABLE"));
        }

In this example parameter number and types are inspected from sentence and results metadata provided by JDBC.

License
=======

Apache License, Version 2.0.
