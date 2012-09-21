package modelotradicional;

import java.io.Serializable;

public class PessoaFacade implements Serializable {

    private Transaction factory;
    private PessoaDao dao;

    public PessoaFacade() {
        factory = Transaction.getInstance();
        dao = new PessoaDao(factory.getSession());
    }

    public Boolean insert(Pessoa p) {
        if (factory.hasTransaction()) {
            return dao.insert(p);
        } else {
            return false;
        }
    }
}
