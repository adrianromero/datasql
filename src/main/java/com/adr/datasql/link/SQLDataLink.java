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

package com.adr.datasql.link;

import com.adr.datasql.Command;
import com.adr.datasql.KindParameters;
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
class SQLDataLink implements DataLink {
    
    private static final Logger logger = Logger.getLogger(SQLDataLink.class.getName());  
    private final Connection c;
    
    SQLDataLink(Connection c) {
        this.c = c;
    }
    
    @Override
    public final <P> int exec(Command command, Parameters<P> parameters, P params) throws DataLinkException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }      
    }   
    
    @Override
    public final <R,P> R find(Command command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new SQLKindResults(resultset);
                
                if (resultset.next()) {
                    return results.read(kr);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataLinkException(ex);            
        }
    }
    
    @Override
    public final <R,P> List<R> query(Command command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", command);

        try (PreparedStatement stmt = c.prepareStatement(command.getCommand())) {
            KindParameters kp = new SQLKindParameters(stmt, command.getParamNames());

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new SQLKindResults(resultset);
                
                List<R> l = new ArrayList<>();
                while (resultset.next()) {
                    l.add(results.read(kr));
                }
                return l;
            }
        } catch (SQLException ex) {
            throw new DataLinkException(ex);             
        }
    }
    
    @Override
    public final void close() throws DataLinkException {
        try {
            c.close();
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
}
