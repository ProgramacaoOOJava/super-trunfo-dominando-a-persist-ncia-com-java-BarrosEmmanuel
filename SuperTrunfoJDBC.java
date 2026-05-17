import java.util.List;
import java.util.Scanner;

/**
 * Sistema Super Trunfo adaptado para JPA e GenericDAO
 * Nível 2 - Aventureiro: Desafio de Código
 */
public class SuperTrunfoJDBC {
    private static final AlunoDAO dao = new AlunoDAO();
    private static final Scanner scanner = new Scanner(System.in);
    private static int pontuacao = 0;
    private static final int META_PONTOS = 5;

    public static void main(String[] args) {
        int opcao = -1;

        // Executa o menu fixo até escolher sair (6) ou alcançar 5 pontos operacionais válidos
        while (opcao != 6 && pontuacao < META_PONTOS) {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            if (opcao == 1) {
                executarInserir();
            } else if (opcao == 2) {
                executarRemover();
            } else if (opcao == 3) {
                executarAlterar();
            } else if (opcao == 4) {
                executarListar();
            } else if (opcao == 5) {
                executarObter();
            } else if (opcao == 6) {
                System.out.println("👋 Saindo da jornada. Até a próxima!");
            } else {
                System.out.println("⚠️ Opção inválida! Escolha de 1 a 6.");
            }
        }

        if (pontuacao >= META_PONTOS) {
            System.out.println("\n🌟 🔥 PARABÉNS! Você atingiu " + pontuacao + " pontos operacionais!");
            System.out.println("🏆 Desafio Nível Aventureiro Concluído com Sucesso!");
        }
        
        GenericDAO.fecharFabrica();
    }

    private static void exibirMenu() {
        System.out.println("\n⚔️ ====== MENU AVENTUREIRO (JPA) ======");
        System.out.println("🎯 Pontuação Atual: [" + pontuacao + "/" + META_PONTOS + "]");
        System.out.println("1. Inserir Carta");
        System.out.println("2. Remover Carta");
        System.out.println("3. Alterar Carta");
        System.out.println("4. Listar Todas as Cartas");
        System.out.println("5. Obter Carta por Matrícula");
        System.out.println("6. Sair");
        System.out.print("👉 Escolha uma opção: ");
    }

    private static void verificarSucesso(boolean sucesso, String mensagemOk) {
        if (sucesso) {
            pontuacao++;
            System.out.println("✅ " + mensagemOk + " (+1 Ponto!)");
        } else {
            System.out.println("❌ Operação falhou.");
        }
    }

    private static void executarInserir() {
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Ano de Entrada: ");
        int ent = scanner.nextInt();
        
        boolean ok = dao.inserir(new Aluno(mat, nome, ent));
        verificarSucesso(ok, "Carta salva com sucesso via JPA!");
    }

    private static void executarRemover() {
        System.out.print("Digite a matrícula para remover: ");
        String mat = scanner.nextLine();
        boolean ok = dao.remover(mat);
        verificarSucesso(ok, "Carta deletada com sucesso!");
    }

    private static void executarAlterar() {
        System.out.print("Matrícula da carta que deseja alterar: ");
        String mat = scanner.nextLine();
        Aluno existente = dao.obter(mat);
        
        if (existente == null) {
            System.out.println("⚠️ Carta não encontrada!");
            return;
        }
        
        System.out.print("Novo Nome (Atual: " + existente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo Ano Entrada (Atual: " + existente.getEntrada() + "): ");
        int ent = scanner.nextInt();
        
        boolean ok = dao.alterar(new Aluno(mat, nome, ent));
        verificarSucesso(ok, "Dados updated com sucesso!");
    }

    private static void executarListar() {
        List<Aluno> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhum aluno no registro.");
            return;
        }
        System.out.println("\n📋 === LISTAGEM COMPLETA ===");
        for (Aluno a : lista) {
            a.exibirCarta();
        }
        verificarSucesso(true, "Listagem gerada com sucesso!");
    }

    private static void executarObter() {
        System.out.print("Digite a matrícula: ");
        String mat = scanner.nextLine();
        Aluno a = dao.obter(mat);
        if (a != null) {
            a.exibirCarta();
            verificarSucesso(true, "Busca realizada!");
        } else {
            System.out.println("⚠️ Nenhuma carta encontrada com a matrícula " + mat);
        }
    }
}
