package com.sistemaevento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.util.ConexaoBD;

public class EventoDao {

    public int salvar(Evento evento) {
            String sql = "INSERT INTO evento (nome, descricao, data, local, capacidade) VALUES (?, ?, ?, ?, ?)";
    
            try (Connection conn = new ConexaoBD().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
                stmt.setString(1, evento.getNome());
                stmt.setString(2, evento.getDescricao());
                stmt.setString(3, evento.getData());
                stmt.setString(4, evento.getLocal());
                stmt.setInt(5, evento.getCapacidade());
    
                stmt.executeUpdate();
    
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    System.out.println("Evento salvo com ID: " + idGerado);
                    return idGerado;
                }
    
            } catch (SQLException e) {
                System.err.println("Erro ao salvar evento: " + e.getMessage());
            }
    
            return -1;
        }      

        public List<Evento> listarTodos() {
            List<Evento> eventos = new ArrayList<>();
            String sql = "SELECT * FROM evento";
        
            try (Connection conn = new ConexaoBD().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
        
                while (rs.next()) {
                    Evento e = new Evento();
                    e.setId(rs.getInt("id"));
                    e.setNome(rs.getString("nome"));
                    e.setDescricao(rs.getString("descricao"));
                    e.setData(rs.getString("data"));
                    e.setLocal(rs.getString("local"));
                    e.setCapacidade(rs.getInt("capacidade"));
                    eventos.add(e);
                }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            return eventos;
        }


        public Evento buscarPorNome(String nome) {
            String sql = "SELECT * FROM evento WHERE nome = ? LIMIT 1";
        
            try (Connection conn = new ConexaoBD().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
        
                stmt.setString(1, nome);
                ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    Evento e = new Evento();
                    e.setId(rs.getInt("id"));
                    e.setNome(rs.getString("nome"));
                    e.setDescricao(rs.getString("descricao"));
                    e.setData(rs.getString("data"));
                    e.setLocal(rs.getString("local"));
                    e.setCapacidade(rs.getInt("capacidade"));
                    return e;
                }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            return null;
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

        public boolean atualizar(Evento evento) {
            String sql = "UPDATE evento SET nome = ?, descricao = ?, data = ?, local = ?, capacidade = ? WHERE id = ?";
            try (Connection conn = new ConexaoBD().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, evento.getNome());
                stmt.setString(2, evento.getDescricao());
                stmt.setString(3, evento.getData());
                stmt.setString(4, evento.getLocal());
                stmt.setInt(5, evento.getCapacidade());
                stmt.setInt(6, evento.getId());

                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas > 0;
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar evento: " + e.getMessage());
                return false;
            }
        }

    public boolean excluir(int id) {
        String sqlDeleteVinculos = "DELETE FROM evento_palestrante WHERE evento_id = ?";
        String sqlDeleteEvento = "DELETE FROM evento WHERE id = ?";
        try (Connection conn = new ConexaoBD().getConnection()) {
            // Exclui os vínculos primeiro
            try (PreparedStatement stmtVinculos = conn.prepareStatement(sqlDeleteVinculos)) {
                stmtVinculos.setInt(1, id);
                stmtVinculos.executeUpdate();
            }
            // Agora exclui o evento
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlDeleteEvento)) {
                stmtEvento.setInt(1, id);
                int linhasAfetadas = stmtEvento.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir evento: " + e.getMessage());
            return false;
        }
    }
        
}