import java.util.Calendar;

public class Cliente extends Pessoa {
    private Conta[] contas;

    public Cliente(String nome, String cpf) throws CPFInvalidoException {
        this.nome = nome;
        this.cpf = cpf;
        if(!ValidaCPF()) throw new CPFInvalidoException("CPF invalido!"); //tratar
    }

    public Cliente(String nome, String cpf, String escolaridade,
                   String estadoCivil, Calendar dataNascimento, Endereco end) throws CPFInvalidoException {
        this.nome = nome;
        this.cpf = cpf;
        if(!ValidaCPF()) throw new CPFInvalidoException("CPF invalido!");
        this.escolaridade = escolaridade;
        this.estadoCivil = estadoCivil;
        this.dataNascimento = dataNascimento;
        this.end = end;
    }

    public Cliente() {
    }

    //métodos
}
