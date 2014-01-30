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

import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 * @param <T>
 */
public abstract class Kind<T> {

    public final static Kind<Number> INT = new KindINT();
    public final static Kind<String> STRING = new KindSTRING();
    public final static Kind<Number> DOUBLE = new KindDOUBLE();
    public final static Kind<Boolean> BOOLEAN = new KindBOOLEAN();
    public final static Kind<Date> TIMESTAMP = new KindTIMESTAMP();
    public final static Kind<Date> DATE = new KindDATE();
    public final static Kind<String> ISODATETIME = new KindISODATETIME();
    public final static Kind<String> ISODATE = new KindISODATE();
    public final static Kind<String> ISOTIME = new KindISOTIME();
    public final static Kind<byte[]> BYTEA = new KindBYTEA();
    public final static Kind<String> BASE64 = new KindBASE64();
    public final static Kind<Object> OBJECT = new KindOBJECT();

    public abstract T get(KindResults read, String name) throws SQLException;
    public abstract T get(KindResults read, int index) throws SQLException;
    public abstract void set(KindParameters write, String name, T value) throws SQLException;
    public abstract void set(KindParameters write, int index, T value) throws SQLException;
    
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
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL:
            case Types.NUMERIC:
                return Kind.DOUBLE;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.CLOB:
                return Kind.STRING;
            case Types.DATE:
            case Types.TIME:
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
    }
    
    private static final class KindISODATETIME extends Kind<String> {
        
        private static DateFormat isodatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private static DateFormat isodate = new SimpleDateFormat("yyyy-MM-dd");
        
        public String format(Date d) {
            return d == null ? null : isodatetime.format(d);
        }
        
        public Date parse(String s) throws ParseException {
            if (s == null) {
                return null;
            } else {
                try {
                    return isodatetime.parse(s);
                } catch (ParseException ex) {
                    return isodate.parse(s);                   
                }
            }   
        }

        @Override
        public String get(KindResults read, String name) throws SQLException {
            return format(read.getTimestamp(name));
        }
        @Override
        public String get(KindResults read, int index) throws SQLException {
            return format(read.getTimestamp(index));
        }
        @Override
        public void set(KindParameters write, String name, String value) throws SQLException {
            try {
                write.setTimestamp(name, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
        }
        @Override
        public void set(KindParameters write, int index, String value) throws SQLException {
            try {
                write.setTimestamp(index, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
        }
    }
    
    private static final class KindISODATE extends Kind<String> {
        
        private static DateFormat isodate = new SimpleDateFormat("yyyy-MM-dd");
        
        public String format(Date d) {
            return d == null ? null : isodate.format(d);
        }
        
        public Date parse(String s) throws ParseException {
            return s == null ? null : isodate.parse(s); 
        }

        @Override
        public String get(KindResults read, String name) throws SQLException {
            return format(read.getDate(name));
        }
        @Override
        public String get(KindResults read, int index) throws SQLException {
            return format(read.getDate(index));
        }
        @Override
        public void set(KindParameters write, String name, String value) throws SQLException {
            try {
                write.setDate(name, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
        }
        @Override
        public void set(KindParameters write, int index, String value) throws SQLException {
            try {
                write.setDate(index, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
        }
    }    
    
    private static final class KindISOTIME extends Kind<String> {
        
        private static DateFormat isotime = new SimpleDateFormat("HH:mm:ss.SSS");
        
        public String format(Date d) {
            return d == null ? null : isotime.format(d);
        }
        
        public Date parse(String s) throws ParseException {
            return s == null ? null : isotime.parse(s); 
        }

        @Override
        public String get(KindResults read, String name) throws SQLException {
            return format(read.getTime(name));
        }
        @Override
        public String get(KindResults read, int index) throws SQLException {
            return format(read.getTime(index));
        }
        @Override
        public void set(KindParameters write, String name, String value) throws SQLException {
            try {
                write.setTime(name, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
        }
        @Override
        public void set(KindParameters write, int index, String value) throws SQLException {
            try {
                write.setTime(index, parse(value));
            } catch (ParseException ex) {
                throw new SQLException(ex);
            }
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
    }

    private static final class KindBASE64 extends Kind<String> {
        @Override
        public String get(KindResults read, String name) throws SQLException {
            return EncodeUtils.encode(read.getBytes(name));
        }
        @Override
        public String get(KindResults read, int index) throws SQLException {
            return EncodeUtils.encode(read.getBytes(index));
        }
        @Override
        public void set(KindParameters write, String name, String value) throws SQLException {
            write.setBytes(name, EncodeUtils.decode(value));
        }
        @Override
        public void set(KindParameters write, int index, String value) throws SQLException {
            write.setBytes(index, EncodeUtils.decode(value));
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
    }        
}
