/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jbc.db;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Restrições de uma determinada entidade
 * @author Rodrigo Marconato
 */
public class Restrictions extends HashMap<String, Object> {
    
    /**
     * Adiciona a restrição efetuando validação de acordo com o tipo de dados
     * @param field Atributo da entidade VO
     * @param fieldName Nome do atributo da entidade VO
     * @param fieldValue Valor do atributo da entidade VO
     */
    public void add(java.lang.reflect.Field field, String fieldName, Object fieldValue){
        
        Annotation[] annotations = field.getDeclaredAnnotations();
        boolean isKey = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == javax.persistence.Id.class
                    || annotation.annotationType() == javax.persistence.ManyToOne.class
                    || annotation.annotationType() == javax.persistence.OneToMany.class){
                isKey = true;
            }
        }
        
        if (isKey){
            // verifica se o valor for diferente de zero, se for zero nao faz inclui a restricao
            if (fieldValue instanceof Long){
                if (new Long(fieldValue.toString()).compareTo(new Long(0)) != 0){
                    this.put(fieldName, fieldValue);
                }
            } 
            if (fieldValue instanceof Integer){
                if (new Integer(fieldValue.toString()).compareTo(new Integer(0)) != 0){
                    this.put(fieldName, fieldValue);
                }
            }                
        }else{  
            if (!(fieldValue instanceof Number)){
                this.put(fieldName, fieldValue);
            }
        }
    }
    
}
