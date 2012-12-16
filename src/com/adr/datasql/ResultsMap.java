//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2011 Adrián Romero Corchado.
//
//    This file is part of Data SQL
//
//    Data SQL is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Data SQL is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Task Executor. If not, see <http://www.gnu.org/licenses/>.

package com.adr.datasql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class ResultsMap implements Results<Map<String, Object>> {

    private KindValue<String>[] values;

    public ResultsMap(KindValue<String> ... values) {
        this.values = values;
    }

    @Override
    public Map<String, Object> read(KindResults kr) throws SQLException {

        Map<String,Object> result = new HashMap<String, Object>();

        for(KindValue<String> v : values) {
            result.put(v.getValue(), v.getKind().get(kr, v.getValue()));
        }

        return result;
    }
}
