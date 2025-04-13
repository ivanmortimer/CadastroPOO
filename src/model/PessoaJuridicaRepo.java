/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.Serial;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.EOFException;

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

    // Método para gravar os dados da lista em um arquivo binário via serialização de objetos,
    // caso ele ainda não exista ou possa ser sobrescrito ou acrescido (appended) ao final
    /**
     * Persiste os objetos da lista em um arquivo binário usando serialização.
     * @param prefixoArquivo Prefixo do nome do arquivo.
     * @param sobrescrever Se true, sobrescreve o arquivo existente.
     * @param append Se true, adiciona ao final do arquivo existente.
     * @return true se bem-sucedido, false se ocorrer IOException.
     * @throws IOException Se houver erro de escrita ou conflito de opções.
     * @throws IllegalStateException Se a lista estiver nula.
     */
    public boolean persistir(String prefixoArquivo, boolean sobrescrever, boolean append)
            throws IOException, IllegalStateException {

        // Verifica se o array está inicializado
        if (pessoa_juridica_array == null) {
            throw new IllegalStateException("O array de pessoas físicas está nulo.");
        }

        // Verifica se o prefixo é válido
        if (prefixoArquivo == null || prefixoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O prefixo do nome do arquivo não pode ser nulo ou vazio.");
        }

        // Cria o nome do arquivo com extensão padronizada
        String nomeArquivo = prefixoArquivo + ".juridica.bin";
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo já existe
        if (arquivo.exists()) {
            if (!sobrescrever && !append) {
                throw new IOException("O arquivo '" + nomeArquivo +
                        "' já existe e os argumentos 'sobrescrever' e 'append' possuem o valor 'false'.");
            }
        }

        // Realiza a escrita dos objetos no arquivo
        try (FileOutputStream fos = new FileOutputStream(arquivo, append);
             ObjectOutputStream oos = append && arquivo.exists()
                     ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {

            for (PessoaJuridica pessoa : pessoa_juridica_array) {
                oos.writeObject(pessoa);
            }

            // Mensagem de sucesso
            System.out.println("Dados do objeto da classe 'PessoaJuridica' armazenados no arquivo: " + nomeArquivo);
            return true;

        } catch (IOException e) {
            // Mensagem de erro padrão
            System.err.println("Erro ao persistir dados no arquivo binário: " + e.getMessage());
            return false;
        }
    }

    // Método para ler os dados de um arquivo binário (se ele existir),
    // via deserialização de objetos, e preencher a lista
    /**
     * Recupera objetos serializados de um arquivo binário e armazena-os na lista.
     * @param prefixoArquivo Prefixo do nome do arquivo.
     * @return true se a recuperação for bem-sucedida.
     * @throws IOException Se houver erro de leitura.
     * @throws ClassNotFoundException Se a classe dos objetos não for encontrada.
     */
    @SuppressWarnings("unchecked")
    public boolean recuperar(String prefixoArquivo) throws IOException, ClassNotFoundException {
        // Verifica se o prefixo é válido
        if (prefixoArquivo == null || prefixoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O prefixo do nome do arquivo não pode ser nulo ou vazio.");
        }

        // Define o nome do arquivo com base no prefixo
        String nomeArquivo = prefixoArquivo + ".juridica.bin";
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe
        if (!arquivo.exists()) {
            throw new FileNotFoundException("O arquivo '" + nomeArquivo + "' não foi encontrado.");
        }

        // Lista temporária para armazenar os objetos recuperados
        List<PessoaJuridica> listaRecuperada = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            while (true) {
                try {
                    // Lê um objeto do arquivo e o adiciona à lista
                    Object obj = ois.readObject();
                    if (obj instanceof PessoaJuridica pessoa_juridica) {
                        listaRecuperada.add(pessoa_juridica);
                    } else {
                        throw new ClassCastException("Objeto lido não é do tipo PessoaJuridica.");
                    }
                } catch (EOFException eof) {
                    // Fim do arquivo atingido, sai do loop
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Mensagem de erro padrão
            System.err.println("Erro ao recuperar dados do arquivo binário: " + e.getMessage());
            throw e;
        }

        // Atribui a lista recuperada ao atributo da classe (com cast explícito)
        pessoa_juridica_array = (ArrayList<PessoaJuridica>) listaRecuperada;

        // Mensagem de sucesso
        System.out.println("Dados da classe 'PessoaJuridica' recuperados com sucesso do arquivo: " + nomeArquivo);
        return true;
    }
}

/**
 * Classe auxiliar que permite acrescentar objetos a um arquivo binário
 * sem reescrever o cabeçalho do stream.
 */
class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Serial
    @Override
    protected void writeStreamHeader() throws IOException {
        // Não escreve o cabeçalho novamente ao usar append
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
