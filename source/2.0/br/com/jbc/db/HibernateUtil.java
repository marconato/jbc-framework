package br.com.jbc.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Fábrica de sessão hibernate que lê informações do arquivo hibernate.cfg que deverá 
 * estar na raiz do projeto que estiver usando este framework
 * @author Rodrigo Marconato
 */
public class HibernateUtil {
    
    private static final String HIBERNATE_PROPERTIES_FILE_NAME = "hibernate.properties";

    private SessionFactory factory;
    
    private static HibernateUtil hibernateUtil; 

    protected HibernateUtil(){      
            
        try {
            Configuration configuration = new Configuration().configure();
            Properties hibernateProperties = this.initConfigFileProperty();
            if (hibernateProperties != null){
                configuration.setProperties(hibernateProperties);
            }       
            ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            
            ServiceRegistry serviceRegistry = registry.buildServiceRegistry();                    
            this.factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static HibernateUtil getInstance(){
        if (hibernateUtil == null){
            hibernateUtil = new HibernateUtil();
        }
        return hibernateUtil;        
    }
    
    private Properties initConfigFileProperty() throws Exception{
        Properties hibernateProperties = new Properties();
        
         try {
            InputStream in = new FileInputStream(HibernateUtil.HIBERNATE_PROPERTIES_FILE_NAME);
            hibernateProperties.load(in);
            in.close();
            return hibernateProperties;
        } catch (FileNotFoundException ex) {
            // Do nothing
            return null;
        }
    }

    /**
     * Abre e retorna a Sessão Hibernate
     * @return Session
     */
    public Session getSession() {
        return this.factory.openSession();
    }
    
}