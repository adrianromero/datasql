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

Getting started
===============

These are few examples how to use DataSQL.

### Creating and using a `Session` object from a `DataSource` object:

This code uses Java 7 syntax. Because the `Session` object implements `Autocloseable`, the `try` block will automatically close the connection requested to the `Datasource` object.

        try (Session session = new Session(datasource.getConnection())) {
            // Here goes your database code
        }

### Executing simple statements

In the following example parameter number and types are inspected from sentence and results metadata provided by JDBC.

        try (Session session = new Session(datasource.getConnection())) {
            // Insert a record
            session.exec(new QueryArray("INSERT INTO TESTTABLE(ID, CODE, NAME, GOOD) VALUES (?, ?, ?, ?)"),
                "record one", 12.4, "name one", true);
            // Find a record
            Object[] record = session.find(new QueryArray("SELECT ID, CODE, NAME, GOOD FROM TESTTABLE WHERE NAME = ?"), "name one");
            // List records
            List<Object[]> records = session.list(new QueryArray("SELECT ID, CODE, NAME, GOOD FROM TESTTABLE"));
        }

But you can also especify the type of parameters
            
        try (Session session = new Session(datasource.getConnection())) {
            // Find a record specifying types
            ProcFind<Object[], Object[]> selectTestTable = new QueryArray(
                "SELECT ID, CODE, NAME, GOOD FROM TESTTABLE WHERE NAME = ?")
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

### Working with POJO objects

Consider the following declaration:

        package com.adr.datasql.samples;
        public class TestPojo {
            // Fields    
            private String id;
            private Double code;
            private String name;
            private Boolean good;
            // TestPojo Configuration   
            public final static Data<SamplePojo> DATA = new DataPojo(new Definition(
                "com_adr_datasql_samples_TestPojo",
                new FieldKey("id", Kind.STRING),
                new Field("code", Kind.DOUBLE),
                new Field("name", Kind.STRING),
                new Field("good", Kind.BOOLEAN));
            // Access methods
            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public Double getCode() { return code; }
            public void setCode(Double code) { this.code = code; }
            public Boolean isGood() { return good; }
            public void setGood(Boolean good) { this.good = good; }
        }

The important part in this declaration is the static field `DATA`. If present, Data SQL will use it for persistence using its capabilities to work with POJO objects. In this case it asumes that `TestPojo` is persisted in a table created the following way:
            
            // Create table using Derby syntax. Porting to other database engines will easy
            session.exec(new QueryArray("create table com_adr_datasql_samples_TestPojo ("
                    + "id varchar(32) not null primary key, "
                    + "code double precision, "
                    + "name varchar(1024), "
                    + "good smallint)"));   

You can insert / retrieve an instance of a new `TestPojo` instance using the following code. You also have more operations to delete, upsert, list, etc.. Take into account that now we are using an `ORMSession` instead of a `Session`.
            
        try (ORMSession session = new Session(datasource.getConnection())) { 
            // Defining a new instance of our TestPojo
            TestPojo pojo = new TestPojo();
            pojo.setId("pojoid");
            pojo.setCode(100.0);
            pojo.setName("pojoname");
            pojo.setGood(true);
            // Insert
            session.insert(pojo);  
            // Get an instance
            SamplePojo returnpojo = session.get(SamplePojo.class, "pojoid");
        }

License
=======

Apache License, Version 2.0.
