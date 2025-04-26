import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Cliente extends Pessoa implements Logavel {
    private ArrayList<Conta> contas;
    private String senha;

    public Cliente(String nome, String cpf) throws CPFInvalidoException {
        this.setNome(nome);
        this.setCpf(cpf);
        if(!ValidaCPF()) throw new CPFInvalidoException("CPF invalido!"); //tratar
    }

    public Cliente(String nome, String cpf, String escolaridade,
                   String estadoCivil, Calendar dataNascimento, Endereco end) {
        super(nome, cpf, escolaridade, estadoCivil, dataNascimento, end);
    }




    //métodos


    //Getters and Setters

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public void NovaConta(Conta c) {
        contas.add(c);
    }

    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if(!this.senha.equals(senha)) {
            throw new SenhaInvalidaException("Senha invalida!\n");
        }
    }

    public boolean Login(String usuario, String senha) {
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.getNome().toLowerCase())) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
