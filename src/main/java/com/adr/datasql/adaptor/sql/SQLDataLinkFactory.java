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

package com.adr.datasql.adaptor.sql;

import com.adr.datasql.Kind;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.link.DataLinkFactory;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author adrian
 */
public class SQLDataLinkFactory implements DataLinkFactory {
    
    private DataSource ds = null;
    private final Map<String, SQLView> viewsmap = new HashMap<>();
    
    public SQLDataLinkFactory init(DataSource ds, SQLView ... views) {
        this.ds = ds;
        for (SQLView v : views) {
            addView(v);
        } 
        return this;
    }
    
    protected void addView(SQLView v) {
        viewsmap.put(v.getName(), v);
    }

    @Override
    public final DataLink getDataLink() throws DataLinkException {
        try {
            SQLDataLink dl = buildSQLDataLink();
            dl.init(ds.getConnection(), viewsmap);
            return dl;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    
    protected SQLDataLink buildSQLDataLink() {
        return new SQLDataLink();    
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
}
