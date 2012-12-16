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

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author adrian
 */
public class DataSourceJNDI {

    private static final Logger logger = Logger.getLogger(DataSourceJNDI.class.getName());

    public static DataSource setupDataSource(String jndiurl) {
        try {          
            Context initContext = new InitialContext();
            return (DataSource) initContext.lookup(jndiurl);
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static DataSource setupDataSource(String jndiurl, Hashtable env) {
        try {
            Context initContext = new InitialContext(env);
            return (DataSource) initContext.lookup(jndiurl);
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }
}
