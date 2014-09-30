package br.com.jbc.db;

import java.io.Serializable;
import org.hibernate.Session;

/**
 * Classe que faz o controle de transacao com o banco de dados
 * @author Rodrigo Leandro Marconato
 */
public class Transaction implements Serializable{

    /**
     * Fabrica de criação de transação
     */
    private static Transaction daoFactory = null;
    
    /**
     * Sessão Hibernate com o banco de dados
     */
    private final Session session;
    
    /**
     * Transação hibernate
     */
    private org.hibernate.Transaction transaction;

    private Transaction() {
        this.session = HibernateUtil.getInstance().getSession();
    }

    
    public Session getSession() {
        return this.session;
    }

    public static Transaction getInstance() {
        if (daoFactory == null) {
            daoFactory = new Transaction();
        }
        return daoFactory;
    }

    /**
     * Inicia uma transação se tiver uma sessão conectada com o banco de dados
     */
    public void beginTransaction() {
        if (this.session.isConnected()) {
            this.transaction = this.session.beginTransaction();
        }
    }

    /**
     * Confirma a transação
     */
    public void commit() {
        this.transaction.commit();
        this.session.clear();
        this.transaction = null;
    }

    /**
     * Verifica de tem transação
     * @return Retorna true se tiver transção ativa e false se não tiver transação ativa
     */
    public boolean hasTransaction() {
        return this.transaction != null;
    }

    /**
     * Desfaz a transação
     */
    public void rollback() {
        this.transaction.rollback();
        this.session.clear();
        this.transaction = null;
    }

    /**
     * Fecha a sessão hibernate
     */
    public void close() {
        this.session.close();
    }
}
