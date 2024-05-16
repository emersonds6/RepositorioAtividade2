
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    /**
     *
     * @param id
     * @return
     */
    public List<ProdutosDTO> consultar() {
        List<ProdutosDTO> produtos = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            if (conexao == null || conexao.isClosed()){
            conexao = getConexao();
        }
            st = conexao.prepareStatement("SELECT * FROM produtos");
            rs = st.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produtos: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar ResultSet: " + ex.getMessage());
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar PreparedStatement: " + ex.getMessage());
                }
            }
        }
        return produtos;
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
