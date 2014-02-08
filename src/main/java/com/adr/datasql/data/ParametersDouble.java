//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014 Adrián Romero Corchado.
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

package com.adr.datasql.data;

import com.adr.datasql.Kind;
import com.adr.datasql.KindParameters;
import com.adr.datasql.Parameters;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public class ParametersDouble implements Parameters<Number> {

    public static final Parameters<Number> INSTANCE = new ParametersDouble();

    private ParametersDouble() {
    }
        
    @Override
    public void write(KindParameters dp, Number param) throws SQLException {
        Kind.DOUBLE.set(dp, 1, param);
    }
}