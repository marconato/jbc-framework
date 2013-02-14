package br.com.jbc.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
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
    
    private static String HIBERNATE_PROPERTIES_FILE_NAME = "hibernate.properties";

    static{
        Properties hibernateProperties = new Properties();
        InputStream in;
        try {
            in = new FileInputStream(HibernateUtil.HIBERNATE_PROPERTIES_FILE_NAME);
            hibernateProperties.load(in);
            in.close();
            
            factory = new AnnotationConfiguration().configure().setProperties(hibernateProperties).buildSessionFactory();
            
        } catch (Exception ex) {

            try {
                factory = new AnnotationConfiguration().configure().buildSessionFactory();
            } catch (Throwable e) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + e);
                throw new ExceptionInInitializerError(ex);
            }
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
