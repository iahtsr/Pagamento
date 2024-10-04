package trab.pagamento.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Define a entidade "Pagamento" que será mapeada para a tabela "pagamento" no banco de dados
@Entity
@Table(name="pagamento")
public class Pagamento {

    // Define a chave primária "cod_pagamento" com geração automática de valor
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cod_pagamento;

    // Colunas da tabela para armazenar o ano, mês e valor do pagamento
    @Column
    private short ano;

    @Column
    private short mes;

    @Column 
    private double valor;

    // Define a relação de muitos para um com a entidade Jogador, vinculada pelo campo "cod_jogador"
    @ManyToOne
    @JoinColumn(name = "cod_jogador", nullable = false)
    @JsonIgnore // Ignora esse campo na serialização JSON para evitar loops recursivos
    private Jogador cod_jogador;

    // Construtor padrão (necessário para JPA)
    public Pagamento() { }

    // Construtor com parâmetros para inicializar os campos
    public Pagamento(short ano, short mes, double valor, Jogador cod_jogador){
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.cod_jogador = cod_jogador;
    }

    // Métodos getters e setters para acessar e modificar os campos

    public int getCod_pagamento(){
        return cod_pagamento;
    }

    public short getAno(){
        return ano;
    }

    public void setAno(short ano){
        this.ano = ano;
    }

    public short getMes(){
        return mes;
    }

    public void setMes(short mes){
        this.mes = mes;
    }

    public double getValor(){
        return valor;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    public Jogador getCod_jogador(){
        return cod_jogador;
    }

    public void setCod_jogador(Jogador cod_jogador){
        this.cod_jogador = cod_jogador;
    }

    // Método toString para exibir as informações do pagamento de forma legível
    @Override
    public String toString(){
        return "Pagamento [codigo = " + cod_pagamento + ", ano = " + ano + ", mes = " + mes + ", valor = " + valor + ", codigo do jogador = " + cod_jogador + "]";
    }
}