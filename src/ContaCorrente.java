import java.util.Calendar;

public class ContaCorrente extends Conta{
    private double limChequeEspecial, valorTaxaAdm;

    public ContaCorrente(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                         Calendar ultimaMovimentacao, boolean estaAtiva, Cliente donoDaConta1, Agencia ag,
                         double limChequeEspecial, double valorTaxaAdm) {
        super(nroConta, senha, saldoAtual, dataAbertura, ultimaMovimentacao, estaAtiva, donoDaConta1, ag);
        this.limChequeEspecial = Math.abs(limChequeEspecial);
        this.valorTaxaAdm = valorTaxaAdm;
    }

    public void Saque(double valor) {
        if(valor < 10) {
            throw new ValorInvalidoException("Valor é menor que o valor minimo");
        }

        if(getSaldoAtual() - valor < -limChequeEspecial) {
            throw new ValorInvalidoException("Valor excede o limite do cheque especial!");
        }
        setSaldoAtual(getSaldoAtual() - valor - valorTaxaAdm);
    }

    public void Deposito(double valor) {
        if(valor <= 0) {
            throw new ValorInvalidoException("Depósito não pode ser menor que 0!");
        }
        setSaldoAtual(getSaldoAtual() + valor - valorTaxaAdm);
    }

    public void MostraInfos() {
        super.MostraInfos();
        System.out.println("Tipo de conta: Corrente");
        System.out.println("Limite cheque especial: " + limChequeEspecial);
        System.out.println("Taxa administrativa: " + valorTaxaAdm);
    }

    //Getters and Setters
    public double getLimChequeEspecial() {return limChequeEspecial;}
    public void setLimChequeEspecial(double limChequeEspecial) {this.limChequeEspecial = limChequeEspecial;
    }
    public double getValorTaxaAdm() {return valorTaxaAdm;}
    public void setValorTaxaAdm(double valorTaxaAdm) {this.valorTaxaAdm = valorTaxaAdm;}

    public String getTipoConta() {
        return "Conta Corrente";
    }

}

