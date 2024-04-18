package io.github.sejodrope;

import java.sql.SQLException;

public class TipoProdutoDAO extends DAO {
    // inserir produto
    // consultar produto pelo ID
    // consultar produto pelo Nome
    // atualizar quantidade produto
    private final static String CRIAR_TABELA = """
            create table  IF NOT EXISTS tipo_produto(
                                 id INTEGER PRIMARY KEY,
                                 nome text);
            """;


    public void criarTabela() {
        try (var conexao = conn();
             var stat = conexao.createStatement()) {
            stat.execute(CRIAR_TABELA);
            System.out.println("Sucesso ao criar tabela.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela.");
            e.printStackTrace();
        }
    }


    public void inserirTipoProduto(TipoProduto tipoProduto) {
        String sql = "insert into tipo_produto(nome) values (?)";

        try (var conexao = conn(); var prepStat = conexao.prepareStatement(sql)) {

            prepStat.setString(1, tipoProduto.nome);
            prepStat.execute();

            System.out.println("Tipo de Produto adicionado na tabela!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}