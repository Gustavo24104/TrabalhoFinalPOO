import java.util.Calendar;

public class Gerente extends Funcionario {
    private static double comissao;
    private Calendar dataDeIngressoComoGerente; //desde quando ele come gerentes
    private Agencia gerenciada;
    private boolean temCurso;

    static double getComissao() {
        return 1; //
    }

    static void setComissao() {

    }

}
