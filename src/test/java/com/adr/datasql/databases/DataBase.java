/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adr.datasql.databases;

import com.adr.datasql.orm.ORMSession;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public class DataBase {
    
    private static javax.sql.DataSource cpds; 
    
    public static void setDataSource(javax.sql.DataSource cpds) {
        DataBase.cpds = cpds;
    }
    
    public static javax.sql.DataSource getDataSource() {
        return cpds; 
    }
    
    public static ORMSession newSession() throws SQLException {
        return new ORMSession(cpds); 
    }    
}
