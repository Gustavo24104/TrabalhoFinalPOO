import java.util.Calendar;

public class Gerente extends Funcionario {
    private static double comissao;
    private Calendar dataDeIngressoComoGerente;
    private Agencia gerenciada;
    private boolean temCurso;

    public Gerente(double salarioBase, String RG, String cargo, String carteiraTrabalho,String sexo,
                   Calendar anoDeIngresso, Calendar dataDeIngressoComoGerente, Agencia gerenciada, boolean temCurso,
                   String name, String CPF, String scolarship, String civil, Calendar data, Endereco address) {
        super(salarioBase, RG, cargo, carteiraTrabalho, sexo, anoDeIngresso, name, CPF, scolarship, civil, data, address);
        this.dataDeIngressoComoGerente = dataDeIngressoComoGerente;
        this.gerenciada = gerenciada;
        this.temCurso = temCurso;
    }

    protected void CalcularSalario(double sal) {
        CalcularSalario(sal);
        setSalario(getSalario() + comissao);
    }




    //Getters and Setters
    public static double getComissao() {
        return comissao;
    }

    public static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }

}
