package br.com.jbc.controller;

import br.com.jbc.db.BuildCriteria;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.Query;

import br.com.jbc.util.SearchPaginate;
import java.io.Serializable;

/**
 * clazz de manipulacao de registros no banco de dados
 * @author Rodrigo Leandro Marconato
 * @param <T> Value Object
 */
public class Dao<T> implements Serializable {

    private final Session session;

    @SuppressWarnings("unchecked")
    protected Dao(Session session) {
        this.session = session;
    }

    /**
     * Insere um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws ConstraintViolationException
     */
    protected Boolean insert(T obj) throws ConstraintViolationException {

        this.session.merge(obj);
        this.session.flush();
        this.session.evict(obj);

        return true;
    }

    /**
     * Insere ou atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws ConstraintViolationException
     */
    protected Boolean insertOrUpdate(T obj) throws ConstraintViolationException {
        this.session.merge(obj);
        this.session.flush();
        this.session.evict(obj);
        return true;
    }

    /**
     * Insere um registro em uma determinada tabela e retorna a entidade persistida
     * @param obj Objeto VO de persistência
     * @return O Objeto Persistido
     * @throws ConstraintViolationException
     */
    protected T insertReturnId(T obj) throws ConstraintViolationException {
        this.session.persist(obj);
        return obj;
    }

    /**
     * Atualiza um registro em uma determinada tabela
     * @param obj Objeto VO de persistência
     * @return Boolean.True para transação com sucesso e Boolean.False para transação com Rollback
     * @throws ConstraintViolationException
     */
    protected Boolean update(T obj) throws ConstraintViolationException {
        this.session.merge(obj);
        this.session.flush();
        this.session.evict(obj);
        return true;

    }

    /**
     * Elimina um determinado registro pelo Objeto
     * @param obj Objeto VO a ser eliminado contendo a sua chave primária
     * @throws Exception
     */
    protected void delete(T obj) throws Exception {
        this.session.delete(obj);
    }

    /**
     * Lista os registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected List<T> findList(T filter) throws Exception {
        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons

        Criteria criteria = BuildCriteria.createCriteria(filter, session, Controller.SEARCH_LIKE_STRING).add(example);

        return criteria.list();
    }

    /**
     * Lista os registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected List<T> findList(T filter, int searchType) throws Exception {
        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons

        Criteria criteria = BuildCriteria.createCriteria(filter, session, searchType).add(example);

        return criteria.list();
    }

    /**
     * Retorna um registro de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected T find(T filter) throws Exception {
        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons

        return (T) BuildCriteria.createCriteria(filter, session, Controller.SEARCH_LIKE_STRING).add(example).uniqueResult();
    }

    /**
     * Retorna um registro de uma determinada tabela, sendo que pode ser informado o tipo de consulta
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param searchType Tipo de pesquisa que será feita a consulta no banco de dados, sendo Controller.SEARCH_LIKE_STRING E Controller.SEARCH_EQUALS_STRING
     * @return Objeto encontrado na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected T find(T filter, int searchType) throws Exception {
        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons

        return (T) BuildCriteria.createCriteria(filter, session, searchType).add(example).uniqueResult();
    }

    /**
     * Procura um registro por um ID
     * @param id Chave primária da entidade
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected T findById(Class clazz, Number id) throws Exception {
        return (T) session.load(clazz, id);
    }

    /**
     * Busca uma lista de registro por uma condição HQL
     * @param condition Condição HQL
     * @return Lista de objetos encontrados na consulta
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListByHQLCondition(String condition) throws Exception {
        Query query = session.createQuery(condition);
        return (query.list());
    }

    /**
     * Busca uma lista de registro por uma condição HQL com uma lista de parametros
     * @param condition Condição HQL
     * @param paramList Lista de parametros passada para a Query HQL
     * @param paramListName Nome da lista de parametros
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        Query query = session.createQuery(condition).setParameterList(paramListName, paramList);
        return (query.list());
    }

    /**
     * Busca uma lista de registro por uma condição HQL paginada
     * @param condition Condição HQL
     * @param firstResult Número da página que a consulta irá mostrar os resultados
     * @param maxResult Número de registros por página
     * @return Lista de objetos encontrados na consulta
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListByHQLCondition(String condition, int firstResult, int maxResult) throws Exception {
        Query query = session.createQuery(condition).setFirstResult(firstResult).setMaxResults(maxResult);
        return (query.list());
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
    @SuppressWarnings("unchecked")
    protected List<T> getListByHQLCondition(String condition, List paramList, String paramListName, int firstResult, int maxResult) throws Exception {
        Query query = session.createQuery(condition).setParameterList(paramListName, paramList)
                .setFirstResult(firstResult).setMaxResults(maxResult);
        return (query.list());
    }

    /**
     * Busca um registro por uma condição HQL
     * @param condition Condição HQL
     * @return Objeto encontrado na consulta
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected T getObjectByHQLCondition(String condition) throws Exception {
        Query query = session.createQuery(condition);
        return ((T) query.uniqueResult());
    }

    /**
     * Busca um valor de um registro por uma condição HQL, podendo ser um tipo primitivo, ou
     * qualquer valor que a condição HQL retorne
     * @param condition Condição HQL
     * @return Valor encontrado na consulta
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected Object getValueByHQLCondition(String condition) throws Exception {
        Query query = session.createQuery(condition);
        return ((Object) query.uniqueResult());
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
    @SuppressWarnings("unchecked")
    protected Object getValueByHQLCondition(String condition, List paramList, String paramListName) throws Exception {
        Query query = session.createQuery(condition).setParameterList(paramListName, paramList);
        return ((Object) query.uniqueResult());
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
    @SuppressWarnings("unchecked")
    protected SearchPaginate findPaginate(T filter, int firstResult, int maxResult, String sortField, boolean orderByType) throws Exception {

        SearchPaginate searchPaginate = new SearchPaginate();

        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons


        // Pega o total de registros da pesquisa
        Criteria criteriaCount = BuildCriteria.createCriteria(filter, session, Controller.SEARCH_LIKE_STRING).add(example).setProjection(Projections.rowCount());

        Object count = criteriaCount.uniqueResult();
        if (count != null) {
            if (count instanceof Long){
                int countInt = ((Long) count).intValue();
                searchPaginate.setRowCount(countInt);
            }else{
                searchPaginate.setRowCount((Integer) count);
            }
        }

        while (searchPaginate.getRowCount() < firstResult) {
            firstResult = firstResult - maxResult;
        }

        //Efetua a pesquisa parametrizada e paginada
        Criteria criteria = BuildCriteria.createCriteria(filter, session, Controller.SEARCH_LIKE_STRING).
                                        setFirstResult(firstResult).
                                        setMaxResults(maxResult).
                                        add(example);

        if (sortField != null && !sortField.equals("")) {
            criteria.addOrder(orderByType ? Order.asc(sortField) : Order.desc(sortField));
        }

        searchPaginate.setListResult(criteria.list());

        return searchPaginate;
    }

    /**
     * Retornar o valor máximo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor máximo
     */
    protected Number getMax(T filter, String field) {

        Criteria c = session.createCriteria(filter.getClass());
        c.setProjection(Projections.max(field));
        return (Number) c.uniqueResult();
    }

    /**
     * metodo que retornar o valor minimo de acordo com o campo da tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @param field Nome do atributo da entidade que será pesquisado
     * @return Valor mínimo
     */
    protected Number getMin(T filter, String field) {

        Criteria c = session.createCriteria(filter.getClass());
        c.setProjection(Projections.min(field));
        return (Number) c.uniqueResult();
    }

    /**
     * Busca o total de registros de uma determinada tabela
     * @param filter Objeto VO contendo os filtros de consulta populados em seus atributos
     * @return Quantidade de registros
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected Integer getCountRecords(T filter) throws Exception {

        Example example = Example.create(filter).excludeZeroes() //exclude zero valued properties
                .ignoreCase().enableLike(MatchMode.ANYWHERE);   //use like for string comparisons

        // Pega o total de registros da pesquisa
        Criteria criteriaCount = BuildCriteria.createCriteria(filter, session, Controller.SEARCH_LIKE_STRING).add(example).setProjection(Projections.rowCount());

        List resultCount = criteriaCount.list();
        if (!resultCount.isEmpty()) {
            return (Integer) resultCount.get(0);
        } else {
            return 0;
        }
    }

    
}
