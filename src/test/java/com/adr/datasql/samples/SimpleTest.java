//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014 Adrián Romero Corchado.
//
//    This file is part of Data SQL
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//     
//         http://www.apache.org/licenses/LICENSE-2.0
//     
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.

package com.adr.datasql.samples;

import com.adr.datasql.derby.DataTestSuite;
import com.adr.datasql.Kind;
import com.adr.datasql.StatementFind;
import com.adr.datasql.Query;
import com.adr.datasql.QueryArray;
import com.adr.datasql.Session;
import com.adr.datasql.data.ParametersDouble;
import com.adr.datasql.data.ResultsInteger;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class SimpleTest {
    
    public SimpleTest() {
    }

    private static DataSource getDataSource() {
        return DataTestSuite.getDataSource();
    }

    @Test
    public void simpleStatements() throws SQLException {
        
        try (Session session = new Session(getDataSource().getConnection())) {
            // Insert a record
            session.exec(new QueryArray("insert into testtable(id, name, line, amount) values (?, ?, ?, ?)"),
                "record one", "name one", 10, 65.0);
            // Find a record
            Object[] record = session.find(new QueryArray("select id, name, line, amount from testtable where name = ?"), "name one");
            // List records
            List<Object[]> records = session.query(new QueryArray("select id, name, line, amount from testtable"));
        }    
    }

    @Test
    public void simpleStatementsTyped() throws SQLException {
        
        try (Session session = new Session(getDataSource().getConnection())) {
            // Find a record specifying types
            StatementFind<Object[], Object[]> selectTestTable = new QueryArray(
                "select id, name, line, amount from testtable where name = ?")
                .setParameters(Kind.STRING)
                .setResults(Kind.STRING, Kind.STRING, Kind.INT, Kind.DOUBLE);       
            Object[] result = session.find(selectTestTable, "name one");
        }    
    }

    @Test
    public void simpleStatementsPrimitives() throws SQLException {
        
        try (Session session = new Session(getDataSource().getConnection())) {
            // Count records
            StatementFind<Number, Number> countTestTable = new Query(
                "select count(*) from testtable where amount > ?")
                .setParameters(ParametersDouble.INSTANCE)
                .setResults(ResultsInteger.INSTANCE);       
            int countrows = session.find(countTestTable, 10.0).intValue();   
        }    
    }       
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = new Session(getDataSource().getConnection())) {        
            // Create table using Derby syntax. Porting to other database engines will easy
            session.exec(new QueryArray(
                "create table testtable (" +
                "id varchar(32) not null primary key, " +
                "name varchar(1024), " +                        
                "line integer, " +
                "amount double precision)")); 
        }
    }      
}
