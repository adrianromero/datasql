//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrián Romero Corchado.
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

package com.adr.datasql.adaptor.sql;

import com.adr.datasql.meta.CommandSQL;
import com.adr.datasql.KindParameters;
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.link.CommandType;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.meta.CommandEntityDelete;
import com.adr.datasql.meta.CommandEntityGet;
import com.adr.datasql.meta.CommandEntityInsert;
import com.adr.datasql.meta.CommandEntityList;
import com.adr.datasql.meta.CommandEntityUpdate;
import com.adr.datasql.meta.CommandGeneric;
import com.adr.datasql.meta.StatementOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class SQLDataLink extends DataLink {
    
    private static final Logger logger = Logger.getLogger(SQLDataLink.class.getName());  
    private Connection c;
    private Map<String, SQLView> viewsmap;
    
    public SQLDataLink init(Connection c, Map<String, SQLView> viewsmap) {
        this.c = c;
        this.viewsmap = viewsmap;
        return this;
    }
    
    @Override
    public <P> int exec(CommandEntityInsert command, Parameters<P> parameters, P params) throws DataLinkException {
        return exec(buildSQLCommand(command), parameters, params);        
    }
    @Override
    public <P> int exec(CommandEntityUpdate command, Parameters<P> parameters, P params) throws DataLinkException {
        return exec(buildSQLCommand(command), parameters, params);        
    }
    @Override
    public <P> int exec(CommandEntityDelete command, Parameters<P> parameters, P params) throws DataLinkException {
        return exec(buildSQLCommand(command), parameters, params);    
    }
    @Override
    public <P> int exec(CommandGeneric command, Parameters<P> parameters, P params) throws DataLinkException {
        if (command.getType() == CommandType.SQLNAMED) {
            return exec(new CommandSQLNamed(command.getCommand()), parameters, params);
        } else {
            return super.exec(command, parameters, params); // call parent.
        }
    }    
    @Override
    public <P> int exec(CommandSQL command, Parameters<P> parameters, P params) throws DataLinkException {
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }     
    }
    @Override    
    public <R, P> R find(CommandEntityGet command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException { 
        return find(buildSQLCommand(command), results, parameters, params);
    }  
    @Override    
    public <R, P> R find(CommandEntityList command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {        
        return find(buildSQLCommand(command), results, parameters, params);
    }  
    @Override
    public <R, P> R find(CommandGeneric command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        if (command.getType() == CommandType.SQLNAMED) {
            return find(new CommandSQLNamed(command.getCommand()), results, parameters, params);
        } else {
            return super.find(command, results, parameters, params); // call parent.
        }
    }        
    @Override
    public <R, P> R find(CommandSQL command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new SQLKindResults(resultset);

                if (resultset.next()) {
                    return results.read(kr);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataLinkException(ex);            
        }        
    }    
    @Override    
    public <R, P> List<R> query(CommandEntityList command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        return query(buildSQLCommand(command), results, parameters, params);
    }
    @Override
    public <R, P> List<R> query(CommandGeneric command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        if (command.getType() == CommandType.SQLNAMED) {
            return query(new CommandSQLNamed(command.getCommand()), results, parameters, params);
        } else {
            return super.query(command, results, parameters, params); // call parent.
        }
    }        
    @Override
    public <R, P> List<R> query(CommandSQL command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new SQLKindResults(resultset);

                List<R> l = new ArrayList<>();
                while (resultset.next()) {
                    l.add(results.read(kr));
                }
                return l;
            }
        } catch (SQLException ex) {
            throw new DataLinkException(ex);             
        }        
    }    

    @Override
    public final void close() throws DataLinkException {
        try {
            c.close();
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    
    private String getTableName(String viewname) {
        SQLView v = viewsmap.get(viewname);
        return v == null ? viewname : v.getTable();
    }
   
    private String getSentence(String viewname) {
        SQLView v = viewsmap.get(viewname);
        return (v == null ? viewname : "(" + v.getSentence() + ")") + " TABLE_ALIAS";
    }
       
    private CommandSQL buildSQLCommand(CommandEntityInsert command) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<>();
        
        sentence.append("INSERT INTO ");
        sentence.append(getTableName(command.getName()));
        sentence.append("(");
               
        boolean filter = false;
        for (String f: command.getFields()) {
            sentence.append(filter ? ", " : "");
            sentence.append(f);

            values.append(filter ? ", ?": "?");
            fieldslist.add(f);

            filter = true;
        }  
        
        sentence.append(") VALUES (");
        sentence.append(values);
        sentence.append(")");
            
        return new CommandSQL(sentence.toString(), fieldslist.toArray(new String[fieldslist.size()]));        
    }
    
    private CommandSQL buildSQLCommand(CommandEntityDelete command) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder sentencefilter = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<>();
        
        sentence.append("DELETE FROM ");
        sentence.append(getTableName(command.getName()));
        
        for (String f: command.getKeys()) {
                sentencefilter.append(sentencefilter.length() == 0 ? " WHERE " : " AND ");
                sentencefilter.append(f);
                sentencefilter.append(" = ?");
                keyfields.add(f);
        }
        sentence.append(sentencefilter);
            
        return new CommandSQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));          
    }
    
    private CommandSQL buildSQLCommand(CommandEntityUpdate command) {
        
        StringBuilder sentence = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<String>();
        
        sentence.append("UPDATE ");
        sentence.append(getTableName(command.getName()));
               
        boolean filter = false;
        for (String f: command.getFields()) {
            sentence.append(filter ? ", " : " SET ");
            sentence.append(f);
            sentence.append(" = ?");    
            keyfields.add(f);
            filter = true;
        }  
        
        filter = false;
        for (String f: command.getKeys()) {
            sentence.append(filter ? " AND " : " WHERE ");
            sentence.append(f);
            sentence.append(" = ?");
            keyfields.add(f);
            filter = true;
        }  
            
        return new CommandSQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));         
    }
    
    private CommandSQL buildSQLCommand(CommandEntityGet command) {
        return buildSQLCommand(new CommandEntityList(command.getName(), command.getFields(), command.getKeys(), null));
    }

    private CommandSQL buildSQLCommand(CommandEntityList command) {
        
        StringBuilder sqlsent = new StringBuilder();
        List<String> fieldslist = new ArrayList<>();
        
        sqlsent.append("SELECT ");
        boolean comma = false;
        for (String f: command.getFields()) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                comma = true;       
            }
            sqlsent.append(f); 
        }    
        
        sqlsent.append(" FROM ");       
        sqlsent.append(getSentence(command.getName()));
        
        // WHERE CLAUSE
        if (command.getCriteria() != null) {
            comma = false;
            for (String f: command.getCriteria()) {
                if (comma) {
                    sqlsent.append(" AND ");
                } else {
                    sqlsent.append(" WHERE ");
                    comma = true;
                }
                
                String realname;
                if (f.endsWith("_LIKE")) {
                    realname = f.substring(0, f.length() - 5);
                    sqlsent.append(realname);
                    sqlsent.append(" LIKE ? {escape '$'}");
                    fieldslist.add(f);                      
                } else {
                    sqlsent.append(f);
                    sqlsent.append(" = ?");
                    fieldslist.add(f);    
                }
            }
        }
        
        // ORDER BY CLAUSE
        if (command.getOrder() != null) {
            comma = false;
            for (StatementOrder o: command.getOrder()) {
                if (comma) {
                    sqlsent.append(", ");
                } else {
                    sqlsent.append(" ORDER BY ");
                    comma = true;
                }           
                sqlsent.append(o.getName());
                sqlsent.append(o.getOrder() == StatementOrder.Order.ASC ? " ASC" : " DESC");               
            }
        }

        // build statement
        return new CommandSQL(sqlsent.toString(), fieldslist.toArray(new String[fieldslist.size()]));           
    }
}
