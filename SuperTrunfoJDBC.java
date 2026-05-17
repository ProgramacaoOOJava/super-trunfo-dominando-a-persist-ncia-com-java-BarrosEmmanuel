import java.util.List;
import java.util.Scanner;

public class SuperTrunfoJPA {
    private static final AlunoDAO dao = new AlunoDAO();
    private static final Scanner scanner = new Scanner(System.in);
    private static int pontuacao = 0;
    private static final int META_PONTOS = 5;

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 6 && pontuacao < META_PONTOS) {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            switch (opcao) {
                case 1 -> executarInserir();
                case 2 -> executarRemover();
                case 3 -> executarAlterar();
                case 4 -> executarListar();
                case 5 -> executarObter();
                case 6 -> System.out.println("👋 Saindo da jornada. Até a próxima!");
                default -> System.out.println("⚠️ Opção inválida! Escolha de 1 a 6.");
            }
        }

        if (pontuacao >= META_PONTOS) {
            System.out.println("\n🌟 🔥 PARABÉNS! Você atingiu " + pontuacao + " pontos operacionais!");
            System.out.println("🏆 Desafio Nível Aventureiro Concluído com Sucesso!");
        }
        
        AlunoDAO.fecharFabrica();
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
            System.out.println("❌ Operação falhou ou não gerou alterações.");
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
        verificarSucesso(ok, "Carta deletada do banco!");
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
        verificarSucesso(ok, "Dados atualizados com sucesso!");
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
        // Operação de leitura bem sucedida conta ponto conforme regra
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
