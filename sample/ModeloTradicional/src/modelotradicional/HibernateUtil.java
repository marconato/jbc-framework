package modelotradicional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Fábrica de sessão hibernate que lê informações do arquivo hibernate.cfg que deverá 
 * estar na raiz do projeto que estiver usando este framework
 * @author Rodrigo Marconato
 */
public class HibernateUtil {
    

    private static SessionFactory factory;
    
    static{
        try{
            factory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Abre e retorna a Sessão Hibernate
     * @return Session
     */
    public static Session getSession() {
        return factory.openSession();
    }
    
    

}
