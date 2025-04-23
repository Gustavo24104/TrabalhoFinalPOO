import java.util.Calendar;

public class ContaCorrente extends Conta{
    private double limChequeEspecial, valorTaxaAdm;

    public ContaCorrente(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                         Calendar ultimaMovimentacao, boolean estaAtiva, Cliente[] donoDaConta, Agencia ag,
                         double limChequeEspecial, double valorTaxaAdm) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta, ag);
        this.limChequeEspecial = limChequeEspecial;
        this.valorTaxaAdm = valorTaxaAdm;
    }

    public void Saque(String senha, double valor) {

    }
}
