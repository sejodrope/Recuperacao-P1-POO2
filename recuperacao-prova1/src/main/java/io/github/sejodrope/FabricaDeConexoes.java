package io.github.sejodrope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexoes {
    private static FabricaDeConexoes instancia;
    private FabricaDeConexoes(){}

    public static FabricaDeConexoes obterInstancia(){
        if(instancia == null){
            instancia = new FabricaDeConexoes();
        }
        return instancia;
    }

    public Connection conn(){
        try{
            return DriverManager.getConnection("jdbc:sqlite:tabelaProdutos.sql");
        } catch(SQLException e){
            throw new RuntimeException();
        }
    }
}

// var conn = FabricaDeConexao . getInstance () . conn () ;
// var ps = conn . preparedStatement ( sql ) ;