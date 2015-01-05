//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2015 Adrián Romero Corchado.
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

import com.adr.datasql.link.DataLinkException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author adrian
 * @param <T>
 */
public abstract class Kind<T> {

    public final static Kind<Number> INT = new KindINT();
    public final static Kind<String> STRING = new KindSTRING();
    public final static Kind<Number> DOUBLE = new KindDOUBLE();
    public final static Kind<BigDecimal> DECIMAL = new KindDECIMAL();
    public final static Kind<Boolean> BOOLEAN = new KindBOOLEAN();
    public final static Kind<Date> TIMESTAMP = new KindTIMESTAMP();
    public final static Kind<Date> DATE = new KindDATE();
    public final static Kind<Date> TIME = new KindTIME();
    public final static Kind<byte[]> BYTEA = new KindBYTEA();
    public final static Kind<Object> OBJECT = new KindOBJECT();

    public abstract T get(KindResults read, String name) throws DataLinkException;
    public abstract T get(KindResults read, int index) throws DataLinkException;
    public abstract void set(KindParameters write, String name, T value) throws DataLinkException;
    public abstract void set(KindParameters write, int index, T value) throws DataLinkException;
    public abstract String _formatISO(T value) throws KindException;
    public final String formatISO(T value) throws KindException {
        return value == null ? "" : _formatISO(value);
    }
    public abstract T _parseISO(String value) throws KindException;
    public final T parseISO(String value) throws KindException {
        return value == null || value.equals("") ? null : _parseISO(value);
    }    
    
    private static final DateFormat dateISO = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat timeISO = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final DateFormat datetimeISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");     
    
    public static final Kind<?> valueOf(String kind) {
        if ("INT".equals(kind)) {
            return Kind.INT;
        } else if ("STRING".equals(kind)) {
            return Kind.STRING;
        } else if ("DOUBLE".equals(kind)) {
            return Kind.DOUBLE;
        } else if ("DECIMAL".equals(kind)) {
            return Kind.DECIMAL;
        } else if ("BOOLEAN".equals(kind)) {
            return Kind.BOOLEAN;
        } else if ("TIMESTAMP".equals(kind)) {
            return Kind.TIMESTAMP;
        } else if ("DATE".equals(kind)) {
            return Kind.DATE;
        } else if ("TIME".equals(kind)) {
            return Kind.TIME;
        } else if ("BYTEA".equals(kind)) {
            return Kind.BYTEA;
        } else if ("OBJECT".equals(kind)) {
            return Kind.OBJECT;            
        } else {
            throw new RuntimeException("Cannot get Kind for value " + kind);
        }
    }

    private static final class KindINT extends Kind<Number> {
        @Override
        public Number get(KindResults read, String name) throws DataLinkException {
            return read.getInt(name);
        }
        @Override
        public Number get(KindResults read, int index) throws DataLinkException {
            return read.getInt(index);
        }
        @Override
        public void set(KindParameters write, String name, Number value) throws DataLinkException {
            write.setInt(name, value == null ? null : value.intValue());
        }
        @Override
        public void set(KindParameters write, int index, Number value) throws DataLinkException {
            write.setInt(index, value == null ? null : value.intValue());
        }
        @Override
        public String _formatISO(Number value) throws KindException {
            return Long.toString(value.longValue());
        }
        @Override
        public Number _parseISO(String value) throws KindException {          
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                throw new KindException(ex);
            }
        }
        @Override
        public String toString() {
            return "Kind.INT";
        }       
    }

    private static final class KindSTRING extends Kind<String> {
        @Override
        public String get(KindResults read, int index) throws DataLinkException {
            return read.getString(index);
        }
        @Override
        public String get(KindResults read, String name) throws DataLinkException {
            return read.getString(name);
        }
        @Override
        public void set(KindParameters write, String name, String value) throws DataLinkException {
            write.setString(name, value);
        }
        @Override
        public void set(KindParameters write, int index, String value) throws DataLinkException {
            write.setString(index, value);
        }
        @Override
        public String _formatISO(String value) throws KindException {
            return value;
        }
        @Override
        public String _parseISO(String value) throws KindException {
            return value;
        }         
        @Override
        public String toString() {
            return "Kind.STRING";
        }          
    }

    private static final class KindDOUBLE extends Kind<Number> {
        @Override
        public Number get(KindResults read, String name) throws DataLinkException {
            return read.getDouble(name);
        }
        @Override
        public Number get(KindResults read, int index) throws DataLinkException {
            return read.getDouble(index);
        }
        @Override
        public void set(KindParameters write, String name, Number value) throws DataLinkException {
            write.setDouble(name, value == null ? null : value.doubleValue());
        }
        @Override
        public void set(KindParameters write, int index, Number value) throws DataLinkException {
            write.setDouble(index, value == null ? null : value.doubleValue());
        }
        @Override
        public String _formatISO(Number value) throws KindException {
            return Double.toString(value.doubleValue());
        }
        @Override
        public Number _parseISO(String value) throws KindException {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                throw new KindException(ex);
            }
        }          
        @Override
        public String toString() {
            return "Kind.DOUBLE";
        }          
    }

    private static final class KindDECIMAL extends Kind<BigDecimal> {
        @Override
        public BigDecimal get(KindResults read, String name) throws DataLinkException {
            return read.getBigDecimal(name);
        }
        @Override
        public BigDecimal get(KindResults read, int index) throws DataLinkException {
            return read.getBigDecimal(index);
        }
        @Override
        public void set(KindParameters write, String name, BigDecimal value) throws DataLinkException {
            write.setBigDecimal(name, value);
        }
        @Override
        public void set(KindParameters write, int index, BigDecimal value) throws DataLinkException {
            write.setBigDecimal(index, value);
        }
        @Override
        public String _formatISO(BigDecimal value) throws KindException {
            return ((BigDecimal) value).toString();
        }
        @Override
        public BigDecimal _parseISO(String value) throws KindException {
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException ex) {
                throw new KindException(ex);
            }
        }         
        @Override
        public String toString() {
            return "Kind.DECIMAL";
        }          
    }

    private static final class KindBOOLEAN extends Kind<Boolean> {
        @Override
        public Boolean get(KindResults read, String name) throws DataLinkException {
            return read.getBoolean(name);
        }
        @Override
        public Boolean get(KindResults read, int index) throws DataLinkException {
            return read.getBoolean(index);
        }
        @Override
        public void set(KindParameters write, String name, Boolean value) throws DataLinkException {
            write.setBoolean(name, value);
        }
        @Override
         public void set(KindParameters write, int index, Boolean value) throws DataLinkException {
            write.setBoolean(index, value);
        }
        @Override
        public String _formatISO(Boolean value) throws KindException {
            return value.toString();
        }
        @Override
        public Boolean _parseISO(String value) throws KindException {
            return Boolean.valueOf(value);
        }             
        @Override
        public String toString() {
            return "Kind.BOOLEAN";
        }           
    }

    private static final class KindTIMESTAMP extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws DataLinkException {
            return read.getTimestamp(name);
        }
        @Override
        public Date get(KindResults read, int index) throws DataLinkException {
            return read.getTimestamp(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws DataLinkException {
            write.setTimestamp(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws DataLinkException {
            write.setTimestamp(index, value);
        }
        @Override
        public String _formatISO(Date value) throws KindException {          
            return datetimeISO.format(value);
        }
        @Override
        public Date _parseISO(String value) throws KindException {        
            try {
                return datetimeISO.parse(value);
            } catch (ParseException e) {
                throw new KindException(e);                
            }
        }          
        @Override
        public String toString() {
            return "Kind.TIMESTAMP";
        }        
    }  
    
    private static final class KindDATE extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws DataLinkException {
            return read.getDate(name);
        }
        @Override
        public Date get(KindResults read, int index) throws DataLinkException {
            return read.getDate(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws DataLinkException {
            write.setDate(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws DataLinkException {
            write.setDate(index, value);
        }
        @Override
        public String _formatISO(Date value) throws KindException {          
            return dateISO.format(value);
        }
        @Override
        public Date _parseISO(String value) throws KindException {        
            try {
                return dateISO.parse(value);
            } catch (ParseException e) {
                throw new KindException(e);                
            }
        }          
        @Override
        public String toString() {
            return "Kind.DATE";
        }          
    }
    
    private static final class KindTIME extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws DataLinkException {
            return read.getTime(name);
        }
        @Override
        public Date get(KindResults read, int index) throws DataLinkException {
            return read.getTime(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws DataLinkException {
            write.setTime(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws DataLinkException {
            write.setTime(index, value);
        }
        @Override
        public String _formatISO(Date value) throws KindException {          
            return timeISO.format(value);
        }
        @Override
        public Date _parseISO(String value) throws KindException {        
            try {
                return timeISO.parse(value);
            } catch (ParseException e) {
                throw new KindException(e);                
            }
        }          
        @Override
        public String toString() {
            return "Kind.TIME";
        }          
    }

    private static final class KindBYTEA extends Kind<byte[]> {
        @Override
        public byte[] get(KindResults read, String name) throws DataLinkException {
            return read.getBytes(name);
        }
        @Override
        public byte[] get(KindResults read, int index) throws DataLinkException {
            return read.getBytes(index);
        }
        @Override
        public void set(KindParameters write, String name, byte[] value) throws DataLinkException {
            write.setBytes(name, value);
        }
        @Override
        public void set(KindParameters write, int index, byte[] value) throws DataLinkException {
            write.setBytes(index, value);
        }
        @Override
        public String _formatISO(byte[] value) throws KindException {          
            return Base64.getEncoder().encodeToString(value);
        }
        @Override
        public byte[] _parseISO(String value) throws KindException {           
            try {
                return Base64.getDecoder().decode(value);
            } catch(IllegalArgumentException e) {
                throw new KindException(e);
            }

        }         
        @Override
        public String toString() {
            return "Kind.BYTEA";
        }          
    }

    private static final class KindOBJECT extends Kind<Object> {
        @Override
        public Object get(KindResults read, String name) throws DataLinkException {
            return read.getObject(name);
        }
        @Override
        public Object get(KindResults read, int index) throws DataLinkException {
            return read.getObject(index);
        }
        @Override
        public void set(KindParameters write, String name, Object value) throws DataLinkException {
            write.setObject(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Object value) throws DataLinkException {
            write.setObject(index, value);
        }
        @Override
        public String _formatISO(Object value) throws KindException {          
            throw new UnsupportedOperationException("Cannot parse using Kind.OBJECT");
        }
        @Override
        public Object _parseISO(String value) throws KindException {           
            throw new UnsupportedOperationException("Cannot parse using Kind.OBJECT");
        }         
        @Override
        public String toString() {
            return "Kind.OBJECT";
        }          
    }        
}
