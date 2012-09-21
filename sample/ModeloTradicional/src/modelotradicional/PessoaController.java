package modelotradicional;

import java.util.List;

import java.io.Serializable;

public class PessoaController implements Serializable {

    private PessoaFacade facade;

    public PessoaController() {
        this.facade = new PessoaFacade();
    }

    public PessoaFacade getFacade() {
        return facade;
    }

    public Boolean insert(Pessoa p) throws Exception {

        try {
            Transaction.getInstance().beginTransaction();
            if (this.facade.insert(p)) {
                Transaction.getInstance().commit();
                return true;
            } else {
                Transaction.getInstance().rollback();
                return false;
            }
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }
}