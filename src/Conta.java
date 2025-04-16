import java.util.Calendar;

public abstract class Conta { //Essa eh a outra classse requisitada como abstrata pela questao 5
    protected String nroConta, senha;
    protected double saldoAtual;
    protected Calendar dataAbertura, ultimaMovimentacao;
    protected boolean estaAtiva;
    protected Cliente[] donoDaConta;
    protected Agencia ag;

    public String getSenha() {
        return senha;
    }

}
