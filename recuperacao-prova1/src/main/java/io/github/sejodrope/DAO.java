package io.github.sejodrope;

import java.sql.Connection;

public class DAO {
    protected Connection conn(){
        return FabricaDeConexoes.obterInstancia().conn();
    }

}
