public class Endereco {
    private String cidade, estado, bairro;
    private int cep;

    public Endereco(String cidade, String estado, String bairro, int cep){
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.cep = cep;
    }


    public String toString() {
        return cidade + estado + bairro;
    }
}
