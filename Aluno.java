public class Aluno {
    private String matricula;
    private String nome;
    private int entrada;
    
    public Aluno() {}
    
    public Aluno(String matricula, String nome, int entrada) {
        this.matricula = matricula;
        this.nome = nome;
        this.entrada = entrada;
    }
    
    public String getMatricula() { return this.matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getEntrada() { return this.entrada; }
    public void setEntrada(int entrada) { this.entrada = entrada; }
    public int getForca() { return this.entrada; }
    
    public String getRaridade() {
        if (matricula == null || matricula.isEmpty()) return "Comum";
        char pr = Character.toUpperCase(matricula.charAt(0));
        return (pr >= 'A' && pr <= 'M') ? "Comum" : "Rara";
    }
    
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
    
    public boolean batalhar(Aluno oponente) {
        return this.entrada > oponente.entrada;
    }
}
