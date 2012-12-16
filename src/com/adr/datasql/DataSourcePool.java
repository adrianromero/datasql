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
