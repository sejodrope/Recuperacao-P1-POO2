package io.github.sejodrope;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ProdutoDAO extends DAO {
    // inserir produto
    // consultar produto pelo ID
    // consultar produto pelo Nome
    // atualizar quantidade produto
    private final static String CRIAR_TABELA = """
                create table  IF NOT EXISTS produto(
                                     id INTEGER PRIMARY KEY,
                                     nome text,
                                     estoque integer,
                                     valor real,
                                     dataLancamento text,
                                     status integer,
                                     tipo integer);
                """;


    public void criarTabela(){
        try(var conexao = conn();
            var stat = conexao.createStatement()){
            stat.execute(CRIAR_TABELA);
            System.out.println("Sucesso ao criar tabela.");
        }catch (SQLException e){
            System.out.println("Erro ao criar tabela.");
            e.printStackTrace();
        }
    }


    public void inserirProduto(Produto produto) {
        String sql = "insert into produto(nome, estoque, valor, dataLancamento, status, tipo) values (?,?,?,?,?,?)";

        try (var conexao = conn(); var prepStat = conexao.prepareStatement(sql)) {

            prepStat.setString(1, produto.nome);
            prepStat.setInt(2, produto.estoque);
            prepStat.setDouble(3, produto.valor);
            prepStat.setString(4, produto.dataLancamento);
            prepStat.setInt(5,produto.status);
            prepStat.setInt(6,produto.tipo);
            prepStat.execute();

            System.out.println("Produto adicionado no Estoque!");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto no Estoque!");
            e.printStackTrace();
        }
    }
    public void atualizarEstoqueProduto(Produto produto){
        String sql = """
                        update produto set estoque = ? where id = ?;
                     """;

        try(var conexao = conn(); var prepStat = conexao.prepareStatement(sql)){

            prepStat.setInt(1,produto.estoque);
            prepStat.setInt(2,produto.id);
            prepStat.execute();

            System.out.println("Produto atualizado no Estoque!");

        }catch(SQLException e){
            System.out.println("Erro ao atualizar produto.");
            e.printStackTrace();
        }


    }

    public void deletarProduto(Produto produto){
        String sql = """
                    delete produto where id = ?;
                    """;
        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)){
            prepStat.setLong(1,produto.id);
            prepStat.execute();
            System.out.println("Produto deletado no Estoque!");

        }catch (SQLException e){
            System.out.println("Erro ao deletar produto.");
            e.printStackTrace();
        }
    }

    public List<Produto> relatorioProdutosPorAno() {
        List<Produto> lista = new ArrayList<>();
        String sql = """
                   select id, nome, valor, estoque, dataLancamento, status, tipo from produto;
                """;

        Scanner leitor = new Scanner(System.in);
        System.out.println("Qual ano você gostaria de utilizar como filtro?");
        String ano = leitor.next();

        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {

            ResultSet resultado = prepStat.executeQuery();

            while (resultado.next()) {
                String data = resultado.getString("dataLancamento");

                String soAno = data.substring(6);

                if(Objects.equals(ano, soAno)){
                    Produto produto = new Produto(
                            resultado.getString("nome"),
                            resultado.getInt("valor"),
                            resultado.getDouble("estoque"),
                            resultado.getString("dataLancamento"),
                            resultado.getInt("id"),
                            resultado.getInt("status"),
                            resultado.getInt("tipo")
                    );
                    lista.add(produto);
                }

            }

        }catch(SQLException e){
            System.out.println("Deu erro ao filtrar ano.");
            e.printStackTrace();
        }

        return lista;
    }

    public List<Produto> relatorioProdutosSemEstoque() {
        List<Produto> lista = new ArrayList<>();
        String sql = """
                   select id, nome, valor, estoque, dataLancamento, status, tipo from produto;
                """;

        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {

            ResultSet resultado = prepStat.executeQuery();

            while (resultado.next()) {
                Integer quantidade = resultado.getInt("estoque");

                if(quantidade <= 0){
                    Produto produto = new Produto(
                            resultado.getString("nome"),
                            resultado.getInt("valor"),
                            resultado.getDouble("estoque"),
                            resultado.getString("dataLancamento"),
                            resultado.getInt("id"),
                            resultado.getInt("status"),
                            resultado.getInt("tipo")
                    );
                    lista.add(produto);
                }

            }

        }catch(SQLException e){
            System.out.println("Deu erro ao procurar estoque.");
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarColuna(){
        String sql = """
                   alter table produto add column status integer;
                """;

        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {
            prepStat.execute();
            System.out.println("Coluna adicionada à tabela");

        }catch(SQLException e){
            System.out.println("Deu erro ao adicionar coluna.");
            e.printStackTrace();
        }

    }
    public List<Produto> relatorioProdutosPorStatus() {
        List<Produto> lista = new ArrayList<>();
        String sql = """
                   select id, nome, valor, estoque, dataLancamento, status, tipo from produto;
                """;

        Scanner leitor = new Scanner(System.in);
        System.out.println("Qual Status você gostaria de utilizar como filtro?");
        System.out.println("1 - Ativo");
        System.out.println("0 - Inativo");

        Integer statusPesquisar = leitor.nextInt();

        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {
            ResultSet resultado = prepStat.executeQuery();

            while (resultado.next()) {
                Integer statusTabela = resultado.getInt("status");

                System.out.printf("Pesquisar" + statusPesquisar);
                System.out.printf("Comparado à" + statusTabela);

                if(statusTabela == statusPesquisar){
                    Produto produto = new Produto(
                            resultado.getString("nome"),
                            resultado.getInt("valor"),
                            resultado.getDouble("estoque"),
                            resultado.getString("dataLancamento"),
                            resultado.getInt("id"),
                            resultado.getInt("status"),
                            resultado.getInt("tipo")
                    );
                    lista.add(produto);
                }

            }
            System.out.println(lista);

        }catch(SQLException e){
            System.out.println("Deu erro ao procurar Status.");
            e.printStackTrace();
        }

        return lista;
    }
    public void atualizarStatusDoProduto(){
        int statusNovo;
        int idProduto;
        String sql = """
                        update produto set status = ? where id = ?;
                     """;
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o ID do produto que deseja alterar o status:");
        idProduto = leitor.nextInt();
        System.out.println("Status = "+ idProduto);


        System.out.println("Digite o status para qual deseja alterar:");
        System.out.println("1 - Ativo");
        System.out.println("0 - Inativo");
        statusNovo = leitor.nextInt();
        System.out.println("Id Alvo = "+statusNovo);

        try(var conexao = conn(); var prepStat = conexao.prepareStatement(sql)){

            prepStat.setInt(1,statusNovo);
            prepStat.setInt(2,idProduto);
            prepStat.execute();

            System.out.println("Status do produto atualizado no Estoque!");

        }catch(SQLException e){
            System.out.println("Erro ao atualizar status do produto.");
            e.printStackTrace();
        }


    }

    public void adicionarColunaEstrangeira() {
        String sql = """
                 ALTER TABLE produto ADD COLUMN tipo INTEGER;
                 ADD CONSTRAINT fk_tipo FOREIGN KEY (tipo) REFERENCES tipo_produto(id);
                """;


        try (var conexao = conn();
             var prepStat = conexao.prepareStatement(sql)) {
            prepStat.execute();
            System.out.println("Coluna adicionada à tabela");

        } catch (SQLException e) {
            System.out.println("Deu erro ao adicionar coluna.");
            e.printStackTrace();
        }

    }

    public void atualizarTipoDoProduto(){
        int tipoNovo;
        int idProduto;
        String sql = """
                        update produto set tipo = ? where id = ?;
                     """;
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o ID do produto que deseja alterar o status:");
        idProduto = leitor.nextInt();
        System.out.println("ID = "+ idProduto);


        System.out.println("Digite o tipo para qual deseja alterar:");
        tipoNovo = leitor.nextInt();
        System.out.println("Tipo = "+tipoNovo);

        try(var conexao = conn(); var prepStat = conexao.prepareStatement(sql)){

            prepStat.setInt(1,tipoNovo);
            prepStat.setInt(2, idProduto);
            prepStat.execute();

            System.out.println("Status do produto atualizado no Estoque!");

        }catch(SQLException e){
            System.out.println("Erro ao atualizar status do produto.");
            e.printStackTrace();
        }


    }
    public void consultarProdutos(){
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = """
                        select id, nome, valor, estoque, dataLancamento, status, tipo from produto;
                     """;

        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {

            ResultSet resultado = prepStat.executeQuery();
            while(resultado.next()){
                Produto produto = new Produto(
                        resultado.getString("nome"),
                        resultado.getInt("valor"),
                        resultado.getDouble("estoque"),
                        resultado.getString("dataLancamento"),
                        resultado.getInt("id"),
                        resultado.getInt("status"),
                        resultado.getInt("tipo")
                );
                listaProdutos.add(produto);

            }
            System.out.println("Sucesso ao consultar produtos!");
        }catch (SQLException e){
            System.out.println("Erro ao consultar produtos.");
            e.printStackTrace();
        }
        for(Produto produto : listaProdutos){
            String tipoProdutoValidado = "";
            System.out.println(
                    " | ID:  " + produto.id + " | " + produto.nome + " | " + produto.valor
                            + " | " + produto.estoque + " | " + produto.dataLancamento + " | "
                            + produto.status + " | " + tipoProdutoValidado + " | "
            );
        }
    }
    public void consultarProdutosDetalhado(){
        List<Produto> listaProdutos = new ArrayList<>();
        List<TipoProduto> listaTipos = new ArrayList<>();

        String sql = """
                        select id, nome, valor, estoque, dataLancamento, status, tipo from produto;
                     """;
        String sql2 = """
                        select id, nome from tipo_produto;
                """;


        try(var conexao = conn();
            var prepStat = conexao.prepareStatement(sql)) {

            ResultSet resultado = prepStat.executeQuery();
            while(resultado.next()){
                Produto produto = new Produto(
                        resultado.getString("nome"),
                        resultado.getInt("valor"),
                        resultado.getDouble("estoque"),
                        resultado.getString("dataLancamento"),
                        resultado.getInt("id"),
                        resultado.getInt("status"),
                        resultado.getInt("tipo")
                );
                listaProdutos.add(produto);

            }
            System.out.println("Sucesso ao consultar produtos!");
        }catch (SQLException e){
            System.out.println("Erro ao consultar produtos.");
            e.printStackTrace();
        }







        try(var conexao = conn();
            var prepStat2 = conexao.prepareStatement(sql2)) {

            ResultSet resultado = prepStat2.executeQuery();
            while(resultado.next()){
                TipoProduto tipoProd = new TipoProduto(
                        resultado.getString("nome"),
                        resultado.getInt("id")
                );
                listaTipos.add(tipoProd);

            }

        }catch (SQLException e){
            System.out.println("Erro ao consultar produtos.");
            e.printStackTrace();
        }
        for(Produto produto : listaProdutos){
            String tipoProdutoValidado = "";
            for(TipoProduto tipo : listaTipos){
                if(Objects.equals(tipo.id, produto.tipo)){
                    tipoProdutoValidado = tipo.nome;
                    System.out.println("Tipo: " + tipoProdutoValidado);
                    break;
                }
            }
            System.out.println(
                    " | ID:  " + produto.id + " | " + produto.nome + " | " + produto.valor
                            + " | " + produto.estoque + " | " + produto.dataLancamento + " | "
                            + produto.status + " | " + tipoProdutoValidado + " | "
            );
        }
    }
}