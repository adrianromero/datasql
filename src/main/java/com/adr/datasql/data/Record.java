/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adr.datasql.data;

import com.adr.datasql.KindParameters;
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.meta.MetaData;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public abstract class Record<P> implements Parameters<P>, Results<P> {
    
    private final MetaData[] metadatas;
    
    public Record(MetaData[] metadatas) {
        this.metadatas = metadatas;
    }
    
    public abstract Object getValue(MetaData f, P param) throws SQLException;
    public abstract void setValue(MetaData f, P param, Object value) throws SQLException;
    public abstract P create() throws SQLException;

    @Override
    public void write(KindParameters dp, P param) throws SQLException {
        for (MetaData md : metadatas) {           
            md.getKind().set(dp, md.getName(), getValue(md, param));              
        }
    }     

    @Override
    public P read(KindResults dp) throws SQLException {
        P param = create();
        for (MetaData md : metadatas) {    
            setValue(md, param, md.getKind().get(dp, md.getName()));              
        }
        return param;
    }    
}
