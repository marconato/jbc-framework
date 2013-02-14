/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jbc.util;

/**
 *
 * @author Rodrigo Marconato
 */
public class JBCException extends Exception{
    
    public static String SEM_TRANSACAO = "Nao há uma Transação ativa!";
    
    public static String TRANSACAO_DESFEITA = "Transação defeita pois um ou mais registro não foi possível atualizar!";
    
    public JBCException(){
        super();
    }
    
    public JBCException(String message){
        super(message);
    }
}
