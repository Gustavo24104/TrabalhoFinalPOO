import java.util.Calendar;

public class Funcionario extends Pessoa {
    String sexo, carteiraTrabalho, RG, cargo;
    double salario;
    Calendar anoDeIngresso;

    public Funcionario(double salarioBase, String RG, String cargo, String carteiraTrabalho,
                       String sexo, Calendar anoDeIngresso) {
        this.RG = RG;
        this.cargo = cargo;
        this.carteiraTrabalho = carteiraTrabalho;
        this.sexo = sexo;
        this.anoDeIngresso = anoDeIngresso;
        CalcularSalario(salarioBase);
    }



    //4,734e+11 <- 15 anos em milisegundos
    protected void CalcularSalario(double sal) {
        if(Calendar.getInstance().getTimeInMillis() - anoDeIngresso.getTimeInMillis() >= 4.734e+11) {
            //TODO: Testar!
            //gerente a mais de 15 anos
            salario = sal + (1.10*sal);
            return;
        }
        salario = sal;
    }


}
