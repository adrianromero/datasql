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

package com.adr.datasql;

import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author adrian
 */
public abstract class Kind<T> {

    public final static Kind<Number> INT = new KindINT();
    public final static Kind<String> STRING = new KindSTRING();
    public final static Kind<Number> DOUBLE = new KindDOUBLE();
    public final static Kind<Boolean> BOOLEAN = new KindBOOLEAN();
    public final static Kind<Date> TIMESTAMP = new KindTIMESTAMP();
//    public final static Kind<String> DATETIME = new KindDATETIME(); // JS Date Time
    public final static Kind<Date> DATE = new KindDATE();
    public final static Kind<byte[]> BYTEA = new KindBYTEA();
    public final static Kind<String> BASE64 = new KindBASE64();

    public abstract T get(KindResults read, String name) throws SQLException;
    public abstract T get(KindResults read, int index) throws SQLException;
    public abstract void set(KindParameters write, String name, T value) throws SQLException;
    public abstract void set(KindParameters write, int index, T value) throws SQLException;

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
    
//    private static final class KindDATETIME extends Kind<String> {
//        @Override
//        public String get(KindResults read, String name) throws SQLException {
//            return read.getTimestamp(name);
//        }
//        @Override
//        public String get(KindResults read, int index) throws SQLException {
//            return read.getTimestamp(index);
//        }
//        @Override
//        public void set(KindParameters write, String name, String value) throws SQLException {
//            write.setTimestamp(name, value);
//        }
//        @Override
//        public void set(KindParameters write, int index, String value) throws SQLException {
//            write.setTimestamp(index, value);
//        }
//    }
    

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
    
}
