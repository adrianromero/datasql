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

/**
 *
 * @author adrian
 */
public class ResultsArray implements Results<Object[]> {

    private Kind[] kinds;

    public ResultsArray(Kind... kinds) {
        this.kinds = kinds;
    }

    @Override
    public Object[] read(KindResults kr) throws SQLException {

        Object[] result = new Object[kinds.length];

        for(int i = 0; i < kinds.length; i++) {
            result[i] = kinds[i].get(kr, i + 1);
        }
        return result;
    }
}
