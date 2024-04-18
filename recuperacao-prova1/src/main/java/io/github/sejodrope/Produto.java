package io.github.sejodrope;

public class Produto {
    //colunas identificador, nome, valor,
    //estoque e data de lan¸camento
    public Integer id;
    public String nome;
    public Double valor;
    public Integer estoque;
    public String dataLancamento;
    public Integer status;
    public Integer tipo;

    public Produto(String nome, Integer estoque, Double valor, String dataLancamento, Integer id, Integer status, Integer tipo){
        this.nome = nome;
        this.estoque = estoque;
        this.valor = valor;
        this.dataLancamento = dataLancamento;
        this.id = id;
        this.status = status;
        this.tipo = tipo;
    }

}