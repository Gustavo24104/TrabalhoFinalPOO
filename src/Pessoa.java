import java.io.Serializable;
import java.util.Calendar;

public abstract class Pessoa implements Serializable {
    private String nome, cpf, escolaridade, estadoCivil;
    Calendar dataNascimento;
    private Endereco end;

    public boolean ValidaCPF () {
        int multiplicador = 10;
        int acc1 = 0, acc2 = 0;
        int digito1, digito2;
        for(int i = 0; i < cpf.length() - 3; ++i) {
            if(cpf.charAt(i) == '.') continue;
            acc1 += multiplicador * Integer.parseInt(String.valueOf(cpf.charAt(i)));
            acc2 += (multiplicador+1) * Integer.parseInt(String.valueOf(cpf.charAt(i)));
            multiplicador--;
        }
        if(acc1 % 11 < 2) digito1 = 0;
        else digito1 = 11 - (acc1 % 11);
        acc2 += (multiplicador+1) * digito1;

        if(acc2 % 11 < 2) digito2 = 0;
        else digito2 = 11 - (acc2 % 11);

        if(digito1 != Integer.parseInt(String.valueOf(cpf.charAt(12)))) return false;
        if(digito2 != Integer.parseInt(String.valueOf(cpf.charAt(13)))) return false;
        return true;
    }

    
    public Pessoa(String nome, String cpf, String escolaridade,
                  String estadoCivil, Calendar dataNascimento, Endereco end) throws CPFInvalidoException{
        this.nome = nome;
        this.cpf = cpf;
        if (!ValidaCPF()) {
            throw new CPFInvalidoException("CPF Invalido informado!\n");
        }
        this.escolaridade = escolaridade;
        this.estadoCivil = estadoCivil;
        this.dataNascimento = dataNascimento;
        this.end = end;
    }

    public Pessoa() {

    }

//Getters and Setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }
}
