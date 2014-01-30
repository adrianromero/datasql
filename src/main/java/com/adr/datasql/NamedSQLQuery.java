//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014 Adrián Romero Corchado.
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrian
 */
public class NamedSQLQuery<R, P> extends Query<R, P> {
    
    private static final char CHAR_ETX = '\u0003';
    
    private static final int STATE_LOOK = 0;
    private static final int STATE_SINGLEQUOTE = 1;
    private static final int STATE_DOUBLEQUOTE = 2;
    private static final int STATE_PARAMETERSTART = 3;
    private static final int STATE_PARAMETERPART = 4;
    
    public NamedSQLQuery(String sql) throws ParseException {
        
        StringBuilder parsedsql = new StringBuilder();
        StringBuilder parametername = new StringBuilder();
        List<String> parsedparams = new ArrayList<String>();
        
        int i = 0;
        int state = STATE_LOOK;
        char c;
        for (;;) {   
            
            c = i < sql.length() ? sql.charAt(i) : CHAR_ETX;
                    
            if (state == STATE_LOOK) {
                if (c == '\'') {
                    parsedsql.append(c);
                    state = STATE_SINGLEQUOTE;
                } else if (c == '\"') {
                    parsedsql.append(c);
                    state = STATE_DOUBLEQUOTE;
                } else if (c == ':') {
                    state = STATE_PARAMETERSTART;
                } else if (c == CHAR_ETX) {
                    this.sql = parsedsql.toString();
                    this.paramnames = parsedparams.toArray(new String[parsedparams.size()]);
                    return;
                } else {
                    parsedsql.append(c);
                }
            } else if (state == STATE_SINGLEQUOTE) {                
                if (c == '\'') {
                    parsedsql.append(c);
                    state = STATE_LOOK;
                } else if (c == CHAR_ETX) {   
                    throw new ParseException("Error parsing sql. Invalid literal.", i);
                } else {
                    parsedsql.append(c);
                }
            } else if (state == STATE_DOUBLEQUOTE) {                
                if (c == '\"') {
                    parsedsql.append(c);
                    state = STATE_LOOK;
                } else if (c == CHAR_ETX) {   
                    throw new ParseException("Error parsing sql. Invalid literal.", i);
                } else {
                    parsedsql.append(c);
                }    
            } else if (state == STATE_PARAMETERSTART) {
                if (c == CHAR_ETX) {
                    throw new ParseException("Error parsing sql. Bad identifier.", i);
                } else if (Character.isJavaIdentifierStart(c)) {
                    parametername = new StringBuilder();
                    parametername.append(c);
                    state = STATE_PARAMETERPART;
                } else {
                    throw new ParseException("Error parsing sql. Bad identifier.", i);
                }
            } else if (state == STATE_PARAMETERPART) {
                if (c == CHAR_ETX) { 
                    parsedsql.append('?');
                    parsedparams.add(parametername.toString());
                    
                    this.sql = parsedsql.toString();
                    this.paramnames = parsedparams.toArray(new String[parsedparams.size()]);
                    return;   
                } else if (Character.isJavaIdentifierPart(c)) {
                    parametername.append(c);
                } else {
                    parsedsql.append('?');
                    parsedsql.append(c);
                    parsedparams.add(parametername.toString());
                    state = STATE_LOOK;
                }
            } else {
                throw new ParseException("Error parsing sql. Invalid state.", i);
            }

            i++;
        }
    }      
}
