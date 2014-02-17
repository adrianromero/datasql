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

```java
try (Session session = new Session(getDataSource().getConnection())) {
    // Here goes your database code
}
```

### Executing simple statements

In the following example parameter number and types are inspected from sentence and results metadata provided by JDBC.

```java
try (Session session = new Session(getDataSource().getConnection())) {
    // Insert a record
    session.exec(new QueryArray("insert into testtable(id, name, line, amount) values (?, ?, ?, ?)"),
        "record one", "name one", 10, 65.0);
    // Find a record
    Object[] record = session.find(new QueryArray("select id, name, line, amount from testtable where name = ?"), "name one");
    // List records
    List<Object[]> records = session.list(new QueryArray("select id, name, line, amount from testtable"));
} 
```

But you can also especify the type of parameters

```java
try (Session session = new Session(getDataSource().getConnection())) {
    // Find a record specifying types
    ProcFind<Object[], Object[]> selectTestTable = new QueryArray(
        "select id, name, line, amount from testtable where name = ?")
        .setParameters(Kind.STRING)
        .setResults(Kind.STRING, Kind.STRING, Kind.INT, Kind.DOUBLE);       
    Object[] result = session.find(selectTestTable, "name one");
}  
```

Also you have predefined classes for primitive results and parameters.

```java 
try (Session session = new Session(getDataSource().getConnection())) {
    // Count records
    ProcFind<Number, Number> countTestTable = new Query(
        "select count(*) from testtable where amount > ?")
        .setParameters(ParametersDouble.INSTANCE)
        .setResults(ResultsInteger.INSTANCE);       
    int countrows = session.find(countTestTable, 10.0).intValue();   
}  
```

### Working with POJO objects

Consider the following declaration:

```java
package com.adr.datasql.samples;
public class ObjectPojo {
    // ObjectPojo Configuration   
    public final static Data<ObjectPojo> DATA = new DataPojo(new Definition(
        "com_adr_datasql_samples_ObjectPojo",
        new FieldKey("id", Kind.STRING),
        new Field("name", Kind.STRING),            
        new Field("line", Kind.INT),
        new Field("amount", Kind.DOUBLE)));
    // Fields    
    private String id;
    private String name;
    private Integer line;
    private Double amount;

    // Access methods
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getLine() { return line; }
    public void setLine(Integer line) { this.line = line; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
```

The important part in this declaration is the static field `DATA`. If present, Data SQL will use it for persistence using its capabilities to work with POJO objects. In this case it asumes that `TestPojo` is persisted in a table created the following way:
            
```java
try (ORMSession session = new ORMSession(getDataSource().getConnection())) { 
    // Create table using Derby syntax. Porting to other database engines will easy
    session.exec(new QueryArray(
        "create table com_adr_datasql_samples_ObjectPojo (" +
        "id varchar(32) not null primary key, " +
        "name varchar(1024), " +                        
        "line integer, " +
        "amount double precision)")); 
}
```

You can insert / retrieve an instance of a new `TestPojo` instance using the following code. You also have more operations to delete, upsert, list, etc.. Take into account that now we are using an `ORMSession` instead of a `Session`.
            
```java
try (ORMSession session = new ORMSession(getDataSource().getConnection())) { 
    // Defining a new instance of our ObjectPojo
    ObjectPojo pojo = new ObjectPojo();
    pojo.setId("pojoid");
    pojo.setName("pojoname");
    pojo.setLine(10);
    pojo.setAmount(50.0);
    // Insert
    session.insert(pojo);  
    // Get an instance
    ObjectPojo returnpojo = session.get(ObjectPojo.class, "pojoid");
} 
```

License
=======

Apache License, Version 2.0.
