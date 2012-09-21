/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelotradicional;

/**
 *
 * @author Rodrigo Marconato
 */
public class Main {
    
    public static void main(String[] args) {
        PessoaController controller = new PessoaController();
        Pessoa p = new Pessoa();
        p.setNome("Marconato");
        try {
            controller.insert(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
