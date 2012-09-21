package modelotradicional;

import org.hibernate.Session;
import java.io.Serializable;

public class PessoaDao implements Serializable {

    private final Session session;

    public PessoaDao(Session session) {
        this.session = session;
    }

    public Boolean insert(Pessoa p) {

        this.session.merge(p);
        this.session.flush();
        this.session.evict(p);

        return true;
    }
}
