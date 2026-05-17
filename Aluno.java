import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
public class Aluno {
    
    @Id
    private String matricula;
    
    @Column(length = 100)
    private String nome;
    
    private int entrada;
    
    // Construtor padrão obrigatório para o JPA
    public Aluno() {
    }
    
    // Construtor com parâmetros preenchido de forma limpa
    public Aluno(String matricula, String nome, int entrada) {
        this.matricula = matricula;
        this.nome = nome;
        this.entrada = entrada;
    }
    
    // Getters e Setters sem nenhuma marcação oculta
    public String getMatricula() {
        return this.matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getEntrada() {
        return this.entrada;
    }
    
    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }
    
    public int getForca() {
        return this.entrada;
    }
    
    public String getRaridade() {
        if (matricula == null || matricula.isEmpty()) {
            return "Comum";
        }
        char primeiraLetra = Character.toUpperCase(matricula.charAt(0));
        if (primeiraLetra >= 'A' && primeiraLetra <= 'M') {
            return "Comum";
        } else {
            return "Rara";
        }
    }
    
    public void exibirCarta() {
        System.out.println("🃏 ================================");
        System.out.println("     SUPER TRUNFO - ALUNOS (JPA)   ");
        System.out.println("==================================");
        System.out.println("🆔 Matrícula: " + this.matricula);
        System.out.println("👤 Nome     : " + this.nome);
        System.out.println("📅 Entrada  : " + this.entrada);
        System.out.println("💪 Força    : " + getForca());
        System.out.println("⭐ Raridade : " + getRaridade());
        System.out.println("==================================");
    }
    
    public boolean batalhar(Aluno oponente) {
        return this.entrada > oponente.entrada;
    }
    
    @Override
    public String toString() {
        return "Aluno: " + nome + " (" + matricula + ")";
    }
}
