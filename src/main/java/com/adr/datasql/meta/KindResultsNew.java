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
//     limitations under the License.n the template in the editor.

package com.adr.datasql.meta;

import com.adr.datasql.KindResults;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.link.DataLinkException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @author adrian
 */
public final class KindResultsNew implements KindResults {
    
    private final MetaData[] projection;
    private final MetaData[] keys;  
    private final Set<String> keysset;
    private final Set<String> projectionset;
    private final Set<Integer> keysint;
    private final Set<Integer> projectionint;
    
    private final Random r = new Random();
    
    public KindResultsNew(MetaData[] projection, MetaData[] keys) {
        this.projection = projection;    
        this.projectionset = Arrays.stream(projection).map(m -> m.getName()).collect(Collectors.toSet());
        this.keys = keys;
        this.keysset = Arrays.stream(keys).map(m -> m.getName()).collect(Collectors.toSet());
        
        this.projectionint = new HashSet<>();
        this.keysint = new HashSet<>();
        for (int i = 0; i < projection.length; i++) {
            projectionint.add(i);
            if (keysset.contains(projection[i].getName())) {
                keysint.add(i);
            }
        }        
    }
    
    private <T> T get(String columnName, Supplier<T> s) throws DataLinkException {
        if (keysset.contains(columnName)) {
            return s.get();
        }
        if (projectionset.contains(columnName)) {
            return null;
        }
        throw new DataLinkException("Cannot find column: " + columnName);
    } 
    
    private <T> T get(int columnIndex, Supplier<T> s) throws DataLinkException {
        if (keysint.contains(columnIndex)) {
            return s.get();
        }
        if (projectionint.contains(columnIndex)) {
            return null;
        }
        throw new DataLinkException("Cannot find column: " + columnIndex);
    } 
    
    @Override
    public Integer getInt(int columnIndex) throws DataLinkException {
        return get(columnIndex, () -> r.nextInt());
    }
    @Override
    public Integer getInt(String columnName) throws DataLinkException {
        return get(columnName, () -> r.nextInt());
    }
    @Override
    public String getString(int columnIndex) throws DataLinkException {
        return get(columnIndex, () -> UUID.randomUUID().toString().replaceAll("-", ""));
    }
    @Override
    public String getString(String columnName) throws DataLinkException {
        return get(columnName, () -> UUID.randomUUID().toString().replaceAll("-", ""));
    }
    @Override
    public Double getDouble(int columnIndex) throws DataLinkException {
        return get(columnIndex, () -> r.nextDouble());
    }
    @Override
    public Double getDouble(String columnName) throws DataLinkException {
        return get(columnName, () -> r.nextDouble());
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws DataLinkException {
        return get(columnIndex, () -> new BigDecimal(r.nextDouble()));
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws DataLinkException {
        return get(columnName, () -> new BigDecimal(r.nextDouble()));
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public Boolean getBoolean(String columnName) throws DataLinkException {        
        return null;
    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws DataLinkException {
        return null;
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public java.util.Date getDate(String columnName) throws DataLinkException {
        return null;
    }
    @Override
    public java.util.Date getTime(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public java.util.Date getTime(String columnName) throws DataLinkException {
        return null;
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public byte[] getBytes(String columnName) throws DataLinkException {
        return null;
    }
    @Override
    public Object getObject(int columnIndex) throws DataLinkException {
        return null;
    }
    @Override
    public Object getObject(String columnName) throws DataLinkException {
        return null;
    }
    
    @Override
    public MetaData[] getMetaData() throws DataLinkException {
        return projection;        
    }    
}