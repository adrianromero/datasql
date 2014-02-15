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

### Creating and using a `Session` object from a `DataSource` object:

This code uses Java 7 syntax. Because the `Session` object implements `Autocloseable`, the `try` block will automatically close the connection requested to the `Datasource` object.

        try (Session session = new Session(datasource.getConnection())) {
            // Here goes your database code
        }

### Executing simple statements

In the following example parameter number and types are inspected from sentence and results metadata provided by JDBC.

        try (Session session = new Session(datasource.getConnection())) {
            // Insert a record
            session.exec(new QueryArray("INSERT INTO TESTTABLE(ID, CODE, NAME, ISGOOD) VALUES (?, ?, ?, ?)"),
                "record one", 12.4, "name one", true);
            // Find a record
            Object[] record = session.find(new QueryArray("SELECT ID, CODE, NAME, ISGOOD FROM TESTTABLE WHERE NAME = ?"), "name one");
            // List records
            List<Object[]> records = session.list(new QueryArray("SELECT ID, CODE, NAME, ISGOOD FROM TESTTABLE"));
        }

But you can also especify the type of parameters
            
        try (Session session = new Session(datasource.getConnection())) {
            // Find a record specifying types
            ProcFind<Object[], Object[]> selectTestTable = new QueryArray(
                "SELECT ID, CODE, NAME, ISGOOD FROM TESTTABLE WHERE NAME = ?")
                .setParameters(Kind.STRING)
                .setResults(Kind.STRING, Kind.DOUBLE, Kind.STRING, Kind.BOOLEAN);       
            Object[] result = session.find(selectTestTable, "name one");   
        }

Also you have predefined classes for primitive results and parameters.
            
        try (Session session = new Session(datasource.getConnection())) {
            // Count records
            ProcFind<Number, Number> countTestTable = new QueryArray(
                "SELECT COUNT(*) FROM TESTTABLE WHERE CODE > ?")
                .setParameters(ParametersDouble.INSTANCE)
                .setResults(ParametersInteger.INSTANCE);       
            int countrows = session.find(countTestTable, 10.0).intValue();   
        }

License
=======

Apache License, Version 2.0.
