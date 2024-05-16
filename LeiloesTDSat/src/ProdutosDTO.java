
public class ProdutosDTO {
    private Integer id;
    private String nome;
    private Integer valor;
    private String status;

    public ProdutosDTO() {
        
    }

    public ProdutosDTO(String nome, Integer valor, String status) {
        this.nome = nome;
        this.valor = valor;
        this.status = status;
    }
    

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
