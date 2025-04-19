import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sistemaevento.util.ConexaoBD;

public class conexao {
    public static void main(String[] args) {
        try {
            String sql = "SELECT * FROM participantes";
            ConexaoBD sqlConn = new ConexaoBD();
            Connection conn = sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("nome"));
            }
            rs.close();
            stm.close();
            sqlConn.close(conn);
        } catch (SQLException e) {
            System.err.println("Erro ao executar SELECT: " + e.getMessage());
        }
    }
}
