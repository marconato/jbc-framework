/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jbc.db;

import br.com.jbc.controller.Controller;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Rodrigo Marconato
 */
public class BuildCriteria {
   
    /**
     * Criar um objeto org.hibernate.Criteria com restrições e álias necessários
     * para realização da consulta
     * @param entity Entidade VO
     * @param session Sessão hibernate usada para Criar o Criteria
     * @return Objeto Criteria com restrições e álias
     */
    public static Criteria createCriteria(Object entity, org.hibernate.Session session, int searchType){
        
        Criteria criteria = session.createCriteria(entity.getClass());
        
        br.com.jbc.db.Restrictions restrictions = parseRestrictions(entity);
        String rest = null;
        HashMap<String, String> alias = new HashMap<String, String>();
        
        for (Entry<String, Object> entry : restrictions.entrySet()) {
            
            alias.putAll(parseAlias(entry.getKey()));
            if (entry.getKey().contains(".")){
                rest = entry.getKey().substring(0, entry.getKey().lastIndexOf("."));
                rest = entry.getKey().replace(rest, alias.get(rest));
            }else{
                rest = entry.getKey();
            }
            
            if (entry.getValue() instanceof String && searchType == Controller.SEARCH_LIKE_STRING){
                criteria.add(Restrictions.ilike(rest, entry.getValue().toString(), MatchMode.ANYWHERE));
            }else{
                criteria.add(Restrictions.eq(rest, entry.getValue()));
            }
        }
            
        String key;
        for (Iterator it = alias.keySet().iterator(); it.hasNext();) {
            key = (String) it.next();
            criteria.createAlias(key, alias.get(key), Criteria.LEFT_JOIN);
        }
        
        return criteria;
    }
    
    /**
     * Captura um map de restricoes encontradas na entidade
     * @param entity Objeto VO contendo os atributos
     * @return Restrições da entidade
     */
    public static br.com.jbc.db.Restrictions parseRestrictions(Object entity) {
        
        List<Field> fields = loadAnnotationFields(entity);
        
        br.com.jbc.db.Restrictions filter = new br.com.jbc.db.Restrictions();
        for (Field field : fields) {
            try {
                Method method = entity.getClass().getMethod(generateNameGetMethod(field.getName()), new Class[0]);
                Object result = method.invoke(entity, new Object[0]);
                //Ignorando valores nulos
                if (result == null || result.toString().isEmpty()){
                    continue;
                }
                if (isPrimitive(result) == false) {
                    //Escaneando restrições Embeddeds e Entitys
                    if (result.getClass().getAnnotation(javax.persistence.Embeddable.class) != null ||
                            result.getClass().getAnnotation(javax.persistence.Entity.class) != null) {
                        HashMap<String, Object> filter2 = (HashMap<String, Object>) parseRestrictions(result);
                        
                        for (Entry<String, Object> entry : filter2.entrySet()) {
                            filter.add(field, generateNameObject(result.getClass().getSimpleName()) + "." + entry.getKey(), entry.getValue());
                        }

                    }else {//Escaneando restrições ID
                        parseIDSInFilter(entity, filter, field, result);
                    }
                } else if (!result.toString().isEmpty()) {
                    filter.add(field, field.getName(), result);
                }
            } catch (Exception e) {
                //Ignorando Exceções} }
            }
        }

        return filter;
    }
    
    /**
     * Recupera a lista de alias de acordo com uma chave
     * @param key
     * @return Map de alias da chave
     */
    public static HashMap<String, String> parseAlias(String key) {
        
        HashMap<String, String> alias = new HashMap<String, String>();
        
        String s;        
        while (key.contains(".")){
                s = key.substring(0, key.lastIndexOf("."));
                if (s.contains(".")){
                    alias.put(s, s.substring(s.lastIndexOf(".")+1, s.length()));
                }else{
                    alias.put(s, s);
                }
                key = s;
            }
        
        return alias;
    }
    
    
    /**
     * Carrega os campos do objeto e super classes que tiverem anotações hibernate
     * @param entity Objeto VO contendo os atributos
     * @return Lista de Fields com anotações
     */
    public static  List<Field> loadAnnotationFields(Object entity){
        
        List<Field> validFields = new ArrayList<Field>();
        Field[] fields = entity.getClass().getDeclaredFields();
        
        for (Field field : fields) {
             if (field.getAnnotations() != null && field.getAnnotations().length > 0){
                 validFields.add(field);
             }
        }
        
        Class superClass = entity.getClass().getSuperclass();
        List<Field> fieldsSuperClass;
        while (superClass != null){
            fieldsSuperClass = Arrays.asList(superClass.getDeclaredFields());
            for (Field fieldSuperClass : fieldsSuperClass) {
                if (fieldSuperClass.getAnnotations() != null && fieldSuperClass.getAnnotations().length > 0){
                    validFields.add(fieldSuperClass);
                }
            }
            superClass = superClass.getSuperclass();
        }
        
        return validFields;
    }

    /**
     * Escaneia as chaves primárias com anotação javax.persistence.Id.class
     * @param entity - Entidade passada para escanear
     * @param filter - Filtro para adicionar Restrição
     * @param field - Atributo atual
     * @param result - Método de Resultado Invocado
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static void parseIDSInFilter(Object entity, br.com.jbc.db.Restrictions filter, Field field, Object result) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        boolean flID = false;
        
        for (Field fieldResult : result.getClass().getDeclaredFields()) {
            if (fieldResult.getAnnotation(javax.persistence.Id.class) != null) {
                Method methodResult = entity.getClass().getMethod(generateNameGetMethod(field.getName()), new Class[0]);
                Object resultResult = methodResult.invoke(entity, new Object[0]);
                filter.add(field, field.getName(), resultResult);
                flID = true;
                break;
            }
        }
        if (flID == false) {
            try {
                Method methodResult = result.getClass().getMethod(generateNameGetMethod("id"), new Class[0]);
                Object resultResult = methodResult.invoke(result, new Object[0]);
                if (resultResult != null && Long.parseLong(resultResult.toString()) != 0) {
                    filter.add(field, field.getName() + ".id", resultResult);
                }
            } catch (NoSuchMethodException nsmE) {
            }

        }
    }

    /**
     * Gera o nome do método de acordo com o nome do atributo
     * @param attributeName
     * @return O nome do método gerado
     */
    public static String generateNameGetMethod(String attributeName) {
        return "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
    }

    /**
     * Gera o nome do objeto relacional
     * @param name
     * @return O nome do objeto gerado
     */
    public static String generateNameObject(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * Verifica se o Objeto é uma classe Wrapper-Primitiva
     * @param obj - Objeto para ser verificado
     * @return true para valores primitivos
     */
    public static boolean isPrimitive(Object obj) {
        if (obj instanceof Number) {
            return true;
        } else if (obj instanceof String) {
            return true;
        } else if (obj instanceof Date) {
            return true;
        } else if (obj instanceof Calendar) {
            return true;
        } else if (obj instanceof Boolean) {
            return true;
        } else {
            return false;
        }
    }
}
