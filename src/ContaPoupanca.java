import java.util.Calendar;

public class ContaPoupanca extends Conta {
    private double rendimentoMes;

    public ContaPoupanca(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                         Calendar ultimaMovimentacao, boolean estaAtiva, Cliente[] donoDaConta, Agencia ag,
                         double rendimentoMes) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta, ag);
        this.rendimentoMes = rendimentoMes;
    }

    public void Saque(String senha, double valor) {

    }
}
