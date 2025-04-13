/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastropoo;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.*;

public class CadastroPOO2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaRepo repoFisica = new PessoaFisicaRepo();
        PessoaJuridicaRepo repoJuridica = new PessoaJuridicaRepo();
        int opcao = -1;

        // Loop principal do menu em modo texto
        do {
            try {
                System.out.println("\n==============================");
                System.out.println("1 - Incluir Pessoa");
                System.out.println("2 - Alterar Pessoa");
                System.out.println("3 - Excluir Pessoa");
                System.out.println("4 - Buscar pelo Id");
                System.out.println("5 - Exibir Todos");
                System.out.println("6 - Persistir Dados");
                System.out.println("7 - Recuperar Dados");
                System.out.println("0 - Finalizar Programa");
                System.out.println("==============================");
                System.out.print("Digite uma opção (0-7): ");

                String entrada = scanner.nextLine();
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1 -> incluirPessoa(scanner, repoFisica, repoJuridica); // Incluir novo cadastro
                    case 2 -> alterarPessoa(scanner, repoFisica, repoJuridica); // Alterar dados de cadastro existente
                    case 3 -> excluirPessoa(scanner, repoFisica, repoJuridica); // Excluir pessoa por ID
                    case 4 -> buscarPessoa(scanner, repoFisica, repoJuridica);  // Exibir uma pessoa pelo ID
                    case 5 -> exibirTodos(scanner, repoFisica, repoJuridica);   // Exibir todos os cadastros
                    case 6 -> persistirDados(scanner, repoFisica, repoJuridica); // Salvar dados em arquivos binários
                    case 7 -> recuperarDados(scanner, repoFisica, repoJuridica); // Carregar dados de arquivos binários
                    case 0 -> System.out.println("Encerrando o programa...");
                    default -> System.out.println("Opção inválida. Digite um número de 0 a 7.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite apenas números inteiros de 0 a 7.");
            } catch (InputMismatchException e) {
                System.out.println("Erro de tipo de entrada. Digite apenas números inteiros.");
                scanner.nextLine(); // limpa o buffer do scanner
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);

        scanner.close();
    }

    // Função para incluir Pessoa Física ou Jurídica
    private static void incluirPessoa(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            System.out.print("Digite o id da pessoa: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Verifica duplicidade de ID antes de incluir
            if ("F".equals(tipo)) {
                for (PessoaFisica pf : repoFisica.obterTodos()) {
                    if (pf.getId() == id) {
                        System.out.println("Erro ao incluir Pessoa Física: A 'id' inserida já se encontra presente na atual lista de Pessoas Físicas.");
                        System.out.println("Por favor escolher uma 'id' diferente, ou antes alterar a 'id' já existente.");
                        return;
                    }
                }
                System.out.println("Insira os dados...");
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Idade: ");
                int idade = Integer.parseInt(scanner.nextLine());

                PessoaFisica pf = new PessoaFisica(nome, id, cpf, idade);
                repoFisica.inserir(pf);

            } else if ("J".equals(tipo)) {
                for (PessoaJuridica pj : repoJuridica.obterTodos()) {
                    if (pj.getId() == id) {
                        System.out.println("Erro ao incluir Pessoa Jurídica: A 'id' inserida já se encontra presente na atual lista de Pessoas Jurídicas.");
                        System.out.println("Por favor escolher uma 'id' diferente, ou antes alterar a 'id' já existente.");
                        return;
                    }
                }
                System.out.println("Insira os dados...");
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("CNPJ: ");
                String cnpj = scanner.nextLine();

                PessoaJuridica pj = new PessoaJuridica(nome, id, cnpj);
                repoJuridica.inserir(pj);

            } else {
                System.out.println("Tipo inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir pessoa: " + e.getMessage());
        }
    }

    // Função para alterar uma pessoa com possibilidade de alterar o ID também
    private static void alterarPessoa(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            System.out.print("Digite o id da pessoa: ");
            int id = Integer.parseInt(scanner.nextLine());

            if ("F".equals(tipo)) {
                PessoaFisica pf = repoFisica.obter(id);
                pf.exibir();

                // Copia de segurança
                String nomeAntigo = pf.getNome();
                String cpfAntigo = pf.getCPF();
                int idadeAntiga = pf.getIdade();
                int idAntigo = pf.getId();

                System.out.println("Digite os novos dados...");
                System.out.print("Novo Id: ");
                int novoId = Integer.parseInt(scanner.nextLine());
                if (novoId != id) {
                    for (PessoaFisica outro : repoFisica.obterTodos()) {
                        if (outro.getId() == novoId) {
                            System.out.println("Erro ao alterar Pessoa Física: A 'id' inserida já se encontra presente na atual lista. Por favor escolha uma 'id' diferente.");
                            return;
                        }
                    }
                }
                System.out.print("Nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("CPF: ");
                String novoCpf = scanner.nextLine();
                System.out.print("Idade: ");
                int novaIdade = Integer.parseInt(scanner.nextLine());

                try {
                    pf.setId(novoId);
                    pf.setNome(novoNome);
                    pf.setCPF(novoCpf);
                    pf.setIdade(novaIdade);
                    repoFisica.alterar(pf);
                } catch (Exception e) {
                    // Restaura valores anteriores
                    pf.setId(idAntigo);
                    pf.setNome(nomeAntigo);
                    pf.setCPF(cpfAntigo);
                    pf.setIdade(idadeAntiga);
                    System.out.println("Erro ao alterar dados: " + e.getMessage());
                }

            } else if ("J".equals(tipo)) {
                PessoaJuridica pj = repoJuridica.obter(id);
                pj.exibir();

                String nomeAntigo = pj.getNome();
                String cnpjAntigo = pj.getCNPJ();
                int idAntigo = pj.getId();

                System.out.println("Digite os novos dados...");
                System.out.print("Novo Id: ");
                int novoId = Integer.parseInt(scanner.nextLine());
                if (novoId != id) {
                    for (PessoaJuridica outro : repoJuridica.obterTodos()) {
                        if (outro.getId() == novoId) {
                            System.out.println("Erro ao alterar Pessoa Jurídica: A 'id' inserida já se encontra presente na atual lista. Por favor escolha uma 'id' diferente.");
                            return;
                        }
                    }
                }
                System.out.print("Nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("CNPJ: ");
                String novoCnpj = scanner.nextLine();

                try {
                    pj.setId(novoId);
                    pj.setNome(novoNome);
                    pj.setCNPJ(novoCnpj);
                    repoJuridica.alterar(pj);
                } catch (Exception e) {
                    pj.setId(idAntigo);
                    pj.setNome(nomeAntigo);
                    pj.setCNPJ(cnpjAntigo);
                    System.out.println("Erro ao alterar dados: " + e.getMessage());
                }

            } else {
                System.out.println("Tipo inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar pessoa: " + e.getMessage());
        }
    }

    // Excluir pessoa por ID
    private static void excluirPessoa(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            System.out.print("Digite o id da pessoa: ");
            int id = Integer.parseInt(scanner.nextLine());

            if ("F".equals(tipo)) {
                repoFisica.excluir(id);
            } else if ("J".equals(tipo)) {
                repoJuridica.excluir(id);
            } else {
                System.out.println("Tipo inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir pessoa: " + e.getMessage());
        }
    }

    // Buscar pessoa pelo ID e exibir seus dados
    private static void buscarPessoa(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            System.out.print("Digite o id da pessoa: ");
            int id = Integer.parseInt(scanner.nextLine());

            if ("F".equals(tipo)) {
                repoFisica.obter(id).exibir();
            } else if ("J".equals(tipo)) {
                repoJuridica.obter(id).exibir();
            } else {
                System.out.println("Tipo inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
        }
    }

    // Exibe todos os registros de Pessoa Física ou Jurídica
    private static void exibirTodos(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        if ("F".equals(tipo)) {
            for (PessoaFisica pf : repoFisica.obterTodos()) {
                pf.exibir();
                System.out.println();
            }
        } else if ("J".equals(tipo)) {
            for (PessoaJuridica pj : repoJuridica.obterTodos()) {
                pj.exibir();
                System.out.println();
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    // Persiste os dados dos repositórios em arquivos binários com verificação de sobrescrição
    private static void persistirDados(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.print("Digite o prefixo para os arquivos: ");
        String prefixo = scanner.nextLine();

        File arquivoFisica = new File(prefixo + ".fisica.bin");
        File arquivoJuridica = new File(prefixo + ".juridica.bin");

        // Solicita confirmação antes de sobrescrever arquivos existentes
        if (arquivoFisica.exists() || arquivoJuridica.exists()) {
            System.out.println("AVISO: já existem arquivos salvos com o mesmo prefixo fornecido.");
            System.out.println("Deseja mesmo sobrescrever esses arquivos?");
            System.out.println("S - Sim | N - Não");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Operação de persistência cancelada.");
                return;
            }
        }

        try {
            boolean ok1 = repoFisica.persistir(prefixo, true, false);
            boolean ok2 = repoJuridica.persistir(prefixo, true, false);
            if (ok1 && ok2) {
                System.out.println("Dados salvos com sucesso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // Recupera dados salvos anteriormente a partir dos arquivos binários
    private static void recuperarDados(Scanner scanner, PessoaFisicaRepo repoFisica, PessoaJuridicaRepo repoJuridica) {
        System.out.print("Digite o prefixo dos arquivos: ");
        String prefixo = scanner.nextLine();

        try {
            boolean ok1 = repoFisica.recuperar(prefixo);
            boolean ok2 = repoJuridica.recuperar(prefixo);
            if (ok1 && ok2) {
                System.out.println("Dados recuperados com sucesso.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao recuperar dados: " + e.getMessage());
        }
    }
}
