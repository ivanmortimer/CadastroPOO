/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.InputMismatchException;

/**
 *
 * @author Ivan
 */
public class PessoaFisica extends Pessoa implements Serializable {
    // Atributos
    private String cpf;
    private int idade;
    
    // Construtores
    public PessoaFisica() {
        super();
        this.cpf = "";
        this.idade = 0;
    }
    public PessoaFisica(String cpf) throws ExcecaoValidacaoCPF {
        super();
        setCPF(cpf);
        this.idade = 0;
    }
    public PessoaFisica(String cpf, int idade) throws ExcecaoValidacaoCPF, ExcecaoValidacaoIdade {
        super();
        setCPF(cpf);
        setIdade(idade);
    }
    public PessoaFisica(String nome, String cpf, int idade) throws ExcecaoValidacaoCPF, ExcecaoValidacaoIdade {
        super(nome);
        setCPF(cpf);
        setIdade(idade);
    }
    public PessoaFisica(String nome, int id, String cpf, int idade) throws ExcecaoValidacaoCPF, ExcecaoValidacaoIdade {
        super(nome, id);
        setCPF(cpf);
        setIdade(idade);
    }
    
    // Métodos 'getter'
    public String getCPF() {
        return this.cpf;
    }
    public int getIdade() {
        return this.idade;
    }
    
    // Métodos 'setter'
    public final void setCPF(String cpf) throws ExcecaoValidacaoCPF {
        if (!validarCPF(cpf)) {
            throw new ExcecaoValidacaoCPF("CPF inválido!");
        }
        this.cpf = cpf;
    }
    public final void setIdade(int idade) throws ExcecaoValidacaoIdade {
        if (validarIdade(idade))
            this.idade = idade;
        else
            throw new ExcecaoValidacaoIdade("ERRO: o valor do atributo idade deve ser entre 0 e 150.");
    }

    // Método 'exibir' para impressão dos dados de PessoaFisica
    @Override
    public void exibir() {
        System.out.println("nome: " + this.getNome());
        System.out.println("id: " + this.getId());
        System.out.println("cpf: " + this.getCPF());
        System.out.println("idade: " + this.getIdade());
    }
    
    // Método 'validarCPF': recebe uma String e retorna true se o CPF for válido.
    // Caso contrário, lança uma exceção ExcecaoValidacaoCPF personalizada.
    protected boolean validarCPF(String CPF) throws ExcecaoValidacaoCPF {
        char DV10, DV11;
        int soma, num, peso, i, resto;

        // Verifica sequência de dígitos iguais e tamanho (11 dígitos)
        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") ||
            CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") ||
            CPF.equals("99999999999") || (CPF.length() != 11)) {
            throw new ExcecaoValidacaoCPF("Entrada inválida: o CPF deve ter exatamente 11 dígitos (em formato String) e seus dígitos não podem ser todos iguais.");
        }

        try {
            // 1º Dígito Verificador (10º dígito)
            soma = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                soma = soma + (num * peso);
                peso--;
            }
            resto = soma % 11;
            if ((resto == 0) || (resto == 1))
                DV10 = '0';
            else
                DV10 = (char) ((11 - resto) + 48);

            // 2º Dígito Verificador (11º dígito)
            soma = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                soma = soma + (num * peso);
                peso--;
            }
            resto = soma % 11;
            if ((resto == 0) || (resto == 1))
                DV11 = '0';
            else
                DV11 = (char) ((11 - resto) + 48);

            // Verifica se os Dígitos Verificadores informados coincidem com os calculados
            if ((DV10 == CPF.charAt(9)) && (DV11 == CPF.charAt(10)))
                return true;
            else
                throw new ExcecaoValidacaoCPF("Dígito Verificador (décimo e/ou décimo-primeiro) inválido.");
        } catch (InputMismatchException erro) {
            return false;
        }
    }
    
    // Método para validar idade (deve ficar entre os valores de 0 e 150)
    protected boolean validarIdade(int idade) {
        return !(idade < 0 || idade > 150);
    }
    
    // Métodos 'equals' e 'hashcode' alterados para conformá-los ao padrão da classe:
    // que usa o atributo 'id' ao invéz de um 'hashcode' para individualizar os objetos
    // do tipo 'Pessoa' e 'PessoaFisica'
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa other = (Pessoa) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.getId());
}

}
