//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2011 Adrián Romero Corchado.
//
//    This file is part of Data SQL
//
//    Data SQL is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Data SQL is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Task Executor. If not, see <http://www.gnu.org/licenses/>.

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
