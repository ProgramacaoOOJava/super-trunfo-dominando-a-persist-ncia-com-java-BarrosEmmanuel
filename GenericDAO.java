package com.supertrufo;

import java.util.List;

/**
 * GenericDAO - Classe abstrata para operações CRUD genéricas
 * @param <E> Tipo da entidade
 * @param <K> Tipo da chave primária
 */
public abstract class GenericDAO<E, K> {
    
    /**
     * Inclui uma nova entidade no banco de dados
     * @param entidade Entidade a ser incluída
     */
    public abstract void incluir(E entidade);
    
    /**
     * Exclui uma entidade pelo identificador
     * @param chave Identificador da entidade
     */
    public abstract void excluir(K chave);
    
    /**
     * Obtém uma entidade pelo identificador
     * @param chave Identificador da entidade
     * @return Entidade encontrada ou null
     */
    public abstract E obter(K chave);
    
    /**
     * Obtém todas as entidades do tipo
     * @return Lista com todas as entidades
     */
    public abstract List<E> obterTodos();
    
    /**
     * Altera uma entidade existente
     * @param entidade Entidade com dados atualizados
     */
    public abstract void alterar(E entidade);
}