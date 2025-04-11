/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 *
 * @author Ivan
 */
public class PessoaFisicaRepo {
    // Atributo estático que representa a "base de dados" em memória
    private ArrayList<PessoaFisica> pessoa_fisica_array = new ArrayList<>();

    // Método para inserir uma nova PessoaFisica
    public void inserir(PessoaFisica pessoa_fisica) {
        pessoa_fisica_array.add(pessoa_fisica);
    }

    // Método para alterar um objeto existente (baseado em equals)
    public void alterar(PessoaFisica pessoa_fisica) throws ExcecaoPertencimentoPessoaFisicaRepo {
        int index = pessoa_fisica_array.indexOf(pessoa_fisica);
        if (index != -1) {
            pessoa_fisica_array.set(index, pessoa_fisica);
        } else {
            throw new ExcecaoPertencimentoPessoaFisicaRepo("O elemento do tipo PessoaFisica passado como argumento para o método 'alterar' não pertence à lista.");
        }
    }

    // Método para excluir um elemento com base no atributo 'id' de PessoaFisica
    public void excluir(int id) throws ExcecaoIndiceForaDosLimites {
        boolean removido = pessoa_fisica_array.removeIf(p -> p != null && p.getId() == id);
        if (!removido) {
            throw new ExcecaoIndiceForaDosLimites("Nenhum elemento com o ID fornecido foi encontrado.");
        }
    }

    // Método para obter um elemento com base no atributo 'id' de PessoaFisica
    public PessoaFisica obter(int id) throws ExcecaoIndiceForaDosLimites {
        return pessoa_fisica_array.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ExcecaoIndiceForaDosLimites("ID não encontrado na lista."));
    }

    // Método para obter a lista de todos os elementos cadastrados
    public ArrayList<PessoaFisica> obterTodos() {
        return this.pessoa_fisica_array;
    }

    // Método para gravar os dados da lista em um arquivo CSV, caso ele ainda não exista e possa ser sobrescrito
    public void persistir(String nomeArquivo, boolean sobrescrever) throws IOException {
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo já existe e se ele pode ser sobrescrito
        if (!sobrescrever && arquivo.exists()) {
            throw new IOException("O arquivo '" + nomeArquivo + "' já existe e o argumento 'sobrescrever' do método 'persistir' tem o valor 'false'.");
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            // Escreve a primeira linha com os nomes dos atributos (cabeçalho)
            escritor.write("id,nome,cpf,idade");
            escritor.newLine();

            // Escreve cada objeto da lista como uma linha CSV
            for (PessoaFisica pf : pessoa_fisica_array) {
                escritor.write(pf.getId() + "," + pf.getNome() + "," + pf.getCPF() + "," + pf.getIdade());
                escritor.newLine();
            }
        }
    }

    // Método para ler os dados de um arquivo CSV (se ele existir) e popular a lista
    public void recuperar(String nomeArquivo) throws IOException {
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe antes de tentar ler
        if (!arquivo.exists()) {
            throw new FileNotFoundException("O arquivo '" + nomeArquivo + "' não foi encontrado.");
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            // Lê a primeira linha e valida o cabeçalho
            String linhaCabecalho = leitor.readLine();
            if (linhaCabecalho == null || !linhaCabecalho.trim().equalsIgnoreCase("id,nome,cpf,idade")) {
                throw new IOException("Cabeçalho do arquivo inválido ou ausente.");
            }

            // Limpa a lista antes de repopular
            pessoa_fisica_array.clear();

            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; // Ignora linhas em branco

                String[] partes = linha.split(",");
                if (partes.length != 4) {
                    System.err.println("Linha inválida (esperado 4 campos): " + linha);
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String nome = partes[1].trim();
                    String cpf = partes[2].trim();
                    int idade = Integer.parseInt(partes[3].trim());

                    // Cria a instância usando o construtor com todos os dados
                    PessoaFisica pf = new PessoaFisica(nome, id, cpf, idade);
                    pessoa_fisica_array.add(pf);
                } catch (NumberFormatException | ExcecaoValidacaoCPF | ExcecaoValidacaoIdade e) {
                    System.err.println("Erro ao processar linha: '" + linha + "'. Motivo: " + e.getMessage());
                }
            }
        }
    }
}

// Exceção para índice inválido
class ExcecaoIndiceForaDosLimites extends Exception implements Serializable {
    private static final long serialVersionUID = 325034835210430846L;
    private String msg_erro;

    public ExcecaoIndiceForaDosLimites() {}

    public ExcecaoIndiceForaDosLimites(String mensagem) {
        super(mensagem);
        this.msg_erro = mensagem;
    }

    @Override
    public String toString() {
        return "ErroIndiceForaDosLimites: " + this.msg_erro;
    }
}

// Exceção para elemento que não pertence à lista
class ExcecaoPertencimentoPessoaFisicaRepo extends Exception implements Serializable {
    private static final long serialVersionUID = 4137324869349831550L;
    private String msg_erro;

    public ExcecaoPertencimentoPessoaFisicaRepo() {}

    public ExcecaoPertencimentoPessoaFisicaRepo(String mensagem) {
        super(mensagem);
        this.msg_erro = mensagem;
    }

    @Override
    public String toString() {
        return "ErroPertencimentoPessoaFisicaRepo: " + this.msg_erro;
    }
}
