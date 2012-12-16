//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012 Adrián Romero Corchado.
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

package com.adr.datasql;

import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author adrian
 */
public class DataSourceBasic {

    public static BasicDataSource setupDataSource(String className, String connectURI, String username, String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(className);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(connectURI);
        return ds;
    }

    public static void printDataSourceStats(BasicDataSource ds) {
        System.out.println("NumActive: " + ds.getNumActive());
        System.out.println("NumIdle: " + ds.getNumIdle());
    }

    public static void shutdownDataSource(BasicDataSource ds) throws SQLException {
        ds.close();
    }
}
