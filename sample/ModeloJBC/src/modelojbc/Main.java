/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelojbc;

import br.com.jbc.controller.Controller;

/**
 *
 * @author Rodrigo Marconato
 */
public class Main {
    
    public static void main(String[] args) {
        Controller<Pessoa> controller = new Controller<Pessoa>();
        try {
            Pessoa p = new Pessoa();
            p.setNome("Rodrigo");
            controller.insert(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
