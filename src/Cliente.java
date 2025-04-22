public class Cliente extends Pessoa {
    private Conta[] contas;
    public Cliente(String cpf) {
        this.cpf = cpf;
        System.out.println(ValidaCPF());
    }
    //métodos
}
