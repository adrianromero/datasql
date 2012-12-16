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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 *
 * @author adrian
 */
public class DataSourcePool {

    private static final Logger logger = Logger.getLogger(DataSourcePool.class.getName());

    public static DataSource setupDataSource(String className, String connectURI, String username, String password) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        GenericObjectPool.Config config = new GenericObjectPool.Config();
//        config.maxActive = 150;
//        config.maxIdle = 100;
//        config.minIdle = 30;
//        config.maxWait = 1000;

        ObjectPool connectionPool = new GenericObjectPool(null, config);
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, username, password);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

        return dataSource;
    }
}
