package com.supertrufo;

import java.util.List;
import java.util.Scanner;

/**
 * SuperTrunfoGame - Aplicação principal com menu interativo e sistema de pontuação
 */
public class SuperTrunfoGame {
    
    private static AlunoDAO alunoDAO;
    private static Scanner scanner;
    private static int pontos = 0;
    private static int operacoesValidas = 0;
    
    public static void main(String[] args) {
        System.out.println("🎮 ========== SUPER TRUNFO EDUCAÇÃO ========== 🎮");
        System.out.println("✨ Bem-vindo ao jogo de cartas dos alunos! ✨");
        System.out.println("📝 Cada operação bem-sucedida vale 1 ponto!");
        System.out.println("🏆 Após 5 pontos, o jogo termina!\n");
        
        try {
            // Inicializa DAO e Scanner
            alunoDAO = new AlunoDAO();
            scanner = new Scanner(System.in);
            
            // Loop principal do jogo
            boolean continuarJogo = true;
            while (continuarJogo && operacoesValidas < 5) {
                exibirMenu();
                int opcao = lerOpcao();
                continuarJogo = processarOpcao(opcao);
                
                // Verifica se atingiu 5 operações válidas
                if (operacoesValidas >= 5) {
                    finalizarJogo();
                    continuarJogo = false;
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro fatal na aplicação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha recursos
            if (alunoDAO != null) {
                alunoDAO.fechar();
            }
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("👋 Jogo encerrado! Obrigado por jogar!");
        }
    }
    
    /**
     * Exibe o menu principal
     */
    private static void exibirMenu() {
        System.out.println("\n📋 ========== MENU PRINCIPAL ==========");
        System.out.println("🎯 Pontuação atual: " + pontos + " ponto(s)");
        System.out.println("⚡ Operações válidas: " + operacoesValidas + "/5");
        System.out.println("\n1️⃣  - Inserir novo aluno");
        System.out.println("2️⃣  - Remover aluno");
        System.out.println("3️⃣  - Alterar dados do aluno");
        System.out.println("4️⃣  - Listar todos os alunos");
        System.out.println("5️⃣  - Obter aluno por matrícula");
        System.out.println("0️⃣  - Sair do jogo");
        System.out.print("\n👉 Escolha uma opção: ");
    }
    
    /**
     * Lê a opção do usuário
     */
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Processa a opção escolhida
     */
    private static boolean processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                inserirAluno();
                break;
            case 2:
                removerAluno();
                break;
            case 3:
                alterarAluno();
                break;
            case 4:
                listarAlunos();
                break;
            case 5:
                obterAlunoPorMatricula();
                break;
            case 0:
                System.out.println("👋 Saindo do jogo...");
                return false;
            default:
                System.out.println("❌ Opção inválida! Tente novamente.");
                break;
        }
        return true;
    }
    
    /**
     * Insere um novo aluno
     */
    private static void inserirAluno() {
        System.out.println("\n📝 ========== INSERIR ALUNO ==========");
        
        System.out.print("📌 Matrícula: ");
        String matricula = scanner.nextLine();
        
        // Verifica se matrícula já existe
        Aluno existente = alunoDAO.obter(matricula);
        if (existente != null) {
            System.out.println("❌ Já existe um aluno com esta matrícula!");
            return;
        }
        
        System.out.print("👤 Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("📅 Ano (ex: 2024): ");
        int ano = Integer.parseInt(scanner.nextLine());
        
        Aluno novoAluno = new Aluno(matricula, nome, ano);
        
        try {
            alunoDAO.incluir(novoAluno);
            pontos++;
            operacoesValidas++;
            System.out.println("🎉 +1 ponto! Total: " + pontos);
        } catch (Exception e) {
            System.out.println("❌ Falha ao inserir aluno!");
        }
    }
    
    /**
     * Remove um aluno por matrícula
     */
    private static void removerAluno() {
        System.out.println("\n🗑️ ========== REMOVER ALUNO ==========");
        
        System.out.print("📌 Matrícula do aluno a remover: ");
        String matricula = scanner.nextLine();
        
        // Verifica se aluno existe antes de remover
        Aluno aluno = alunoDAO.obter(matricula);
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }
        
        System.out.print("⚠️ Tem certeza que deseja remover " + aluno.getNome() + "? (s/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("s")) {
            try {
                alunoDAO.excluir(matricula);
                pontos++;
                operacoesValidas++;
                System.out.println("🎉 +1 ponto! Total: " + pontos);
            } catch (Exception e) {
                System.out.println("❌ Falha ao remover aluno!");
            }
        } else {
            System.out.println("❌ Operação cancelada!");
        }
    }
    
    /**
     * Altera dados de um aluno
     */
    private static void alterarAluno() {
        System.out.println("\n✏️ ========== ALTERAR ALUNO ==========");
        
        System.out.print("📌 Matrícula do aluno a alterar: ");
        String matricula = scanner.nextLine();
        
        Aluno aluno = alunoDAO.obter(matricula);
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }
        
        System.out.println("📋 Dados atuais:");
        System.out.println("   Nome: " + aluno.getNome());
        System.out.println("   Ano: " + aluno.getAno());
        
        System.out.println("\n📝 Insira os novos dados (deixe em branco para manter):");
        
        System.out.print("👤 Novo nome [" + aluno.getNome() + "]: ");
        String novoNome = scanner.nextLine();
        if (novoNome.trim().isEmpty()) {
            novoNome = aluno.getNome();
        }
        
        System.out.print("📅 Novo ano [" + aluno.getAno() + "]: ");
        String novoAnoStr = scanner.nextLine();
        int novoAno = aluno.getAno();
        if (!novoAnoStr.trim().isEmpty()) {
            novoAno = Integer.parseInt(novoAnoStr);
        }
        
        Aluno alunoAlterado = new Aluno(matricula, novoNome, novoAno);
        
        try {
            alunoDAO.alterar(alunoAlterado);
            pontos++;
            operacoesValidas++;
            System.out.println("🎉 +1 ponto! Total: " + pontos);
        } catch (Exception e) {
            System.out.println("❌ Falha ao alterar aluno!");
        }
    }
    
    /**
     * Lista todos os alunos
     */
    private static void listarAlunos() {
        System.out.println("\n📋 ========== LISTAR ALUNOS ==========");
        
        List<Aluno> alunos = alunoDAO.obterTodos();
        
        if (alunos.isEmpty()) {
            System.out.println("📭 Nenhum aluno cadastrado no baralho!");
        } else {
            System.out.println("🎴 BARALHO DE CARTAS - Total: " + alunos.size());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for (int i = 0; i < alunos.size(); i++) {
                System.out.println((i + 1) + "️⃣ " + alunos.get(i));
            }
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        
        // Listar não dá ponto (apenas leitura)
    }
    
    /**
     * Obtém um aluno por matrícula
     */
    private static void obterAlunoPorMatricula() {
        System.out.println("\n🔍 ========== BUSCAR ALUNO ==========");
        
        System.out.print("📌 Matrícula: ");
        String matricula = scanner.nextLine();
        
        Aluno aluno = alunoDAO.obter(matricula);
        
        if (aluno != null) {
            System.out.println("\n✅ ALUNO ENCONTRADO:");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println(aluno);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        } else {
            System.out.println("❌ Nenhum aluno encontrado com a matrícula informada!");
        }
        
        // Buscar não dá ponto (apenas consulta)
    }
    
    /**
     * Finaliza o jogo com pontuação e listagem final
     */
    private static void finalizarJogo() {
        System.out.println("\n🏆 ========== FIM DE JOGO ========== 🏆");
        System.out.println("🎉 PARABÉNS! Você completou 5 operações válidas!");
        System.out.println("⭐ PONTUAÇÃO FINAL: " + pontos + " ponto(s)!");
        System.out.println("\n📊 BOLETIM FINAL:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✅ Operações realizadas: " + operacoesValidas);
        System.out.println("⭐ Pontos acumulados: " + pontos);
        
        System.out.println("\n🎴 BARALHO FINAL - Alunos remanescentes:");
        List<Aluno> alunos = alunoDAO.obterTodos();
        
        if (alunos.isEmpty()) {
            System.out.println("📭 O baralho está vazio!");
        } else {
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for (int i = 0; i < alunos.size(); i++) {
                System.out.println((i + 1) + "️⃣ " + alunos.get(i));
            }
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        
        System.out.println("\n🎮 OBRIGADO POR JOGAR SUPER TRUNFO!");
        System.out.println("🌟 Continue praticando e melhorando seu baralho!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}