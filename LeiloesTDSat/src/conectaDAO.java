
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    private Connection conexao;
    private Statement st;

    Connection conn = null;

    public void conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/uconze", "root", "Pandorasbox2015");

            System.out.println("SUCESSO DE CONEXÃO!");
        } catch (ClassNotFoundException e) {
            System.out.println("Falha ao carregar a classe de conexão!"); //Erro de java
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco. Erro de SQL!"); //Erro de SQL
        }
    }

    public void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("DESCONECTADO COM SUCESSO");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desconectar");
        }
    }

}


