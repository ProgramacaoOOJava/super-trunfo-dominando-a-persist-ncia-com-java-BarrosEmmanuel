import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Sistema Super Trunfo usando JDBC puro
 * Nível 1 - Novato: Desafio de Código - "Cartas Clássicas - JDBC Puro"
 */
public class SuperTrunfoJDBC {
    
    // Configurações de conexão com o banco Derby
    private static final String URL = "jdbc:derby:escola;create=true";
    private static final String USUARIO = "";
    private static final String SENHA = "";
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    public static void main(String[] args) {
        criarTabela();
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
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            if (opcao == 1) {
                System.out.print("Matrícula: ");
                String mat = scanner.nextLine();
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Ano de Entrada: ");
                int ent = scanner.nextInt();
                inserirAluno(new Aluno(mat, nome, ent));
            } 
            else if (opcao == 2) {
                exibirTodasCartas();
            } 
            else if (opcao == 3) {
                System.out.print("Digite a matrícula para excluir: ");
                String mat = scanner.nextLine();
                excluirAluno(mat);
            } 
            else if (opcao == 4) {
                inserirDadosExemplo();
            } 
            else if (opcao == 5) {
                batalharCartas();
            }
        }
        System.out.println("Sistema encerrado com humildade. Até a próxima!");
    }
    
    /**
     * Obtém uma conexão com o banco de dados Derby
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    
    /**
     * Cria a tabela aluno se ela não existir
     */
    public static void criarTabela() {
        String sql = "CREATE TABLE aluno (matricula VARCHAR(20) PRIMARY KEY, nome VARCHAR(100), entrada INT)";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            System.out.println("⚙️ Banco de dados inicializado.");
            
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                // Tabela já existe, apenas prossegue silenciosamente
            } else {
                System.err.println("❌ Erro ao criar tabela: " + e.getMessage());
            }
        }
    }
    
    /**
     * Insere um aluno (carta) na base de dados usando PreparedStatement
     */
    public static boolean inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, entrada) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, aluno.getMatricula());
            ps.setString(2, aluno.getNome());
            ps.setInt(3, aluno.getEntrada());
            
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Carta inserida com sucesso!");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Consulta todos os alunos usando Statement e ResultSet
     */
    public static List<Aluno> consultarTodosAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEntrada(rs.getInt("entrada"));
                alunos.add(aluno);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao consultar: " + e.getMessage());
        }
        return alunos;
    }
    
    /**
     * Exclui um aluno usando PreparedStatement
     */
    public static boolean excluirAluno(String matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Carta excluída com sucesso.");
                return true;
            } else {
                System.out.println("⚠️ Nenhuma carta encontrada com essa matrícula.");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca um aluno específico por matrícula
     */
    public static Aluno buscarAluno(String matricula) {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getString("matricula"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setEntrada(rs.getInt("entrada"));
                    return aluno;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Exibe todas as cartas formatadas
     */
    public static void exibirTodasCartas() {
        List<Aluno> alunos = consultarTodosAlunos();
        
        if (alunos.isEmpty()) {
            System.out.println("📭 Baralho vazio.");
            return;
        }
        
        System.out.println("\n=== SEU BARALHO ===");
        for (Aluno aluno : alunos) {
            aluno.exibirCarta();
            System.out.println();
        }
    }
    
    /**
     * Insere dados de exemplo no sistema (Mínimo de 5 cartas requerido)
     */
    public static void inserirDadosExemplo() {
        Aluno[] exemplos = {
            new Aluno("A2020001", "Ana Silva", 2020),
            new Aluno("B2021002", "Bruno Dias", 2021),
            new Aluno("M2018003", "Carlos Lima", 2018),
            new Aluno("N2024004", "Nadia Souza", 2024),
            new Aluno("R2025005", "Rafael Cruz", 2025)
        };
        
        int inseridos = 0;
        for (Aluno aluno : exemplos) {
            if (buscarAluno(aluno.getMatricula()) == null) {
                if (inserirAluno(aluno)) {
                    inseridos++;
                }
            }
        }
        System.out.println("🎲 " + inseridos + " novas cartas de exemplo adicionadas.");
    }
    
    /**
     * Implementa a lógica de batalha simples entre duas cartas
     */
    public static void batalharCartas() {
        List<Aluno> alunos = consultarTodosAlunos();
        
        if (alunos.size() < 2) {
            System.out.println("⚠️ Cadastre pelo menos 2 cartas para poder batalhar!");
            return;
        }
        
        Aluno carta1 = alunos.get(random.nextInt(alunos.size()));
        Aluno carta2;
        do {
            carta2 = alunos.get(random.nextInt(alunos.size()));
        } while (carta1.getMatricula().equals(carta2.getMatricula()));
        
        System.out.println("\n--- JOGADOR 1 ---");
        carta1.exibirCarta();
        System.out.println("\n       VS        \n");
        System.out.println("--- JOGADOR 2 ---");
        carta2.exibirCarta();
        
        System.out.println("\nResultado da Batalha (Maior ano vence):");
        if (carta1.batalhar(carta2)) {
            System.out.println("🏆 VENCEDOR: Jogador 1 (" + carta1.getNome() + ")");
        } else if (carta2.batalhar(carta1)) {
            System.out.println("🏆 VENCEDOR: Jogador 2 (" + carta2.getNome() + ")");
        } else {
            System.out.println("🤝 EMPATE! Ambos entraram no mesmo ano.");
        }
    }
}
