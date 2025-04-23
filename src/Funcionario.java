import java.io.Serializable;
import java.util.Calendar;

public class Funcionario extends Pessoa implements Logavel, Serializable {
    private String sexo, carteiraTrabalho, RG, cargo;
    private double salario;
    private Calendar anoDeIngresso;
    private String senha;

    public Funcionario(double salarioBase, String RG, String cargo, String carteiraTrabalho,
                       String sexo, Calendar anoDeIngresso, String name, String CPF,
                       String scolarship, String civil, Calendar data, Endereco address) {
        super(name, CPF, scolarship, civil, data, address);
        this.RG = RG;
        this.cargo = cargo;
        this.carteiraTrabalho = carteiraTrabalho;
        this.sexo = sexo;
        this.anoDeIngresso = anoDeIngresso;
        CalcularSalario(salarioBase);
    }

    public Funcionario(Funcionario f) {
        this.sexo = f.sexo;
        this.carteiraTrabalho = f.carteiraTrabalho;
        this.RG = f.RG;
        this.cargo = f.cargo;
        this.salario = f.salario;
        this.anoDeIngresso = f.anoDeIngresso;
        this.senha = f.senha;
    }



    public Funcionario(Calendar anoDeIngresso) {
        this.anoDeIngresso = anoDeIngresso;
        CalcularSalario(1);
    }

    //4,734e+11 <- 15 anos em milisegundos
    private void CalcularSalario(double sal) {
        Calendar agora = Calendar.getInstance();
        agora.add(Calendar.YEAR, -15);
        if(anoDeIngresso.before(agora)) {
            System.out.println("quinze anos!");
            //TODO: Testar!
            //funcionario a mais de 15 anos
            salario = sal + (1.10*sal);
            return;
        }
        System.out.println("funcionario novo");
        salario = sal;
    }

    //Getters and Setters
    public Calendar getAnoDeIngresso() {
        return anoDeIngresso;
    }
    public void setAnoDeIngresso(Calendar anoDeIngresso) {
        this.anoDeIngresso = anoDeIngresso;
    }

    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRG() {
        return RG;
    }
    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCarteiraTrabalho() {
        return carteiraTrabalho;
    }
    public void setCarteiraTrabalho(String carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
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
