/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastropoo;

import model.*;
import java.io.IOException;

/**
 *
 * @author Ivan
 */
public class CadastroPOO1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Criação de Repositório de Pessoas físicas
        PessoaFisicaRepo repo1 = new PessoaFisicaRepo();
        
        // Criação de Pessoas Físicas
        try {
            PessoaFisica pf1 = new PessoaFisica("Ivan Mortimer", 1, "05398724665", 44);
            PessoaFisica pf2 = new PessoaFisica("Nicolas Mortimer", 2, "18338726656", 5);
            
            // Adição das Pessoas Físicas criadas ao repositório 'repo1'
            repo1.inserir(pf1);
            repo1.inserir(pf2);
            
            // Garantir a persistência dos dados do repositório gravando-os no arquivo "repo1.csv"
            try {
                repo1.persistir("repo1", true, false);
            }
            catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
        catch (ExcecaoValidacaoCPF e) {
            System.out.println(e.toString());
        }
        catch (ExcecaoValidacaoIdade e) {
            System.out.println(e.toString());
        }
        // Criação de novo Repositório de Pessoas físicas
        PessoaFisicaRepo repo2 = new PessoaFisicaRepo();

        // Recuperar dados do arquivo "repo1.csv" e adicioná-los ao repositório 'repo2'
        try {
            repo2.recuperar("repo1");
            System.out.println();
            for (PessoaFisica pessoa_fisica : repo2.obterTodos()) {
                pessoa_fisica.exibir();
                System.out.println();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        
        
        // Criação de Repositório de Pessoas Jurídicas
        PessoaJuridicaRepo repo3 = new PessoaJuridicaRepo();
        
        // Criação de Pessoas Físicas
        try {
            PessoaJuridica pj1 = new PessoaJuridica("Empresa Brasileira de Correios e Telégrafos", 1, "34028316000103");
            PessoaJuridica pj2 = new PessoaJuridica("Banco do Brasil S.A.", 2, "00000000000191");
            
            // Adição das Pessoas Físicas criadas ao repositório 'repo1'
            repo3.inserir(pj1);
            repo3.inserir(pj2);
            
            // Garantir a persistência dos dados do repositório gravando-os no arquivo "repo1.csv"
            try {
                repo3.persistir("repo3", true, false);
            }
            catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
        catch (ExcecaoValidacaoCNPJ e) {
            System.out.println(e.toString());
        }

        // Criação de novo Repositório de Pessoas Jurídicas
        PessoaJuridicaRepo repo4 = new PessoaJuridicaRepo();

        // Recuperar dados do arquivo "repo1.csv" e adicioná-los ao repositório 'repo2'
        try {
            repo4.recuperar("repo3");
            System.out.println();
            for (PessoaJuridica pessoa_juridica : repo4.obterTodos()) {
                pessoa_juridica.exibir();
                System.out.println();
            }
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
