package br.com.jbc.controller;

import java.util.List;

import br.com.jbc.util.SearchPaginate;
import br.com.jbc.db.Transaction;
import br.com.jbc.util.JBCException;
import java.io.Serializable;

/**
 * Classe de controle de operacoes CRUD com controle de transacao
 * @author Rodrigo Leandro Marconato
 * @param <T> Value Object de controle
 */
public class Controller<T> implements Serializable {
    
    /**
     * Variável que informa que uma consulta deve fazer busca usando Restrictions.ilike().
     * É usada nos métodos find e findList
     */
    public static int SEARCH_LIKE_STRING = 1;
    
    /**
     * Variável que informa que uma consulta deve fazer busca usando Restrictions.eq().
     * É usada nos métodos find e findList
     */
    public static int SEARCH_EQUALS_STRING = 2;
    
    /**
     * Váriavel que informa o tipo de ordenação para uma consulta Crescente.
     * É usada nos métodos findPaginate
     */
    public static boolean ORDER_ASC = Boolean.TRUE;
    
    /**
     * Váriavel que informa o tipo de ordenação para uma consulta Decrescente.
     * É usada nos métodos findPaginate
     */
    public static boolean ORDER_DESC = Boolean.TRUE;

    private Facade<T> facade;

    public Controller() {
        this.facade = new Facade<T>();
    }

    /**
     * Retorna o facade
     * @return Facade<T>
     */
    public Facade<T> getFacade() {
        return facade;
    }

    /**
     * Insere um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean insert(T obj) throws Exception {

        try {
            Transaction.getInstance().beginTransaction();
            if (this.facade.insert(obj)) {
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

    /**
     * Insere uma lista de registros em uma determinada tabela
     * @param objList Lista de Objetos VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean insert(List<T> objList) throws Exception {

        if (objList == null)
            return false;
        
        try {
            Transaction.getInstance().beginTransaction();
            
            for (Object obj : objList) {
                if (!this.facade.insert((T)obj)){
                    throw new JBCException(JBCException.TRANSACAO_DESFEITA);
                }
            }
            Transaction.getInstance().commit();
            return true;
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }

    /**
     * Insere ou atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean insertOrUpdate(T obj) throws Exception {
        try {
            Transaction.getInstance().beginTransaction();
            if (this.facade.insertOrUpdate(obj)) {
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

    /**
     * Insere ou atualiza uma lista de registros em uma determinada tabela
     * @param objList Lista de Objetos VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean insertOrUpdate(List<T> objList) throws Exception {
        
        if (objList == null)
            return false;
        
        try {
            Transaction.getInstance().beginTransaction();
            
            for (Object obj : objList) {
                if (!this.facade.insertOrUpdate((T)obj)){
                    throw new JBCException(JBCException.TRANSACAO_DESFEITA);
                }
            }
            Transaction.getInstance().commit();
            return true;
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }

    /**
     * Insere um registro em uma determinada tabela e retorna a entidade persistida
     * @param obj Objeto VO de persistência
     * @return O Objeto Persistido
     * @throws Exception
     */
    public T insertReturnId(T obj) throws Exception {

        try {
            Transaction.getInstance().beginTransaction();
            obj = this.facade.insertReturnId(obj);
            if (obj != null){
                Transaction.getInstance().commit();
                return obj;
            } else {
                Transaction.getInstance().rollback();
                return null;
            }
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }

    /**
     * Atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean update(T obj) throws Exception {
        try {
            Transaction.getInstance().beginTransaction();
            if (this.facade.update(obj)) {
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

    /**
     * Atualiza uma lista de registros em uma determinada tabela
     * @param objList Lista de Objetos VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean update(List<T> objList) throws Exception {
        
        if (objList == null)
            return false;
        
        try {
            Transaction.getInstance().beginTransaction();
            
            for (Object obj : objList) {
                if (!this.facade.update((T)obj)){
                    throw new JBCException(JBCException.TRANSACAO_DESFEITA);
                }
            }
            Transaction.getInstance().commit();
            return true;
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }

    /**
     * Elimina um determinado registro pelo Objeto
     * @param obj Objeto VO a ser eliminado contendo a sua chave primária
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean delete(T obj) throws Exception {
        try {
            Transaction.getInstance().getSession().clear();
            Transaction.getInstance().beginTransaction();
            if (this.facade.delete(obj)) {
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

    /**
     * Elimina uma lista de registros
     * @param objList Objeto VO a ser eliminado contendo a sua chave primária
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws Exception
     */
    public Boolean delete(List<T> objList) throws Exception {
        
        if (objList == null)
            return false;
        
        try {
            Transaction.getInstance().getSession().clear();
            for (Object obj : objList) {
                if (!this.facade.delete((T)obj)){
                    throw new JBCException(JBCException.TRANSACAO_DESFEITA);
                }
            }
            Transaction.getInstance().commit();
            return true;
        } catch (Exception e) {
            Transaction.getInstance().rollback();
            throw e;
        }
    }

    /**
     * Lista os registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    public List<T> findList(T filter) throws Exception {
        return this.facade.findList(filter);
    }

    /**
     * Lista os registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    public List<T> findList(T filter, int searchType) throws Exception {
        return this.facade.findList(filter);
    }

    /**
     * Retorna um registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    public T find(T filter) throws Exception {
        return this.facade.find(filter);
    }

    /**
     * Retorna um registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    public T find(T filter, int searchType) throws Exception {
        return this.facade.find(filter, searchType);
    }

    /**
     * Procura um registro por um ID
     * @param id Chave primária da entidade
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    public T findById(Class clazz, Number id) throws Exception {
        T t = this.facade.findById(clazz, id);
        return t;
    }

    /**
     * Busca uma lista de registro por uma condição HQL
     * @param condition Condição HQL
     * @return Lista de objetos encontrados na consulta
     * @throws Exception
     */
    public List<T> getListByHQLCondition(String condition) throws Exception {
        return this.facade.getListByHQLCondition(condition);
    }

    /**
     * Busca uma lista de registro por uma condição HQL com uma lista de parametros
     * @param condition Condição HQL
     * @param paramList Lista de parametros passada para a Query HQL
     * @param paramListName Nome da lista de parametros
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    public List<T> getListByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        return this.facade.getListByHQLCondition(condition, paramList, paramListName);
    }

    /**
     * Busca uma lista de registro por uma condição HQL paginada
     * @param condition Condição HQL
     * @param firstResult Número da página que a consulta irá mostrar os resultados
     * @param maxResult Número de registros por página
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    public List<T> getListByHQLCondition(String condition, int firstResult, int maxResult) throws Exception {
        return this.facade.getListByHQLCondition(condition, firstResult, maxResult);
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
    public List<T> getListByHQLCondition(String condition, List paramList, String paramListName, int firstResult, int maxResult) throws Exception {
        return this.facade.getListByHQLCondition(condition, paramList, paramListName, firstResult, maxResult);
    }

    /**
     * Busca um registro por uma condição HQL
     * @param condition Condição HQL
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    public T getObjectByHQLCondition(String condition) throws Exception {
        return this.facade.getObjectByHQLCondition(condition);
    }

    /**
     * Busca um valor de um registro por uma condição HQL, podendo ser um tipo primitivo, ou
     * qualquer valor que a condição HQL retorne
     * @param condition Condição HQL
     * @return Valor encontrado na consulta
     * @throws Exception
     */
    public Object getValueByHQLCondition(String condition) throws Exception {
        return this.facade.getValueByHQLCondition(condition);
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
    public Object getValueByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        return this.facade.getValueByHQLCondition(condition, paramList, paramListName);
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
    public SearchPaginate findPaginate(T filter, int firstResult, int maxResult, String sortField, boolean orderByType) throws Exception {
        return this.facade.findPaginate(filter, firstResult, maxResult, sortField, orderByType);
    }

    /**
     * Retornar o valor máximo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor máximo
     */
    public Number getMax(T filter, String field) {
        return this.facade.getMax(filter, field);
    }

    /**
     * metodo que retornar o valor minimo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor mínimo
     */
    public Number getMin(T filter, String field) {
        return this.facade.getMin(filter, field);
    }

    /**
     * Busca o total de registros de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Quantidade de registros
     * @throws Exception
     */
    public Integer getCountRecords(T filter) throws Exception {
        return this.facade.getCountRecords(filter);
    }

}