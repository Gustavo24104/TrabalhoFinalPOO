import java.util.Calendar;

public class ContaPoupanca extends Conta {
    private double rendimentoMes;

    public ContaPoupanca(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                         Calendar ultimaMovimentacao, boolean estaAtiva, Cliente donoDaConta, Agencia ag,
                         double rendimentoMes) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta, ag);
        this.rendimentoMes = rendimentoMes;
    }

    public void Saque(double valor) throws ValorInvalidoException{
        if(valor < 10) {
            throw new ValorInvalidoException("Valor é menor que o valor mínimo!");
        }
        if(valor > getSaldoAtual()){
            throw new ValorInvalidoException("Valor maior que saldo!");
        }
        setSaldoAtual(getSaldoAtual() - valor);
    }

    public void MostraInfos() {
        super.MostraInfos();
        System.out.println("Tipo de conta: Poupança");
        System.out.println("Rendimento mensal: " + rendimentoMes);
    }

}
