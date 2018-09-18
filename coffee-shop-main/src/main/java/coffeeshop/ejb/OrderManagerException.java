/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.ejb;

public class OrderManagerException extends Exception{
    public OrderManagerException() {
    }

    public OrderManagerException(String msg) {
        super(msg);
    }
}
