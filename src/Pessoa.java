import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

public abstract class Pessoa implements Serializable,  PodeSerLidoDoTeclado {
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

    //Função para verificar se o cpf esta no formato correto
    private boolean ParseCPF() {
        //Primeiro verifica se ta no formato correto
        if (cpf.length() < 14) return false;

        for(int i = 0; i < 14; ++i) {
            if(i == 3 || i == 7) { //posições com ponto
                if(cpf.charAt(i) != '.') return false;
                continue;
            }
            if(i == 11) {
                if (cpf.charAt(i) != '-') return false;
                continue;
            }
            if(!Character.isDigit(cpf.charAt(i))) return false;
        }
        return ValidaCPF(); //depois valida o cpf
    }

    public void LerDoTeclado() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite nome: ");
        nome = sc.nextLine();

        System.out.println("Digite escolaridade: ");
        escolaridade = sc.nextLine();

        System.out.println("Digite estado civil: ");
        estadoCivil = sc.nextLine();

        boolean validar = false;
        while(!validar) {
            System.out.println("Digite cpf (xxx.yyy.zzz-ab): ");
            cpf = sc.nextLine();
            validar = ParseCPF();
            if(!validar) System.out.println("CPF inválido! Por favor tente novamente!");
        }

        System.out.println("Digite data de nascimento: ");
        dataNascimento = Banco.CriarData();

        System.out.println("Digite endereço: ");
        end = Banco.CriarEndereco();
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

    //Construtor vazio
    public Pessoa() {}

    public Pessoa(Pessoa p) {
        this.nome = p.nome;
        this.dataNascimento = p.dataNascimento;
        this.estadoCivil = p.estadoCivil;
        this.escolaridade = p.escolaridade;
        this.cpf = p.cpf;
        this.end = p.end;
    }

    public String toString() {
        return "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nEscolaridade: " + escolaridade +
                "\nEstado Civil: " + estadoCivil +
                "\nData de Nascimento: " + dataNascimento.get(Calendar.DAY_OF_MONTH) + "/" +
                dataNascimento.get(Calendar.MONTH) + "/" + dataNascimento.get(Calendar.YEAR) +
                "\nEndereço: " + end;
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
