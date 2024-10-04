package trab.pagamento.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

// Define a entidade "Jogador" que será mapeada para a tabela "jogador" no banco de dados
@Entity
@Table(name="jogador")
public class Jogador {

    // Define a chave primária "cod_jogador" com geração automática de valor
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cod_jogador;

    // Colunas da tabela com tamanho máximo de 60 caracteres
    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String email;

    // Coluna para data de nascimento, com formatação específica para o JSON
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datanasc;

    // Define a relação de um para muitos com a entidade Pagamento, vinculada pelo campo "cod_jogador"
    @OneToMany(mappedBy = "cod_jogador")
    private List<Pagamento> pagamentos;

    // Construtor padrão (necessário para JPA)
    public Jogador() { }

    // Construtor com parâmetros para inicializar os campos
    public Jogador(String nome, String email, Date datanasc){
        this.nome = nome;
        this.email = email;
        this.datanasc = datanasc;
    }

    // Métodos getters e setters para acessar e modificar os campos

    public int getCod_jogador(){
        return cod_jogador;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Date getDatanasc(){
        return datanasc;
    }

    public void setDatanasc(Date datanasc){
        this.datanasc = datanasc;
    }

    public List<Pagamento> getPagamentos(){
        return pagamentos;
    }

    // Método toString para exibir as informações do jogador de forma legível
    @Override
    public String toString(){
        return "Jogador [codigo = " + cod_jogador + ", nome = " + nome + ", email = " + email + ", data de nasc. = " + datanasc + "]";
    }
}