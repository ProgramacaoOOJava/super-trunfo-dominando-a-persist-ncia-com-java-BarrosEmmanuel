package com.supertrufo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alunos")
public class Aluno implements Serializable {
    
    @Id
    @Column(name = "matricula", length = 20)
    private String matricula;
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "ano", nullable = false)
    private int ano;
    
    // Construtor padrão (obrigatório para JPA)
    public Aluno() {}
    
    // Construtor com parâmetros
    public Aluno(String matricula, String nome, int ano) {
        this.matricula = matricula;
        this.nome = nome;
        this.ano = ano;
    }
    
    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    @Override
    public String toString() {
        return String.format("📚 Matrícula: %s | Nome: %s | Ano: %d", 
                            matricula, nome, ano);
    }
}