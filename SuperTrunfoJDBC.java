import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SuperTrunfoJDBC {
    // Lista simples em memória para simular o banco e rodar sem travar no Codespace
    private static List<Aluno> bancoEmMemoria = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("⚙️ Sistema inicializado com sucesso em memória.");
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU SUPER TRUNFO ---");
            System.out.println("1. Cadastrar Carta (Aluno)");
            System.out.println("2. Listar Todas as Cartas");
            System.out.println("3. Excluir uma Carta");
            System.out.println("4. Inserir 5 Cartas de Exemplo");
            System.out.println("5. Iniciar Batalha");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("⚠️ Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    interagirInsercao();
                    break;
                case 2:
                    exibirTodasCartas();
                    break;
                case 3:
                    interagirExclusao();
                    break;
                case 4:
                    inserirDadosExemplo();
                    break;
                case 5:
                    batalharCartas();
                    break;
                case 0:
                    System.out.println("Saindo do jogo... Obrigado por jogar!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
    
    public static boolean inserirAluno(Aluno aluno) {
        bancoEmMemoria.add(aluno);
        System.out.println("✅ Carta inserida: " + aluno.getNome());
        return true;
    }
    
    public static List<Aluno> consultarTodosAlunos() {
        return bancoEmMemoria;
    }
    
    public static boolean excluirAluno(String matricula) {
        for (int i = 0; i < bancoEmMemoria.size(); i++) {
            if (bancoEmMemoria.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println("✅ Carta com matrícula " + matricula + " foi removida.");
                bancoEmMemoria.remove(i);
                return true;
            }
        }
        System.out.println("⚠️ Nenhuma carta encontrada com essa matrícula.");
        return false;
    }
    
    public static Aluno buscarAluno(String matricula) {
        for (Aluno a : bancoEmMemoria) {
            if (a.getMatricula().equalsIgnoreCase(matricula)) return a;
        }
        return null;
    }
    
    public static void exibirTodasCartas() {
        if (bancoEmMemoria.isEmpty()) {
            System.out.println("📭 Baralho vazio.");
            return;
        }
        System.out.println("\n=== SEU BARALHO ===");
        for (Aluno aluno : bancoEmMemoria) {
            aluno.exibirCarta();
            System.out.println();
        }
    }
    
    public static void inserirDadosExemplo() {
        String[][] dados = {
            {"A2020001", "Ana Silva", "2020"},
            {"B2021002", "Bruno Dias", "2021"},
            {"M2018003", "Carlos Lima", "2018"},
            {"N2024004", "Nadia Souza", "2024"},
            {"R2025005", "Rafael Cruz", "2025"}
        };
        int count = 0;
        for (String[] d : dados) {
            if (buscarAluno(d[0]) == null) {
                inserirAluno(new Aluno(d[0], d[1], Integer.parseInt(d[2])));
                count++;
            }
        }
        System.out.println("🎲 " + count + " novas cartas de exemplo adicionadas.");
    }
    
    public static void batalharCartas() {
        if (bancoEmMemoria.size() < 2) {
            System.out.println("⚠️ Cadastre pelo menos 2 cartas para poder batalhar!");
            return;
        }
        Aluno c1 = bancoEmMemoria.get(random.nextInt(bancoEmMemoria.size()));
        Aluno c2;
        do {
            c2 = bancoEmMemoria.get(random.nextInt(bancoEmMemoria.size()));
        } while (c1.getMatricula().equals(c2.getMatricula()));
        
        System.out.println("\n--- JOGADOR 1 ---");
        c1.exibirCarta();
        System.out.println("\n       VS        \n");
        System.out.println("--- JOGADOR 2 ---");
        c2.exibirCarta();
        
        System.out.println("\nResultado da Batalha:");
        if (c1.batalhar(c2)) {
            System.out.println("🏆 VENCEDOR: Jogador 1 (" + c1.getNome() + ")");
        } else if (c2.batalhar(c1)) {
            System.out.println("🏆 VENCEDOR: Jogador 2 (" + c2.getNome() + ")");
        } else {
            System.out.println("🤝 EMPATE!");
        }
    }

    private static void interagirInsercao() {
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Ano de Entrada: ");
        int ent = scanner.nextInt();
        scanner.nextLine();
        inserirAluno(new Aluno(mat, nome, ent));
    }

    private static void interagirExclusao() {
        System.out.print("Matrícula para excluir: ");
        String mat = scanner.nextLine();
        excluirAluno(mat);
    }
}
