package com.supertrufo;

import javax.persistence.*;
import java.util.List;

/**
 * AlunoDAO - Implementação do DAO para entidade Aluno
 * Utiliza JPA para persistência com Apache Derby
 */
public class AlunoDAO extends GenericDAO<Aluno, String> {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    /**
     * Construtor - Inicializa EntityManagerFactory e EntityManager
     */
    public AlunoDAO() {
        try {
            emf = Persistence.createEntityManagerFactory("supertrunfo-pu");
            em = emf.createEntityManager();
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar ao banco de dados: " + e.getMessage());
            throw new RuntimeException("Falha na conexão com o banco", e);
        }
    }
    
    @Override
    public void incluir(Aluno aluno) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(aluno);
            transaction.commit();
            System.out.println("✅ Aluno inserido com sucesso!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Erro ao inserir aluno: " + e.getMessage());
            throw new RuntimeException("Falha na inserção", e);
        }
    }
    
    @Override
    public void excluir(String matricula) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Aluno aluno = em.find(Aluno.class, matricula);
            if (aluno != null) {
                em.remove(aluno);
                transaction.commit();
                System.out.println("✅ Aluno removido com sucesso!");
            } else {
                transaction.rollback();
                System.err.println("❌ Aluno não encontrado com matrícula: " + matricula);
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Erro ao excluir aluno: " + e.getMessage());
            throw new RuntimeException("Falha na exclusão", e);
        }
    }
    
    @Override
    public Aluno obter(String matricula) {
        try {
            Aluno aluno = em.find(Aluno.class, matricula);
            if (aluno == null) {
                System.err.println("❌ Aluno não encontrado com matrícula: " + matricula);
            }
            return aluno;
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar aluno: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Aluno> obterTodos() {
        try {
            TypedQuery<Aluno> query = em.createQuery("SELECT a FROM Aluno a", Aluno.class);
            List<Aluno> alunos = query.getResultList();
            System.out.println("📊 Total de alunos encontrados: " + alunos.size());
            return alunos;
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar alunos: " + e.getMessage());
            return List.of(); // Retorna lista vazia
        }
    }
    
    @Override
    public void alterar(Aluno aluno) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Aluno alunoExistente = em.find(Aluno.class, aluno.getMatricula());
            if (alunoExistente != null) {
                alunoExistente.setNome(aluno.getNome());
                alunoExistente.setAno(aluno.getAno());
                em.merge(alunoExistente);
                transaction.commit();
                System.out.println("✅ Aluno alterado com sucesso!");
            } else {
                transaction.rollback();
                System.err.println("❌ Aluno não encontrado para alteração: " + aluno.getMatricula());
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Erro ao alterar aluno: " + e.getMessage());
            throw new RuntimeException("Falha na alteração", e);
        }
    }
    
    /**
     * Fecha os recursos do EntityManager e Factory
     */
    public void fechar() {
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            System.out.println("🔒 Conexão com banco de dados encerrada.");
        } catch (Exception e) {
            System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
        }
    }
}