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
import com.adr.datasql.QueryArray;
import com.adr.datasql.Session;
import com.adr.datasql.orm.ORMSession;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class ObjectPojoTest {
    
    public ObjectPojoTest() {
    }

    private static DataSource getDataSource() {
        return DataTestSuite.getDataSource();
    }  

    @Test
    public void pojoStatements() throws SQLException {
        
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
    }    
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (ORMSession session = new ORMSession(getDataSource().getConnection())) {            
            // Create table using Derby syntax. Porting to other database engines will easy
            session.exec(new QueryArray(
                "create table com_adr_datasql_samples_ObjectPojo (" +
                "id varchar(32) not null primary key, " +
                "name varchar(1024), " +                        
                "line integer, " +
                "amount double precision)")); 
        }
    }    
}
