package com.supertrufo;

import java.util.List;
import java.util.Scanner;

public class SuperTrunfoGame {
    
    private static AlunoDAO alunoDAO;
    private static Scanner scanner;
    private static int pontos = 0;
    private static int operacoesValidas = 0;
    
    public static void main(String[] args) {
        System.out.println("🎮 SUPER TRUNFO EDUCAÇÃO - Nível Aventureiro");
        System.out.println("✨ Cada operação bem-sucedida vale 1 ponto!");
        System.out.println("🏆 Após 5 pontos, o jogo termina!\n");
        
        try {
            alunoDAO = new AlunoDAO();
            scanner = new Scanner(System.in);
            
            while (operacoesValidas < 5) {
                exibirMenu();
                int opcao = lerOpcao();
                if (!processarOpcao(opcao)) {
                    break;
                }
            }
            
            if (operacoesValidas >= 5) {
                finalizarJogo();
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (alunoDAO != null) alunoDAO.fechar();
            if (scanner != null) scanner.close();
        }
    }
    
    private static void exibirMenu() {
        System.out.println("\n📋 ========== MENU ==========");
        System.out.println("⭐ Pontuação: " + pontos + " pontos");
        System.out.println("🎯 Operações válidas: " + operacoesValidas + "/5");
        System.out.println("\n1️⃣ - Inserir aluno");
        System.out.println("2️⃣ - Remover aluno");
        System.out.println("3️⃣ - Alterar aluno");
        System.out.println("4️⃣ - Listar todos");
        System.out.println("5️⃣ - Buscar por matrícula");
        System.out.println("0️⃣ - Sair");
        System.out.print("\n👉 Escolha: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
    
    private static boolean processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                inserirAluno();
                return true;
            case 2:
                removerAluno();
                return true;
            case 3:
                alterarAluno();
                return true;
            case 4:
                listarAlunos();
                return true;
            case 5:
                buscarAluno();
                return true;
            case 0:
                System.out.println("👋 Saindo...");
                return false;
            default:
                System.out.println("❌ Opção inválida!");
                return true;
        }
    }
    
    private static void inserirAluno() {
        System.out.println("\n📝 NOVO ALUNO");
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        
        if (alunoDAO.obter(matricula) != null) {
            System.out.println("❌ Matrícula já existe!");
            return;
        }
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        
        try {
            alunoDAO.incluir(new Aluno(matricula, nome, ano));
            pontos++;
            operacoesValidas++;
            System.out.println("🎉 +1 ponto! Total: " + pontos);
        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir: " + e.getMessage());
        }
    }
    
    private static void removerAluno() {
        System.out.println("\n🗑️ REMOVER ALUNO");
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        
        Aluno aluno = alunoDAO.obter(matricula);
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }
        
        System.out.print("Remover " + aluno.getNome() + "? (s/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("s")) {
            try {
                alunoDAO.excluir(matricula);
                pontos++;
                operacoesValidas++;
                System.out.println("🎉 +1 ponto! Total: " + pontos);
            } catch (Exception e) {
                System.out.println("❌ Erro ao remover: " + e.getMessage());
            }
        }
    }
    
    private static void alterarAluno() {
        System.out.println("\n✏️ ALTERAR ALUNO");
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        
        Aluno aluno = alunoDAO.obter(matricula);
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado!");
            return;
        }
        
        System.out.println("Dados atuais:");
        System.out.println("  Nome: " + aluno.getNome());
        System.out.println("  Ano: " + aluno.getAno());
        
        System.out.print("Novo nome (" + aluno.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isEmpty()) nome = aluno.getNome();
        
        System.out.print("Novo ano (" + aluno.getAno() + "): ");
        String anoStr = scanner.nextLine();
        int ano = anoStr.isEmpty() ? aluno.getAno() : Integer.parseInt(anoStr);
        
        try {
            alunoDAO.alterar(new Aluno(matricula, nome, ano));
            pontos++;
            operacoesValidas++;
            System.out.println("🎉 +1 ponto! Total: " + pontos);
        } catch (Exception e) {
            System.out.println("❌ Erro ao alterar: " + e.getMessage());
        }
    }
    
    private static void listarAlunos() {
        System.out.println("\n📋 LISTA DE ALUNOS");
        List<Aluno> alunos = alunoDAO.obterTodos();
        
        if (alunos.isEmpty()) {
            System.out.println("📭 Nenhum aluno cadastrado!");
        } else {
            System.out.println("Total: " + alunos.size() + " alunos\n");
            for (int i = 0; i < alunos.size(); i++) {
                System.out.println((i+1) + "️⃣ " + alunos.get(i));
            }
        }
    }
    
    private static void buscarAluno() {
        System.out.println("\n🔍 BUSCAR ALUNO");
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        
        Aluno aluno = alunoDAO.obter(matricula);
        if (aluno != null) {
            System.out.println("\n✅ Aluno encontrado:");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println(aluno);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
        } else {
            System.out.println("❌ Aluno não encontrado!");
        }
    }
    
    private static void finalizarJogo() {
        System.out.println("\n🏆 ========== FIM DE JOGO! ==========");
        System.out.println("🎉 Parabéns! Você completou 5 operações!");
        System.out.println("⭐ PONTUAÇÃO FINAL: " + pontos + " pontos!");
        System.out.println("\n📊 BARALHO FINAL:");
        
        List<Aluno> alunos = alunoDAO.obterTodos();
        if (alunos.isEmpty()) {
            System.out.println("📭 O baralho está vazio!");
        } else {
            System.out.println("Total de cartas: " + alunos.size());
            for (Aluno a : alunos) {
                System.out.println("  🃏 " + a);
            }
        }
        
        System.out.println("\n🌟 Obrigado por jogar Super Trunfo!");
        System.out.println("=====================================\n");
    }
}
