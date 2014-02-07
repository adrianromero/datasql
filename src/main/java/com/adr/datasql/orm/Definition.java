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

package com.adr.datasql.orm;

import com.adr.datasql.Query;
import java.util.ArrayList;

/**
 *
 * @author adrian
 */
public class Definition {
    
    private final String tablename;
    private final Field[] fields;
    
    public Definition(String tablename, Field... fields) {
        this.tablename = tablename;
        this.fields = fields;
    }
    
    public String getTableName() {
        return tablename;
    }
    
    public String getClassName() {
        return tablename.replaceAll("_", ".");
    }
 
    public Field[] getFields() {
        return fields;
    }
 
    public Field[] getFieldsKey() {
        ArrayList<Field> keys = new ArrayList<Field>();
        for (Field f: fields) {
            if (f.isKey()) {
                keys.add(f);
            }
        }        
        return keys.toArray(new Field[keys.size()]);
    }

    public Query getStatementInsert() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("INSERT INTO ");
        sql.append(getTableName());
        sql.append("(");
               
        boolean filter = false;
        for (Field f: fields) {
            sql.append(filter ? ", " : "");
            sql.append(f.getName());

            values.append(filter ? ", ?": "?");
            fieldslist.add(f.getName());

            filter = true;
        }  
        
        sql.append(") VALUES (");
        sql.append(values);
        sql.append(")");
            
        return new Query(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    } 
    
    public Query getStatementUpdate() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlfilter = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("UPDATE ");
        sql.append(getTableName());
               
        boolean filter = false;
        for (Field f: fields) {
            // UPDATE
            sql.append(filter ? ", " : " SET ");
            sql.append(f.getName());
            sql.append(" = ?");    
            fieldslist.add(f.getName());
            filter = true;
            // WHERE
            if (f.isKey()) {
                sqlfilter.append(sqlfilter.length() == 0 ? " WHERE " : " AND ");
                sqlfilter.append(f.getName());
                sqlfilter.append(" = ?");
                fieldslist.add(f.getName());
            }
        }  
        sql.append(sqlfilter);
            
        return new Query(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    }
    
    public Query getStatementDelete() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlfilter = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("DELETE FROM ");
        sql.append(getTableName());
        
        for (Field f: fields) {
            if (f.isKey()) {
                sqlfilter.append(sqlfilter.length() == 0 ? " WHERE " : " AND ");
                sqlfilter.append(f.getName());
                sqlfilter.append(" = ?");
                fieldslist.add(f.getName());
            }
        }
        sql.append(sqlfilter);
            
        return new Query(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    } 
    
    public Query getStatementSelect() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlfilter = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("SELECT ");
        boolean filter = false;
        for (Field f: fields) {
            // SELECT
            sql.append(filter ? ", " : "");
            sql.append(f.getName());
            filter = true;           
            // WHERE
            if (f.isKey()) {
                sqlfilter.append(sqlfilter.length() == 0 ? " WHERE " : " AND ");
                sqlfilter.append(f.getName());
                sqlfilter.append(" = ?");
                fieldslist.add(f.getName());
            }
        }     
        
        sql.append(" FROM ");       
        sql.append(getTableName());
        sql.append(sqlfilter);
 
        return new Query(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));        
    }
}
