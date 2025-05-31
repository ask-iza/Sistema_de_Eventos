package com.sistemaevento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sistemaevento.tabelas.Palestrante;
import com.sistemaevento.util.ConexaoBD;

public class PalestranteDao {

    public int cadastrarPalestrante(Palestrante p) {
        String sql = "INSERT INTO palestrante (nome, curriculo, area_atuacao, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = new ConexaoBD().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCurriculo());
            stmt.setString(3, p.getArea_atuacao());
            stmt.setString(4, p.getEmail());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                System.out.println("Palestrante salvo com ID: " + idGerado);
                return idGerado;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar evento: " + e.getMessage());
        }

        return -1;
    }

    public void salvar(Palestrante p) {
        String sql = "INSERT INTO palestrante (nome, curriculo, area_atuacao, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = new ConexaoBD().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCurriculo());
            stmt.setString(3, p.getArea_atuacao());
            stmt.setString(4, p.getEmail());
            stmt.executeUpdate();

            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vincularPalestranteAoEvento(int eventoId, int palestranteId) {
        String sql = "INSERT INTO evento_palestrante (evento_id, palestrante_id) VALUES (?, ?)";
    
        try (Connection conn = new ConexaoBD().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, eventoId);
            stmt.setInt(2, palestranteId);
            stmt.executeUpdate();
    
        } catch (SQLException e) {
            System.err.println("Erro ao vincular palestrante ao evento: " + e.getMessage());
        }
    }

    public boolean verificarPalestrante(int id, String email) {
        String sql = "SELECT COUNT(*) FROM palestrante WHERE id = ? AND email = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Palestrante buscarPalestrantePorId(int id) {
        System.out.println("Buscando palestrante com ID: " + id);  // Depuração
        String sql = "SELECT * FROM palestrante WHERE id = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Palestrante palestrante = new Palestrante();
                palestrante.setId(rs.getInt("id"));
                palestrante.setNome(rs.getString("nome"));
                palestrante.setCurriculo(rs.getString("curriculo"));
                palestrante.setArea_atuacao(rs.getString("area_atuacao"));
                palestrante.setEmail(rs.getString("email"));
                return palestrante;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Caso não encontre o palestrante
    }


        public Palestrante buscarPalestrantePorNome(String nome) {
        String sql = "SELECT * FROM palestrante WHERE nome = ?";
        try (Connection conn = new ConexaoBD().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Palestrante palestrante = new Palestrante();
                palestrante.setId(rs.getInt("id"));
                palestrante.setNome(rs.getString("nome"));
                palestrante.setCurriculo(rs.getString("curriculo"));
                palestrante.setArea_atuacao(rs.getString("area_atuacao"));
                palestrante.setEmail(rs.getString("email"));
                return palestrante;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}