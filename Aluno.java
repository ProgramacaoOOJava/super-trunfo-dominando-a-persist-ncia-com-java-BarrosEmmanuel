/**
 * Classe que representa um aluno no sistema Super Trunfo
 * Nível 1 - Novato: Desafio de Código
 */
public class Aluno {
    private String matricula;
    private String nome;
    private int entrada;
    
    // Construtor padrão
    public Aluno() {
    }
    
    // Construtor com parâmetros
    public Aluno(String matricula, String nome, int entrada) {
        this.matricula = matricula;
        this.nome = nome;
        this.entrada = entrada;
    }
    
    // Getters e Setters
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
    
    /**
     * Calculates the power of the card based on entry year
     */
    public int getForca() {
        return this.entrada;
    }
    
    /**
     * Determines card rarity based on the first letter of the enrollment
     * A-M = Common, N-Z = Rare
     */
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
    
    /**
     * Displays the card formatted in Super Trunfo style
     */
    public void exibirCarta() {
        System.out.println("================================");
        System.out.println("     SUPER TRUNFO - ALUNOS     ");
        System.out.println("================================");
        System.out.println("Matrícula: " + this.matricula);
        System.out.println("Nome     : " + this.nome);
        System.out.println("Entrada  : " + this.entrada);
        System.out.println("Força    : " + getForca());
        System.out.println("Raridade : " + getRaridade());
        System.out.println("================================");
    }
    
    /**
     * Compares two cards in a battle
     */
    public boolean batalhar(Aluno oponente) {
        return this.entrada > oponente.entrada;
    }
    
    @Override
    public String toString() {
        return "Aluno: " + nome + " (" + matricula + ")";
    }
}
