
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class conectaDAO {

    private Connection conexao;
    private PreparedStatement st;
    private ResultSet rs;

    public Connection getConexao() {
        return conexao;
    }

    public boolean conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?autoReconnect=true&useSSL=false", "root", "Pandorasbox2015");
            System.out.println("Conectado!");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
        return false;
    }

    public void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Desconectado!");
            } catch (SQLException ex) {
                System.out.println("Erro ao desconectar: " + ex.getMessage());
            }
        }
    }

    public int salvar(ProdutosDTO pdto) {
        int status;
        PreparedStatement st = null;
        try {
            st = conexao.prepareStatement("INSERT INTO produtos (nome,valor) VALUES (?,?)");

            st.setString(1, pdto.getNome());
            st.setInt(2, pdto.getValor());
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
            return ex.getErrorCode();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar o PreparedStatement: " + ex.getMessage());
                }
            }
        }
    }


    public ProdutosDTO consultar(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    ProdutosDTO pdto = new ProdutosDTO();
                    pdto.setId(rs.getInt(id));
                    pdto.setNome(rs.getString("nome"));
                    pdto.setValor(rs.getInt("valor"));
                    
                    return pdto;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Eroo ao excluir: " + ex.getMessage());
            return false;
        }
    }

    public int atualizar(ProdutosDTO pdto) {
        String sql = "UPDATE produtos SET nome = ?, valor = ?, status = ? WHERE id = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, pdto.getId());
            st.setString(2, pdto.getNome());
            st.setInt(3, pdto.getValor());
            st.setString(4, pdto.getStatus());
            return st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

}
