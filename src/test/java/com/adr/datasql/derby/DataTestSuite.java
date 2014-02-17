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

package com.adr.datasql.derby;

import com.adr.datasql.orm.ORMSession;
import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author adrian
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    com.adr.datasql.tests.QueriesTest.class, 
    com.adr.datasql.tests.NSQLTest.class,
    com.adr.datasql.tests.ORMTest.class,
    com.adr.datasql.tests.JsonORMTest.class,
    com.adr.datasql.samples.SimpleTest.class,
    com.adr.datasql.samples.ObjectPojoTest.class   
})
public class DataTestSuite {
    
    private static EmbeddedConnectionPoolDataSource cpds;   
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        
        FileUtils.deleteDirectory(new File("mytestdb")); // DROP DATABASE mytestdb
        
        cpds = new EmbeddedConnectionPoolDataSource(); 
        cpds.setDatabaseName("mytestdb");
        cpds.setCreateDatabase("create");             
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (cpds != null) {        
            try {
                cpds.setShutdownDatabase("shutdown");
                cpds.getConnection();
                throw new RuntimeException("Cannot shutdown database.");
            } catch (SQLException ex) {
                if (!"08006".equals(ex.getSQLState())) {
                    throw new RuntimeException("Database shutdown error.", ex);
                }
            } finally {
                cpds = null;
            }
        }        
    }
    
    public static DataSource getDataSource() {
        return cpds; 
    }
    
    public static ORMSession newSession() throws SQLException {
        return new ORMSession(cpds.getConnection()); 
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
