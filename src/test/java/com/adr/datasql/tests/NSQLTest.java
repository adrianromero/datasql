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

package com.adr.datasql.tests;

import com.adr.datasql.adaptor.sql.CommandSQLNamed;
import com.adr.datasql.link.DataLinkException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class NSQLTest {
    
    public NSQLTest() {
    }

    @Test
    public void testQueries() throws DataLinkException {
        
        Assert.assertEquals("select * from table where name = ? and surname = ?[name, surname]", 
                new CommandSQLNamed("select * from table where name = :name and surname = :surname").toString());
        Assert.assertEquals("select * from table where date > ? and date < ?[mydate, mydate]", 
                new CommandSQLNamed("select * from table where date > :mydate and date < :mydate").toString());
        Assert.assertEquals("select * from table where date > ':mydate' and date < ?[mydate]", 
                new CommandSQLNamed("select * from table where date > ':mydate' and date < :mydate").toString());
        try {
            new CommandSQLNamed("select * from table where date > :mydate and date < :").toString();
        } catch (DataLinkException e) {
           Assert.assertEquals("Error parsing command. Bad identifier. Character: 53", e.getMessage());
        }
    } 
}
