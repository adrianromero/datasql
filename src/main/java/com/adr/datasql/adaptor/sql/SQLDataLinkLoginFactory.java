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

import javax.sql.DataSource;

/**
 *
 * @author adrian
 */
public class SQLDataLinkLoginFactory extends SQLDataLinkFactory {
        
    @Override
    public SQLDataLinkFactory init(DataSource ds, SQLView ... views) {        
       addView(new SQLView(
               "view_permission", 
               "select permission.id, permission.role_id, subject.id as subject_id, subject.name as subject_name, subject.code as subject_code "
                       + "from permission join subject on permission.subject_id = subject.id",
               "permission"));       
       return super.init(ds, views);
    }    
    
    @Override
    protected SQLDataLink buildSQLDataLink() {
        return new SQLDataLinkLogin();    
    }
}
