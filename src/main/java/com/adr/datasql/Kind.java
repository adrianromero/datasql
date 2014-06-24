//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2014 Adrián Romero Corchado.
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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
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

    public abstract T get(KindResults read, String name) throws SQLException;
    public abstract T get(KindResults read, int index) throws SQLException;
    public abstract void set(KindParameters write, String name, T value) throws SQLException;
    public abstract void set(KindParameters write, int index, T value) throws SQLException;
    
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

    public static final Kind<?> getKind(int type) {
        switch (type) {
            case Types.INTEGER:
            case Types.BIGINT:
            case Types.SMALLINT:
            case Types.TINYINT:
                return Kind.INT;
            case Types.BIT:
            case Types.BOOLEAN:
                return Kind.BOOLEAN;
            case Types.DECIMAL:
            case Types.NUMERIC:                
                return Kind.DECIMAL;
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL:
                return Kind.DOUBLE;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.CLOB:
                return Kind.STRING;
            case Types.DATE:
                return Kind.DATE;                
            case Types.TIME:
                return Kind.TIME;                
            case Types.TIMESTAMP:
                return Kind.TIMESTAMP;
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
                return Kind.BYTEA;
            case Types.ARRAY:                    
            case Types.DATALINK:
            case Types.DISTINCT:
            case Types.JAVA_OBJECT:
            case Types.NULL:
            case Types.OTHER:
            case Types.REF:
            case Types.STRUCT:
            default:
                return Kind.OBJECT;
        }
    }

    private static final class KindINT extends Kind<Number> {
        @Override
        public Number get(KindResults read, String name) throws SQLException {
            return read.getInt(name);
        }
        @Override
        public Number get(KindResults read, int index) throws SQLException {
            return read.getInt(index);
        }
        @Override
        public void set(KindParameters write, String name, Number value) throws SQLException {
            write.setInt(name, value == null ? null : value.intValue());
        }
        @Override
        public void set(KindParameters write, int index, Number value) throws SQLException {
            write.setInt(index, value == null ? null : value.intValue());
        }
        @Override
        public String toString() {
            return "Kind.INT";
        }       
    }

    private static final class KindSTRING extends Kind<String> {
        @Override
        public String get(KindResults read, int index) throws SQLException {
            return read.getString(index);
        }
        @Override
        public String get(KindResults read, String name) throws SQLException {
            return read.getString(name);
        }
        @Override
        public void set(KindParameters write, String name, String value) throws SQLException {
            write.setString(name, value);
        }
        @Override
        public void set(KindParameters write, int index, String value) throws SQLException {
            write.setString(index, value);
        }
        @Override
        public String toString() {
            return "Kind.STRING";
        }          
    }

    private static final class KindDOUBLE extends Kind<Number> {
        @Override
        public Number get(KindResults read, String name) throws SQLException {
            return read.getDouble(name);
        }
        @Override
        public Number get(KindResults read, int index) throws SQLException {
            return read.getDouble(index);
        }
        @Override
        public void set(KindParameters write, String name, Number value) throws SQLException {
            write.setDouble(name, value == null ? null : value.doubleValue());
        }
        @Override
        public void set(KindParameters write, int index, Number value) throws SQLException {
            write.setDouble(index, value == null ? null : value.doubleValue());
        }
        @Override
        public String toString() {
            return "Kind.DOUBLE";
        }          
    }

    private static final class KindDECIMAL extends Kind<BigDecimal> {
        @Override
        public BigDecimal get(KindResults read, String name) throws SQLException {
            return read.getBigDecimal(name);
        }
        @Override
        public BigDecimal get(KindResults read, int index) throws SQLException {
            return read.getBigDecimal(index);
        }
        @Override
        public void set(KindParameters write, String name, BigDecimal value) throws SQLException {
            write.setBigDecimal(name, value);
        }
        @Override
        public void set(KindParameters write, int index, BigDecimal value) throws SQLException {
            write.setBigDecimal(index, value);
        }
        @Override
        public String toString() {
            return "Kind.DECIMAL";
        }          
    }

    private static final class KindBOOLEAN extends Kind<Boolean> {
        @Override
        public Boolean get(KindResults read, String name) throws SQLException {
            return read.getBoolean(name);
        }
        @Override
        public Boolean get(KindResults read, int index) throws SQLException {
            return read.getBoolean(index);
        }
        @Override
        public void set(KindParameters write, String name, Boolean value) throws SQLException {
            write.setBoolean(name, value);
        }
        @Override
         public void set(KindParameters write, int index, Boolean value) throws SQLException {
            write.setBoolean(index, value);
        }
        @Override
        public String toString() {
            return "Kind.BOOLEAN";
        }           
    }

    private static final class KindTIMESTAMP extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws SQLException {
            return read.getTimestamp(name);
        }
        @Override
        public Date get(KindResults read, int index) throws SQLException {
            return read.getTimestamp(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws SQLException {
            write.setTimestamp(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws SQLException {
            write.setTimestamp(index, value);
        }
        @Override
        public String toString() {
            return "Kind.TIMESTAMP";
        }        
    }  
    
    private static final class KindDATE extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws SQLException {
            return read.getDate(name);
        }
        @Override
        public Date get(KindResults read, int index) throws SQLException {
            return read.getDate(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws SQLException {
            write.setDate(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws SQLException {
            write.setDate(index, value);
        }
        @Override
        public String toString() {
            return "Kind.DATE";
        }          
    }
    
    private static final class KindTIME extends Kind<Date> {
        @Override
        public Date get(KindResults read, String name) throws SQLException {
            return read.getTime(name);
        }
        @Override
        public Date get(KindResults read, int index) throws SQLException {
            return read.getTime(index);
        }
        @Override
        public void set(KindParameters write, String name, Date value) throws SQLException {
            write.setTime(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Date value) throws SQLException {
            write.setTime(index, value);
        }
        @Override
        public String toString() {
            return "Kind.TIME";
        }          
    }

    private static final class KindBYTEA extends Kind<byte[]> {
        @Override
        public byte[] get(KindResults read, String name) throws SQLException {
            return read.getBytes(name);
        }
        @Override
        public byte[] get(KindResults read, int index) throws SQLException {
            return read.getBytes(index);
        }
        @Override
        public void set(KindParameters write, String name, byte[] value) throws SQLException {
            write.setBytes(name, value);
        }
        @Override
        public void set(KindParameters write, int index, byte[] value) throws SQLException {
            write.setBytes(index, value);
        }
        @Override
        public String toString() {
            return "Kind.BYTEA";
        }          
    }

    private static final class KindOBJECT extends Kind<Object> {
        @Override
        public Object get(KindResults read, String name) throws SQLException {
            return read.getObject(name);
        }
        @Override
        public Object get(KindResults read, int index) throws SQLException {
            return read.getObject(index);
        }
        @Override
        public void set(KindParameters write, String name, Object value) throws SQLException {
            write.setObject(name, value);
        }
        @Override
        public void set(KindParameters write, int index, Object value) throws SQLException {
            write.setObject(index, value);
        }
        @Override
        public String toString() {
            return "Kind.OBJECT";
        }          
    }        
}
