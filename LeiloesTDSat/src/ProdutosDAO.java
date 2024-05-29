
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        //conn = new conectaDAO().connectDB();
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

    public void venderProduto(int produtoId) {
        Connection conexao = null;
    PreparedStatement stmt = null;

    try {
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?autoReconnect=true&useSSL=false", "root", "Pandorasbox2015");

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, produtoId);

        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }

    } catch (SQLException e) {
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
        }
    }
}
}
