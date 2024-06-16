import java.io.Serializable;

public class Book implements Serializable {
    private String escritor;
    private String nome;
    private String categoria;
    private int quantidade;

    public Book() {}

    public Book(String escritor, String nome, String categoria, int quantidade) {
        this.escritor = escritor;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "escritor='" + escritor + '\'' +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
