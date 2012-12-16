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

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author adrian
 */
public class EncodeUtils {
    
    private EncodeUtils() {
    }
 
    public static byte[] decode(String base64) {

        try {
            return base64 == null ? null : Base64.decodeBase64(base64.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String encode(byte[] raw) {
        try {
            return raw == null ? null : new String(Base64.encodeBase64(raw), "ASCII");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] decodeHEX(String hex) {
        try {
            return hex == null ? null : Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException ex) {
            return null;
        }
    }

    public static String encodeHEX(byte[] raw) {
        return raw == null ? null : new String(Hex.encodeHex(raw));
    }   
}
