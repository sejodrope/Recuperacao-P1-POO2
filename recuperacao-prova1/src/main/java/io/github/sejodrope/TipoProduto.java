package io.github.sejodrope;

public class TipoProduto {
    //colunas identificador, nome, valor,
    //estoque e data de lan¸camento
    public Integer id;
    public String nome;

    public TipoProduto(String nome, Integer id){
        this.id = id;
        this.nome = nome;
    }

}