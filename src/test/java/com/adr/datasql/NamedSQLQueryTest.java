/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adr.datasql;

import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class NamedSQLQueryTest {
    
    public NamedSQLQueryTest() {
    }

    @Test
    public void testQueries() throws ParseException {
        
        Assert.assertEquals(
                "select * from table where name = ? and surname = ?[name, surname]", 
                new NamedSQLQuery("select * from table where name = :name and surname = :surname").toString());
        Assert.assertEquals(
                "select * from table where date > ? and date < ?[mydate, mydate]", 
                new NamedSQLQuery("select * from table where date > :mydate and date < :mydate").toString());
        Assert.assertEquals(
                "select * from table where date > ':mydate' and date < ?[mydate]", 
                new NamedSQLQuery("select * from table where date > ':mydate' and date < :mydate").toString());
        try {
            new NamedSQLQuery("select * from table where date > :mydate and date < :").toString();
        } catch (ParseException e) {
           Assert.assertEquals(e.getMessage(), 53, e.getErrorOffset());
        }

    
    }
}
