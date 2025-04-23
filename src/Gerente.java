import java.util.Calendar;

public class Gerente extends Funcionario {
    private static double comissao;
    private Calendar dataDeIngressoComoGerente; //desde quando ele come gerentes
    private Agencia gerenciada;
    private boolean temCurso;

    public Gerente(double salarioBase, String RG, String cargo, String carteiraTrabalho,String sexo,
                   Calendar anoDeIngresso, Calendar dataDeIngressoComoGerente, Agencia gerenciada, boolean temCurso) {
        super(salarioBase, RG, cargo, carteiraTrabalho, sexo, anoDeIngresso);
        this.dataDeIngressoComoGerente = dataDeIngressoComoGerente;
        this.gerenciada = gerenciada;
        this.temCurso = temCurso;
    }


    static double getComissao() {
        return comissao; //
    }

    static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }

    protected void CalcularSalario(double sal) {
        super.CalcularSalario(sal);
        salario += salario + comissao;
    }

}
