package com.sistemaevento.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String HOST = "localhost";
    private static final String PORT = "5439";
    private static final String DATABASE = "postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123";

    private static final String URL = "jdbc:postgresql://localhost:5439/postgres"; //"jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;

    private Connection conn = null;

   public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com PostgreSQL estabelecida.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do PostgreSQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return this.conn;
    }

    public void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connect();
    }
}