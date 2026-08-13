package br.edu.ifsp.naoki.testes;

import br.edu.ifsp.naoki.dao.AlunoDao;
import br.edu.ifsp.naoki.modelo.Aluno;
import br.edu.ifsp.naoki.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CadastroDeAluno {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            exibirMenu();
            System.out.print("Digite a opção desejada: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número de 1 a 6.\n");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner);
                    break;
                case 2:
                    excluirAluno(scanner);
                    break;
                case 3:
                    alterarAluno(scanner);
                    break;
                case 4:
                    buscarAlunoPorNome(scanner);
                    break;
                case 5:
                    listarAlunos();
                    break;
                case 6:
                    System.out.println("\nFIM");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número de 1 a 6.\n");
            }
        } while (opcao != 6);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n** CADASTRO DE ALUNOS **\n");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Excluir aluno");
        System.out.println("3 - Alterar aluno");
        System.out.println("4 - Buscar aluno pelo nome");
        System.out.println("5 - Listar alunos (com status aprovação)");
        System.out.println("6 - FIM\n");
    }

    // 1 - Cadastrar aluno
    private static void cadastrarAluno(Scanner scanner) {
        System.out.println("\n--- CADASTRAR ALUNO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("RA: ");
        String ra = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        BigDecimal nota1 = lerNota(scanner, "Nota 1: ");
        BigDecimal nota2 = lerNota(scanner, "Nota 2: ");
        BigDecimal nota3 = lerNota(scanner, "Nota 3: ");

        Aluno aluno = new Aluno(nome, ra, email, nota1, nota2, nota3);

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao = new AlunoDao(em);

        try {
            em.getTransaction().begin();
            dao.cadastrar(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno cadastrado com sucesso! ID gerado: " + aluno.getId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // 2 - Excluir aluno
    private static void excluirAluno(Scanner scanner) {
        System.out.println("\n--- EXCLUIR ALUNO ---");
        System.out.print("Informe o ID do aluno a ser excluído: ");
        Long id = lerId(scanner);
        if (id == null) return;

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao = new AlunoDao(em);

        try {
            Aluno aluno = dao.buscarPorId(id);
            if (aluno == null) {
                System.out.println("Aluno não encontrado para o ID: " + id);
                return;
            }

            System.out.println("Aluno encontrado: " + aluno.getNome() + " (RA: " + aluno.getRa() + ")");
            System.out.print("Confirma a exclusão? (S/N): ");
            String confirma = scanner.nextLine();

            if (confirma.equalsIgnoreCase("S")) {
                em.getTransaction().begin();
                dao.excluir(aluno);
                em.getTransaction().commit();
                System.out.println("Aluno excluído com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // 3 - Alterar aluno
    private static void alterarAluno(Scanner scanner) {
        System.out.println("\n--- ALTERAR ALUNO ---");
        System.out.print("Informe o ID do aluno a ser alterado: ");
        Long id = lerId(scanner);
        if (id == null) return;

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao = new AlunoDao(em);

        try {
            Aluno aluno = dao.buscarPorId(id);
            if (aluno == null) {
                System.out.println("Aluno não encontrado para o ID: " + id);
                return;
            }

            System.out.println("Dados atuais do aluno:");
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("RA: " + aluno.getRa());
            System.out.println("Email: " + aluno.getEmail());
            System.out.println("Notas: " + aluno.getNota1() + " - " + aluno.getNota2() + " - " + aluno.getNota3());
            System.out.println("----------------------------------------");

            System.out.print("Novo Nome (pressione ENTER para manter): ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                aluno.setNome(nome);
            }

            System.out.print("Novo RA (pressione ENTER para manter): ");
            String ra = scanner.nextLine();
            if (!ra.trim().isEmpty()) {
                aluno.setRa(ra);
            }

            System.out.print("Novo Email (pressione ENTER para manter): ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                aluno.setEmail(email);
            }

            System.out.print("Deseja alterar as notas? (S/N): ");
            String alteraNotas = scanner.nextLine();
            if (alteraNotas.equalsIgnoreCase("S")) {
                aluno.setNota1(lerNota(scanner, "Nova Nota 1: "));
                aluno.setNota2(lerNota(scanner, "Nova Nota 2: "));
                aluno.setNota3(lerNota(scanner, "Nova Nota 3: "));
            }

            em.getTransaction().begin();
            dao.alterar(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno alterado com sucesso!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao alterar aluno: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // 4 - Buscar aluno pelo nome
    private static void buscarAlunoPorNome(Scanner scanner) {
        System.out.println("\n--- BUSCAR ALUNO POR NOME ---");
        System.out.print("Digite o nome ou trecho do nome: ");
        String nome = scanner.nextLine();

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao = new AlunoDao(em);

        try {
            List<Aluno> alunos = dao.buscarPorNome(nome);
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado contendo '" + nome + "'.");
            } else {
                System.out.println("\nExibindo alunos encontrados:\n");
                for (Aluno a : alunos) {
                    exibirDetalhesAluno(a);
                }
            }
        } finally {
            em.close();
        }
    }

    // 5 - Listar alunos (com status aprovação)
    private static void listarAlunos() {
        System.out.println("\nExibindo todos os alunos:\n");

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao = new AlunoDao(em);

        try {
            List<Aluno> alunos = dao.buscarTodos();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado até o momento.");
            } else {
                for (Aluno a : alunos) {
                    exibirDetalhesAluno(a);
                }
            }
        } finally {
            em.close();
        }
    }

    private static void exibirDetalhesAluno(Aluno a) {
        System.out.println("Nome: " + a.getNome());
        System.out.println("Email: " + a.getEmail());
        System.out.println("RA: " + a.getRa());
        System.out.println("Notas: " + a.getNota1() + " - " + a.getNota2() + " - " + a.getNota3());
        System.out.println("Media: " + a.getMedia());
        System.out.println("Situação: " + a.getSituacao() + "\n");
    }

    private static BigDecimal lerNota(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String input = scanner.nextLine().replace(',', '.');
                return new BigDecimal(input);
            } catch (Exception e) {
                System.out.println("Nota inválida! Digite um valor numérico (ex: 8.5).");
            }
        }
    }

    private static Long lerId(Scanner scanner) {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return null;
        }
    }
}
