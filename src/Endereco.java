import java.io.Serializable;

public class Endereco implements Serializable {
    private String cidade, estado, bairro, cep;

    public Endereco(String cidade, String estado, String bairro, String cep){
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.cep = cep;
    }

    public String toString() {
        return cep + ", " + cidade + ", " + estado + ", " + bairro;
    }
}
