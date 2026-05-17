import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Aluno {
    
    @Id
    private String matricula;
    
    @Column(length = 100)
    private String nome;
    
    private int entrada;
    
    public Aluno() {
    }
    
    public Aluno(String matricula, String nome, int entrada) {
        this.matricula = matricula;
        this.nome = nome;
        this.entrada = entrada;
    }
    
    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getEntrada() { return entrada; }
    public void setEntrada(int entrada) { this.entrada = entrada; }
    
    public int getForca() {
        return this.entrada;
    }
    
    public String getRaridade() {
        if (matricula == null || matricula.isEmpty()) return "Comum";
        char primeiraLetra = Character.toUpperCase(matricula.charAt(0));
        return (primeiraLetra >= 'A' && primeiraLetra <= 'M') ? "Comum" : "Rara";
    }
    
    public void exibirCarta() {
        System.out.println("🃏 .----------------------------.");
        System.out.printf("   | %-26s |\n", "SUPER TRUNFO - JPA");
        System.out.println("   |----------------------------|");
        System.out.printf("   | 🆔 Matrícula : %-12s |\n", matricula);
        System.out.printf("   | 👤 Nome      : %-12s |\n", nome.length() > 12 ? nome.substring(0, 9) + "..." : nome);
        System.out.printf("   | 📅 Entrada   : %-12d |\n", entrada);
        System.out.printf("   | 💪 Força     : %-12d |\n", getForca());
        System.out.printf("   | ⭐ Raridade  : %-12s |\n", getRaridade());
        System.out.println("   '----------------------------'");
    }
}
