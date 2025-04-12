/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.io.Serial;
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
public class PessoaJuridicaRepo implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    // Atributo estático que representa a "base de dados" em memória
    private ArrayList<PessoaJuridica> pessoa_juridica_array = new ArrayList<>();

    // Método para inserir uma nova PessoaJuridica
    public void inserir(PessoaJuridica pessoa_juridica) {
        pessoa_juridica_array.add(pessoa_juridica);
    }

    // Método para alterar um objeto existente (baseado em equals)
    public void alterar(PessoaJuridica pessoa_juridica) throws ExcecaoPertencimentoPessoaJuridicaRepo {
        int index = pessoa_juridica_array.indexOf(pessoa_juridica);
        if (index != -1) {
            pessoa_juridica_array.set(index, pessoa_juridica);
        } else {
            throw new ExcecaoPertencimentoPessoaJuridicaRepo("O elemento do tipo PessoaJuridica passado como argumento para o método 'alterar' não pertence à lista.");
        }
    }

    // Método para excluir um elemento com base no atributo 'id' de PessoaJuridica
    public void excluir(int id) throws ExcecaoIndiceForaDosLimites {
        boolean removido = pessoa_juridica_array.removeIf(p -> p != null && p.getId() == id);
        if (!removido) {
            throw new ExcecaoIndiceForaDosLimites("Nenhum elemento com o ID fornecido foi encontrado.");
        }
    }

    // Método para obter um elemento com base no atributo 'id' de PessoaJuridica
    public PessoaJuridica obter(int id) throws ExcecaoIndiceForaDosLimites {
        return pessoa_juridica_array.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ExcecaoIndiceForaDosLimites("ID não encontrado na lista."));
    }

    // Método para obter a lista de todos os elementos cadastrados
    public ArrayList<PessoaJuridica> obterTodos() {
        return this.pessoa_juridica_array;
    }

    // Método para gravar os dados da lista em um arquivo CSV, caso ele ainda não exista ou possa ser sobrescrito ou acrescido ao final
    public void persistir(String nomeArquivo, boolean sobrescrever, boolean append) throws IOException {
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo já existe
        if (arquivo.exists()) {
            // Verifica se o arquivo ser sobrescrito ou acrescido ao final, caso contrário lança uma exceção
            if (!sobrescrever && !append) {
                throw new IOException("O arquivo '" + nomeArquivo + "' já existe e os argumentos 'sobrescrever' e 'append' possuem o valor 'false'.");
            }
            // Caso sobrescrever seja true e append false, simplesmente continua com escrita sem append (isto é, sobrescrevendo o arquivo)
        }

        // Variável que guarda o valor booleano da verificação das condições para a escrita do cabeçalho
        boolean escreverCabecalho = !arquivo.exists() || (sobrescrever && !append);

        // Tenta dar início ao processo de escrita no arquivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, append))) {
            // Escreve a primeira linha com os nomes dos atributos (cabeçalho), mas apenas se as condições
            // anteriormente estipuladas forem atendidas
            if (escreverCabecalho) {
                escritor.write("id,nome,cnpj");
                escritor.newLine();
            }

            // Escreve cada objeto da lista como uma linha CSV
            for (PessoaJuridica pf : pessoa_juridica_array) {
                String linha = String.format("%d,%s,%s",
                    pf.getId(),
                    pf.getNome(),
                    pf.getCNPJ()
                );
                escritor.write(linha);
                escritor.newLine();
            }
            // Exibe mensagem informando que os dados foram armazenados com sucesso no arquivo especificado
            System.out.println("Dados do objeto da classe 'PessoaJuridica' armazenados no arquivo: " + arquivo.getName());
        }
    }

    // Método para ler os dados de um arquivo CSV (se ele existir) e preencher a lista
    public void recuperar(String nomeArquivo) throws IOException {
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe. Caso contrário, lança uma exceção.
        if (!arquivo.exists()) {
            throw new FileNotFoundException("O arquivo '" + nomeArquivo + "' não foi encontrado.");
        }

        // Tenta dar início ao processo de leitura do arquivo
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            // Lê e valida o cabeçalho
            String linhaCabecalho = leitor.readLine();
            if (linhaCabecalho == null) {
                throw new IOException("Cabeçalho ausente no arquivo.");
            }

            String[] cabecalhos = linhaCabecalho.trim().toLowerCase().split(",");
            // Verifica o vetor de strings contendo os nomes dos atributos no cabeçalho possui exatamente 3 elementos
            if (cabecalhos.length != 3) {
                throw new IOException("Cabeçalho incompleto. Esperado: id,nome,cnpj");
            }

            if (!cabecalhos[0].equals("id") || !cabecalhos[1].equals("nome") ||
                !cabecalhos[2].equals("cnpj")) {
                throw new IOException("Cabeçalho inválido. Esperado exatamente: id,nome,cnpj");
            }

            // Limpa a lista antes de repopular (i.e., de preenchê-la novamente)
            pessoa_juridica_array.clear();

            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; // Ignora linhas em branco

                String[] partes = linha.split(",");
                // Verifica se cada vetor de strings contendo os valores dos atributos de cada
                // objeto da classe 'PessoaJuridica' contido em cada linha do arquivo
                // possui exatamente 3 elementos:
                if (partes.length != 3) {
                    System.err.println("Linha inválida (esperado 3 campos): " + linha);
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String nome = partes[1].trim();
                    String cnpj = partes[2].trim();

                    // Usa o construtor completo de PessoaJuridica
                    PessoaJuridica pf = new PessoaJuridica(nome, id, cnpj);
                    pessoa_juridica_array.add(pf);
                } catch (NumberFormatException | ExcecaoValidacaoCNPJ e) {
                    System.err.println("Erro ao processar linha: '" + linha + "'. Motivo: " + e.getMessage());
                }
            }
            // Exibe mensagem informando que os dados foram recuperados com sucesso do arquivo especificado
            System.out.println("Dados do objeto da classe 'PessoaJuridica' recuperados do arquivo: " + arquivo.getName());
        }
    }
}

// Exceção para índice inválido
class ExcecaoIndiceForaDosLimites extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 11L;
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
class ExcecaoPertencimentoPessoaJuridicaRepo extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 12L;
    private String msg_erro;

    public ExcecaoPertencimentoPessoaJuridicaRepo() {}

    public ExcecaoPertencimentoPessoaJuridicaRepo(String mensagem) {
        super(mensagem);
        this.msg_erro = mensagem;
    }

    @Override
    public String toString() {
        return "ErroPertencimentoPessoaJuridicaRepo: " + this.msg_erro;
    }
}
