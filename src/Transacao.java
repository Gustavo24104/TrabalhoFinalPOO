import java.util.Calendar;

public class Transacao {
    protected Calendar data;
    protected double valor;
    protected String canal, tipo;
    Conta conta;

    public Transacao(Conta c, Calendar d) {
        conta = c;
        data = d;
    }


    public Transacao(Calendar data, Conta conta, String tipo, String canal, double valor) {
        this.data = data;
        this.conta = conta;
        this.tipo = tipo;
        this.canal = canal;
        this.valor = valor;
    }

    public void RealizaMovimentacao(String senha, Conta recebedor) {

        if(tipo.equals("saque")) {
            conta.Saque(senha, valor);
        }

        if(tipo.equals("deposito")) {
            if(valor < 0) {
                return;
                //ValorInvalidoException
            }
            // conta.Deposito(senha, valor);
        }

        if(tipo.equals("consulta")){
            System.out.println(conta.getSaldoAtual(senha));
        }

        if(tipo.equals("pagamento")) {
            if(valor < 0) {
                return;
                //Valor Invalido
            }
            conta.Saque(senha, valor);
            // conta.Deposito(senha, valor);
        }
        conta.setUltimaMovimentacao(Calendar.getInstance());
    }
}
