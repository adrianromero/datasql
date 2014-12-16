/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adr.datasql;

/**
 *
 * @author adrian
 */
public class KindException extends Exception {

    /**
     * Creates a new instance of <code>KindException</code> without detail
     * message.
     */
    public KindException() {
    }

    public KindException(String msg) {
        super(msg);
    }
    public KindException(Throwable t) {
        super(t);
    }   
}
