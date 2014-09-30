package br.com.jbc.controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import br.com.jbc.util.SearchPaginate;
import br.com.jbc.db.Transaction;
import br.com.jbc.util.JBCException;
import java.io.Serializable;

/**
 * Classe de controle de operacoes que se conectam a camada de banco de dados
 * @author Rodrigo Leandro Marconato
 * @param <T> Value Object de controle
 */
public class Facade<T> implements Serializable{

    protected final Transaction factory;
    protected Dao<T> dao;

    protected Facade() {
        factory = Transaction.getInstance();
        dao = new Dao<T>(factory.getSession());
    }

    /**
     * Insere um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     * @throws ConstraintViolationException
     */
    protected Boolean insert(T obj) throws Exception, ConstraintViolationException {
        if (factory.hasTransaction()) {
            return dao.insert(obj);
        } else {
            throw new JBCException(JBCException.SEM_TRANSACAO);
        }
    }

    /**
     * Insere ou atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    protected Boolean insertOrUpdate(T obj) throws Exception {
        if (factory.hasTransaction()) {
            return dao.insertOrUpdate(obj);
        } else {
            throw new JBCException(JBCException.SEM_TRANSACAO);
        }
    }

    /**
     * Insere um registro em uma determinada tabela e retorna a entidade persistida
     * @param obj Objeto VO de persistência
     * @return O Objeto Persistido
     * @throws Exception
     */
    protected T insertReturnId(T obj) throws Exception, ConstraintViolationException {
        if (factory.hasTransaction()) {
            return dao.insertReturnId(obj);
        } else {
            throw new JBCException(JBCException.SEM_TRANSACAO);
        }
    }

    /**
     * Atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    protected Boolean update(T obj) throws Exception {
        if (factory.hasTransaction()) {
            return dao.update(obj);
        } else {
            throw new JBCException(JBCException.SEM_TRANSACAO);
        }
    }

    /**
     * Elimina um determinado registro pelo Objeto
     * @param obj Objeto VO a ser eliminado contendo a sua chave primária
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    protected Boolean delete(T obj) throws Exception {
        if (factory.hasTransaction()) {
            dao.delete(obj);
            return true;
        } else {
            throw new JBCException(JBCException.SEM_TRANSACAO);
        }
    }

    /**
     * Lista os registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    protected List<T> findList(T filter) throws Exception {
        this.factory.getSession().clear();
        return dao.findList(filter);
    }

    /**
     * Lista os registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    protected List<T> findList(T filter, int searchType) throws Exception {
        this.factory.getSession().clear();
        return dao.findList(filter, searchType);
    }

    /**
     * Retorna um registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    protected T find(T filter) throws Exception {
        this.factory.getSession().clear();
        return dao.find(filter);
    }

    /**
     * Retorna um registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    protected T find(T filter, int searchType) throws Exception {
        this.factory.getSession().clear();
        return dao.find(filter, searchType);
    }

    /**
     * Procura um registro por um ID
     * @param id Chave primária da entidade
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    protected T findById(Class clazz, Number id) throws Exception {

        T t = (T) dao.findById(clazz, id);
        return t;
    }

    /**
     * Busca uma lista de registro por uma condição HQL
     * @param condition Condição HQL
     * @return Lista de objetos encontrados na consulta
     * @throws Exception
     */
    protected List<T> getListByHQLCondition(String condition) throws Exception {
        this.factory.getSession().clear();
        return dao.getListByHQLCondition(condition);
    }

    /**
     * Busca uma lista de registro por uma condição HQL com uma lista de parametros
     * @param condition Condição HQL
     * @param paramList Lista de parametros passada para a Query HQL
     * @param paramListName Nome da lista de parametros
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    protected List<T> getListByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        this.factory.getSession().clear();
        return dao.getListByHQLCondition(condition, paramList, paramListName);
    }

    /**
     * Busca uma lista de registro por uma condição HQL paginada
     * @param condition Condição HQL
     * @param firstResult Número da página que a consulta irá mostrar os resultados
     * @param maxResult Número de registros por página
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    protected List<T> getListByHQLCondition(String condition, int firstResult, int maxResult) throws Exception {
        this.factory.getSession().clear();
        return dao.getListByHQLCondition(condition, firstResult, maxResult);
    }

    /**
     * Busca uma lista de registro por uma condição HQL paginada com uma lista de parametros
     * @param condition Condição HQL
     * @param paramList Lista de parametros passada para a Query HQL
     * @param paramListName Nome da lista de parametros
     * @param firstResult Número da página que a consulta irá mostrar os resultados
     * @param maxResult Número de registros por página
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    protected List<T> getListByHQLCondition(String condition, List paramList, String paramListName, int firstResult, int maxResult) throws Exception {
        this.factory.getSession().clear();
        return dao.getListByHQLCondition(condition, paramList, paramListName, firstResult, maxResult);
    }

    /**
     * Busca um registro por uma condição HQL
     * @param condition Condição HQL
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    protected T getObjectByHQLCondition(String condition) throws Exception {
        this.factory.getSession().clear();
        return dao.getObjectByHQLCondition(condition);
    }

    /**
     * Busca um valor de um registro por uma condição HQL, podendo ser um tipo primitivo, ou
     * qualquer valor que a condição HQL retorne
     * @param condition Condição HQL
     * @return Valor encontrado na consulta
     * @throws Exception
     */
    protected Object getValueByHQLCondition(String condition) throws Exception {
        this.factory.getSession().clear();
        return dao.getValueByHQLCondition(condition);
    }

     /**
     * Busca um valor de um registro por uma condição HQL com uma lista de parametros, podendo ser um tipo primitivo, ou
     * qualquer valor que a condição HQL retorne
     * @param condition Condição HQL
     * @param paramList Lista de parametros passada para a Query HQL
     * @param paramListName Nome da lista de parametros
     * @return Valor encontrado na consulta
     * @throws Exception
     */
    protected Object getValueByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        this.factory.getSession().clear();
        return dao.getValueByHQLCondition(condition, paramList, paramListName);
    }

    /**
     * Realiza uma consulta paginada com filtro passado por parametro
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param firstResult Número da página que a consulta irá mostrar os resultados
     * @param maxResult Número de registros por página
     * @param sortField Nome do atributo da entidade que será ordenado
     * @param orderByType Tipo de ordenação da Lista, os valores devem ser Controller.ORDER_ASC ou Controller.ORDER_DESC
     * @return Componente SearchPaginate contendo a lista páginada e a quantidade total de registros daquela tabela
     * @throws Exception 
     */
    protected SearchPaginate findPaginate(T filter, int firstResult, int maxResult, String sortField, boolean orderByType) throws Exception {

        return dao.findPaginate(filter, firstResult, maxResult, sortField, orderByType);
    }

    /**
     * Retornar o valor máximo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor máximo
     */
    protected Number getMax(T filter, String field){

        return dao.getMax(filter, field);
    }

    /**
     * metodo que retornar o valor minimo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor mínimo
     */
    protected Number getMin(T filter, String field){

        return dao.getMin(filter, field);
    }

    /**
     * Busca o total de registros de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Quantidade de registros
     * @throws Exception
     */
    protected Integer getCountRecords(T filter) throws Exception {

        return dao.getCountRecords(filter);
    }
}
