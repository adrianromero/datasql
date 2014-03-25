/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adr.datasql.orm;

import com.adr.datasql.meta.Field;
import com.adr.datasql.StatementFind;
import com.adr.datasql.Query;
import com.adr.datasql.data.ParametersArray;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <R>
 */
public class StatementGet<R> implements StatementFind<R, Object[]> {
        
    private final Query<R, Object[]> queryfind;
    
    public StatementGet(Data<R> data) {
        
        Field[] fieldskey = data.getDefinition().getFieldsKey();
        
        queryfind = new Query<R, Object[]>(data.getDefinition().getStatementSelect(fieldskey))
                .setResults(data)
                .setParameters(new ParametersArray(fieldskey));
    }

    @Override
    public R find(Connection c, Object[] params) throws SQLException {
        return queryfind.find(c, params);
    }
}