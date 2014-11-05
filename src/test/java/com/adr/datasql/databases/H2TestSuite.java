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

package com.adr.datasql.databases;

import com.adr.datasql.orm.ORMSession;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
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
    com.adr.datasql.tests.JsonORMTest.class
})
public class H2TestSuite {
    
    private static JdbcConnectionPool cpds;   
    
    @BeforeClass
    public static void setUpClass() throws Exception {       
        cpds = JdbcConnectionPool.create("jdbc:h2:~/h2testdb", "sa", "");     
        DataBase.setDataSource(cpds);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (cpds != null) {        
            cpds.dispose();
            cpds = null;
            DataBase.setDataSource(cpds);
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
