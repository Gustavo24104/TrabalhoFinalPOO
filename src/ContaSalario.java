import java.util.Calendar;

public class ContaSalario extends Conta {
    private double limSaque, limTransferencia;

    public ContaSalario(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                        Calendar ultimaMovimentacao, boolean estaAtiva, Cliente donoDaConta, Agencia ag,
                        double limSaque, double limTransferencia) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta, ag);
        this.limSaque = limSaque;
        this.limTransferencia = limTransferencia;
    }

    public void Saque(double valor) throws ValorInvalidoException {
        if(valor > limSaque) {
            throw new ValorInvalidoException("Valor excede o limite de saque desta conta!");
        }
        if(valor < 10) {
            throw new ValorInvalidoException("Valor é menor que o valor minimo!");
        }
        if(valor > getSaldoAtual()){
            throw new ValorInvalidoException("Valor maior que saldo!");
        }
        setSaldoAtual(getSaldoAtual() - valor);
    }
}
