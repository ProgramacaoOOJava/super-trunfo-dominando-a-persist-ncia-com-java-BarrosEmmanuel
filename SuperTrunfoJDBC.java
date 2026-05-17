import java.util.List;
import java.util.Scanner;

public class SuperTrunfoJDBC {
    private static final AlunoDAO dao = new AlunoDAO();
    private static final Scanner scanner = new Scanner(System.in);
    private static int pontuacao = 0;
    private static final int META_PONTOS = 5;

    public static void main(String[] args) {
        int opcao = -1;

        // Mantem o laco enquanto nao escolher sair (6) e nao atingir 5 pontos de sucesso
        while (opcao != 6 && pontuacao < META_PONTOS) {
            exibirMenu();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa buffer
            } else {
                scanner.nextLine();
                System.out.println("⚠️ Entrada inválida! Digite um número de 1 a 6.");
                continue;
            }

            // Sintaxe de switch classica para total compatibilidade com Java 11
            switch (opcao) {
                case 1:
                    executarInserir();
                    break;
                case 2:
                    executarRemover();
                    break;
                case 3:
                    executarAlterar();
                    break;
                case 4:
                    executarListar();
                    break;
                case 5:
                    executarObter();
                    break;
                case 6:
                    System.out.println("👋 Saindo do jogo de forma voluntária. Até logo!");
                    break;
                default:
                    System.out.println("⚠️ Opção inválida! Escolha de 1 a 6.");
                    break;
            }
        }

        // Regra de Ouro: Se atingiu os 5 pontos, mostra o resultado final e o baralho restante
        if (pontuacao >= META_PONTOS) {
            System.out.println("\n🌟 🔥 FIM DE JOGO! Você atingiu a meta de " + META_PONTOS + " operações válidas!");
            System.out.println("🎯 Pontuação Final do Jogador: " + pontuacao + " pontos.");
            
            System.out.println("\n📋 === BARALHO FINAL (ALUNOS RESTANTES NA BASE) ===");
            List<Aluno> listaRestante = dao.obterTodos();
            if (listaRestante.isEmpty()) {
                System.out.println("📭 O baralho terminou completamente vazio.");
            } else {
                for (Aluno a : listaRestante) {
                    a.exibirCarta();
                }
            }
            System.out.println("\n🏆 Desafio Nível Aventureiro Concluído com Sucesso e Humildade!");
        }
        
        // Fecha os recursos corretamente conforme os requisitos nao funcionais
        AlunoDAO.fecharFabrica();
    }

    private static void exibirMenu() {
        System.out.println("\n⚔️ ====== MENU AVENTUREIRO (JPA) ======");
        System.out.println("🎯 Pontuação Atual: [" + pontuacao + "/" + META_PONTOS + "]");
        System.out.println("1. Inserir aluno");
        System.out.println("2. Remover aluno");
        System.out.println("3. Alterar dados de aluno");
        System.out.println("4. Listar todos os alunos");
        System.out.println("5. Obter aluno por matrícula");
        System.out.println("6. Sair do jogo");
        System.out.print("👉 Escolha uma opção: ");
    }

    private static void verificarSucesso(boolean sucesso, String mensagemOk) {
        if (sucesso) {
            pontuacao++;
            System.out.println("✅ " + mensagemOk + " (+1 Ponto Operacional!)");
        } else {
            System.out.println("❌ A operação falhou ou não encontrou o registro.");
        }
    }

    private static void executarInserir() {
        System.out.print("Matrícula (Chave Primária): ");
        String mat = scanner.nextLine();
        System.out.print("Nome do Aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        
        boolean ok = dao.incluir(new Aluno(mat, nome, ano));
        verificarSucesso(ok, "Aluno inserido com sucesso via JPA!");
    }

    private static void executarRemover() {
        System.out.print("Digite a matrícula do aluno para remover: ");
        String mat = scanner.nextLine();
        
        boolean ok = dao.excluir(mat);
        verificarSucesso(ok, "Aluno removido da base de dados!");
    }

    private static void executarAlterar() {
        System.out.print("Digite a matrícula do aluno que deseja alterar: ");
        String mat = scanner.nextLine();
        Aluno existente = dao.obter(mat);
        
        if (existente == null) {
            System.out.println("⚠️ Aluno não encontrado!");
            return;
        }
        
        System.out.print("Novo Nome (Atual: " + existente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo Ano (Atual: " + existente.getAno() + "): ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        
        boolean ok = dao.alterar(new Aluno(mat, nome, ano));
        verificarSucesso(ok, "Dados do aluno alterados com sucesso!");
    }

    private static void executarListar() {
        System.out.println("\n📋 === LISTANDO TODOS OS ALUNOS ===");
        List<Aluno> lista = dao.obterTodos();
        if (lista.isEmpty()) {
            System.out.println("📭 Nenhum aluno cadastrado no momento.");
        } else {
            for (Aluno a : lista) {
                a.exibirCarta();
            }
        }
        // Operacao de listagem bem sucedida acumula ponto conforme regra do jogo
        verificarSucesso(true, "Listagem gerada!");
    }

    private static void executarObter() {
        System.out.print("Digite a matrícula buscada: ");
        String mat = scanner.nextLine();
        Aluno a = dao.obter(mat);
        if (a != null) {
            a.exibirCarta();
            verificarSucesso(true, "Aluno localizado com sucesso!");
        } else {
            System.out.println("⚠️ Nenhum aluno encontrado com a matrícula: " + mat);
        }
    }
}
