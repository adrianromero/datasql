/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adr.datasql.meta;

/**
 *
 * @author adrian
 */
public class FieldOrder {
    
    public static enum Sort {
        ASC(" ASC"),
        DESC(" DESC");
        
        private final String sql;
        Sort(String sql) {
            this.sql = sql;
        }
        public String toSQL() {
            return sql;
        }       
    }
    
    private final Field field;
    private final Sort sort;
    
    public FieldOrder(Field field, Sort sort) {
        this.field = field;
        this.sort = sort;
    }
    public Field getField() {
        return field;
    }
    public Sort getSort() {
        return sort;
    }    
}
