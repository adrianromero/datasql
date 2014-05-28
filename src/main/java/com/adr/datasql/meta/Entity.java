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

package com.adr.datasql.meta;

import com.adr.datasql.Query;
import com.adr.datasql.Results;
import com.adr.datasql.SQL;
import com.adr.datasql.StatementQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 *
 * @author adrian
 */
public class Entity implements SourceList {
    
    private final String name;
    private final Field[] fields;
       
    public Entity(String name, Field... fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }
 
    @Override
    public Field[] getMetaDatas() {
        return fields;
    }
    
    @Override
    public String toString() {
        return "Entity {name: " + name + ", fields: " + Arrays.toString(fields) + "}";
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
 
    public Field[] getFields(Set<String> fieldsname) {
        ArrayList<Field> keys = new ArrayList<Field>();
        for (Field f: fields) {
            if (fieldsname.contains(f.getName())) {
                keys.add(f);
            }
        }        
        return keys.toArray(new Field[keys.size()]);
    }
    
    public SQL getStatementInsert() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("INSERT INTO ");
        sql.append(getName());
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
            
        return new SQL(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    } 
    
    public SQL getStatementUpdate() {
        
        StringBuilder sql = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("UPDATE ");
        sql.append(getName());
               
        boolean filter = false;
        for (Field f: fields) {
            sql.append(filter ? ", " : " SET ");
            sql.append(f.getName());
            sql.append(" = ?");    
            fieldslist.add(f.getName());
            filter = true;
        }  
        
        filter = false;
        for (Field f: fields) {
            if (f.isKey()) {
                sql.append(filter ? " AND " : " WHERE ");
                sql.append(f.getName());
                sql.append(" = ?");
                fieldslist.add(f.getName());
                filter = true;
            }
        }  
            
        return new SQL(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    }
    
    public SQL getStatementDelete() {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlfilter = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("DELETE FROM ");
        sql.append(getName());
        
        for (Field f: fields) {
            if (f.isKey()) {
                sqlfilter.append(sqlfilter.length() == 0 ? " WHERE " : " AND ");
                sqlfilter.append(f.getName());
                sqlfilter.append(" = ?");
                fieldslist.add(f.getName());
            }
        }
        sql.append(sqlfilter);
            
        return new SQL(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));
    } 
       
    public SQL getStatementSelect(Field... filterfields) {
        
        StringBuilder sql = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sql.append("SELECT ");
        boolean comma = false;
        for (Field f: fields) {
            if (comma) {
                sql.append(", ");
            } else {
                comma = true;       
            }
            sql.append(f.getName()); 
        }    
        
        sql.append(" FROM ");       
        sql.append(getName());
        
        comma = false;
        for (Field f: filterfields) {
            if (comma) {
                sql.append(" AND ");
            } else {
                sql.append(" WHERE ");
                comma = true;
            }           
            sql.append(f.getName());
            sql.append(" = ?");
            fieldslist.add(f.getName());            
        }

        return new SQL(sql.toString(), fieldslist.toArray(new String[fieldslist.size()]));            
    }
    
    @Override
    public <R, P> StatementQuery<R, P> getStatementFilter(Results<R> results, StatementOrder[] order) {
        
        StringBuilder sqlsent = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sqlsent.append("SELECT ");
        boolean comma = false;
        for (Field f: fields) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                comma = true;       
            }
            sqlsent.append(f.getName()); 
        }    
        
        sqlsent.append(" FROM ");       
        sqlsent.append(getName());
        
//        // WHERE CLAUSE
//        comma = false;
//        for (FilterField f: filterfields) {
//            if (comma) {
//                sqlsent.append(" AND ");
//            } else {
//                sqlsent.append(" WHERE ");
//                comma = true;
//            }           
//            sqlsent.append(f.getName());
//            sqlsent.append(" = ?");
//            fieldslist.add(f.getName());            
//        }
        
        // ORDER BY CLAUSE
        comma = false;
        for (StatementOrder o: order) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                sqlsent.append(" ORDER BY ");
                comma = true;
            }           
            sqlsent.append(o.getName());
            sqlsent.append(o.getSort().toSQL());               
        }

        // build statement
        SQL sql = new SQL(sqlsent.toString(), fieldslist.toArray(new String[fieldslist.size()]));     
        return new Query<R, P>(sql).setResults(results).setParameters(null);
    }
    
//    public static enum Filter {
//        NONE,
//        NULL,
//        NOTNULL,
//        EQUAL,
//        GREATER,
//        LESS,
//        GREATEROREQUAL,
//        LESSOREQUEAL
//    }
//    
//    public static class FilterField {
//        private final Field field;
//        private final Filter filter;
//        public FilterField(Field field, Filter filter) {
//            this.field = field;
//            this.filter = filter;
//        }
//        public Field getField() {
//            return field;
//        }
//        public Filter getFilter() {
//            return filter;
//        }
//    }
     
//    
//    public SQL getStatementCreateTable(Database db) {
//        
//    }
//    
//    public SQL getStatementDropTable(Database db) {
//        
//    }
}

