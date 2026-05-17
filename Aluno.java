import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Aluno {
    
    @Id
    private String matricula;
    
    @Column(length = 100)
    private String nome;
    
    private int ano;
    
    // Construtor padrao obrigatorio para o JPA
    public Aluno() {
    }
    
    // Construtor com parametros
    public Aluno(String matricula, String nome, int ano) {
        this.matricula = matricula;
        this.nome = nome;
        this.ano = ano;
    }
    
    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    
    // Regra de negocio: Forca baseada no ano
    public int getForca() {
        return this.ano;
    }
    
    // Regra de negocio: Raridade baseada na primeira letra da matricula
    public String getRaridade() {
        if (matricula == null || matricula.isEmpty()) return "Comum";
        char primeiraLetra = Character.toUpperCase(matricula.charAt(0));
        if (primeiraLetra >= 'A' && primeiraLetra <= 'M') {
            return "Comum";
        } else {
            return "Rara";
        }
    }
    
    // Interface rica e feedback visual em console
    public void exibirCarta() {
        System.out.println("🃏 .----------------------------.");
        System.out.printf("   | %-26s |\n", "SUPER TRUNFO - JPA");
        System.out.println("   |----------------------------|");
        System.out.printf("   | 🆔 Matrícula : %-12s |\n", matricula);
        System.out.printf("   | 👤 Nome      : %-12s |\n", nome.length() > 12 ? nome.substring(0, 9) + "..." : nome);
        System.out.printf("   | 📅 Ano        : %-12d |\n", ano);
        System.out.printf("   | 💪 Força     : %-12d |\n", getForca());
        System.out.printf("   | ⭐ Raridade  : %-12s |\n", getRaridade());
        System.out.println("   '----------------------------'");
    }

    @Override
    public String toString() {
        return "Aluno: " + nome + " (" + matricula + ")";
    }
}
