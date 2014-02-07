/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adr.datasql.orm;

import com.adr.datasql.ProcFind;
import com.adr.datasql.Query;
import com.adr.datasql.Session;
import com.adr.datasql.data.ParametersArray;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <P>
 */
public class GetData<R> implements ProcFind<R, Object[]> {
        
    private final Query<R, Object[]> queryfind;
    
    public GetData(Data<R> data) {
        
        queryfind = data.getDefinition().getStatementSelect();
        queryfind.setResults(data);
        queryfind.setParameters(new ParametersArray(data.getDefinition().getFieldsKey()));
    }

    @Override
    public R find(Session s, Object[] params) throws SQLException {
        return queryfind.find(s, params);
    }
}