import java.util.Calendar;

public class ContaSalario extends Conta {
    private double limSaque, limTransferencia;

    public ContaSalario(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                        Calendar ultimaMovimentacao, boolean estaAtiva, Cliente[] donoDaConta, Agencia ag,
                        double limSque, double limTransferencia) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta, ag);
        this.limSaque = limSque;
        this.limTransferencia = limTransferencia;
    }

    public void Saque(String senha, double valor) {

    }
}
