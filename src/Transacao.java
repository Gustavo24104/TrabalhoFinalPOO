import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Transacao {
    private Calendar data;
    private double valor;
    private String canal, tipo;
    Conta conta;

    public Transacao(Conta conta, Calendar data) {
        this.conta = conta;
        this.data = data;
    }

    public Transacao(Calendar data, Conta conta, String tipo, String canal, double valor) {
        this.data = data;
        this.conta = conta;
        this.tipo = tipo;
        this.canal = canal;
        this.valor = valor;
    }

    public void RealizaMovimentacao(String senha, Conta recebedor) {

        if(!conta.isEstaAtiva()) {
            System.out.println("A conta esta inativa!\n");
        }

        if(conta.isEstaBloqueada()) {
            System.out.println("Essa conta foi bloqueada!!!!");
        }

        if(tipo.equals("saque")) {

            try {
                conta.ValidaSenha(senha);
                conta.Saque(valor);
            }
            catch (ValorInvalidoException e) {
                System.out.println(e);
            }

            catch (SenhaInvalidaException e) {
                System.out.println("Senha inválida!\n " +
                        "Você tem " + (3 - conta.getTentativasErradas()) + " tentativas restantes.");
            }

        }

        if(tipo.equals("deposito")) {
            try {
                conta.Deposito(valor);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if(tipo.equals("consulta")){
            try {
                conta.ValidaSenha(senha);
                System.out.println(conta.getSaldoAtual());
            }
            catch (SenhaInvalidaException e) {
                System.out.println(e);
            }
        }

        if(tipo.equals("pagamento")) {
            try {
                conta.Saque(valor);
                recebedor.Deposito(valor);
            } catch (Exception e) {
                System.out.println(e);
            }
            recebedor.setUltimaMovimentacao(Calendar.getInstance());
        }
        conta.setUltimaMovimentacao(Calendar.getInstance());
    }

    //Getters and Setters
    public Calendar getData() {
        return data;
    }
    public void setData(Calendar data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCanal() {
        return canal;
    }
    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Conta getConta() {
        return conta;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
    }

}
