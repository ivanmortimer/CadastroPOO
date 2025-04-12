/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.io.Serial;
import java.util.InputMismatchException;

/**
 *
 * @author Ivan
 */
public class PessoaJuridica extends Pessoa implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    // Atributo
    private String cnpj;
    
    // Métodos construtores
    public PessoaJuridica() {
        super();
        this.cnpj = "";
    }
    public PessoaJuridica(String nome) throws ExcecaoValidacaoCNPJ {
        super(nome);
        this.cnpj = "";
    }
    public PessoaJuridica(String nome, int id) throws ExcecaoValidacaoCNPJ {
        super(nome, id);
        this.cnpj = "";
    }
    public PessoaJuridica(String nome, int id, String cnpj) throws ExcecaoValidacaoCNPJ {
        super(nome, id);
        setCNPJ(cnpj);
    }
    
    // Método 'getter'
    public final String getCNPJ() {
        return this.cnpj;
    }
    
    // Método 'setter'
    public final void setCNPJ ( String CNPJ ) throws ExcecaoValidacaoCNPJ {
        if ( validarCNPJ ( CNPJ ) ) {
            this.cnpj = CNPJ;
        }
        else {
            System.out.println ( "ERRO: CNPJ invalido!" );
        }
    }
    
    // Método 'exibir' para impressão dos dados de PessoaFisica
    @Override
    public void exibir() {
        System.out.println("Id: " + this.getId());
        System.out.println("Nome: " + this.getNome());
        System.out.println("CNPJ: " + this.getCNPJ());
    }
    
    // Método 'validarCNPJ': recebe um argumento do tipo String e retorna
    // 'true' se a string se conforma com o formato padrão de um CNPJ.
    // Caso contrário, retorna 'false'.
    public boolean validarCNPJ ( String CNPJ ) throws ExcecaoValidacaoCNPJ {
        char DV13, DV14;
        int soma, num, peso, i, resto;
        
        //Verifica sequência de dígitos iguais e tamanho (14 dígitos)
        if ( CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222") ||
             CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
             CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888") ||
             CNPJ.equals("99999999999999") || (CNPJ.length() != 14) ) {
            throw new ExcecaoValidacaoCNPJ ( "Entrada inválida: o CNPJ deve ter exatamente 14 dígitos (em fortato String) e seus dígitos não podem ser todos iguais." );
            //   return(false);
        }
        
        try {
            //1º Dígito Verificador
            soma = 0;
            peso = 2;
            for ( i = 11 ; i >= 0 ; i-- ) {
                num = (int)( CNPJ.charAt ( i ) - 48 );
                soma = soma + ( num * peso );
                peso++;
                if ( peso == 10 )
                peso = 2;
            }
            resto = soma % 11;
            if ( ( resto  == 0 ) || ( resto == 1 ) )
                DV13 = '0';
            else
                DV13 = (char)( ( 11 - resto ) + 48 );
            
            //2º Dígito Verificador
            soma = 0;
            peso = 2;
            for ( i = 12 ; i >= 0 ; i-- ) {
                num = (int) ( CNPJ.charAt ( i ) - 48 );
                soma = soma + ( num * peso );
                peso++;
                if ( peso == 10 )
                peso = 2;
            }
            resto = soma % 11;
            if ( ( resto == 0 ) || ( resto == 1 ) )
                DV14 = '0';
            else
                DV14 = (char) ( ( 11 - resto ) + 48 );
            
            //Verifica se os Dígitos Verificadores informados coincidem com os calculados
            if ( ( DV13 == CNPJ.charAt ( 12 ) ) && ( DV14 == CNPJ.charAt ( 13 ) ) )
                return true;
            else
            {
                throw new ExcecaoValidacaoCNPJ ( "Dígito Verificador (décimo-terceiro e/ou décimo-quarto) inválido." );
            }
        } catch (InputMismatchException erro) {
            return false;
        }
    }
}
