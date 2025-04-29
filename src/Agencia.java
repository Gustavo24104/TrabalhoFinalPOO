import java.io.Serializable;
import java.util.*;

public class Agencia implements Serializable {
    private String nomeFicticio;
    private String nroAgencia; //6 digitos
    private Endereco end;
    private Gerente gerente;
    private TreeMap<String, Funcionario> funcs; // mapeia funcionario e cpf
    private TreeMap<String, Cliente> clientes; //maepia clientes e cpf

    public Agencia(String nomeFicticio, String nroAgencia, Endereco end){
        this.nomeFicticio = nomeFicticio;
        this.nroAgencia = nroAgencia;
        this.end = end;
        this.funcs = new TreeMap<>();
        clientes = new TreeMap<>();
//        for(Funcionario f : funcs) {
//            this.funcs.put(f.getCpf(), f);
//        }
    }

    public Agencia(String nomeFicticio, String nroAgencia) {
        this.nroAgencia = nroAgencia;
        this.nomeFicticio = nomeFicticio;
    }

    public void CadastraFuncionario(Funcionario f) {
        funcs.put(f.getCpf(), f);
    }




    public void DemiteFuncionario(Funcionario f){
        funcs.remove(f.getCpf());
    }


    public void CadastraCliente(Cliente c) {
        clientes.put(c.getCpf(), c);
    }

    public Cliente EcontraCliente(String cpf) {
        return clientes.get(cpf);
    }



    public void Menu() {
        Scanner sc = new Scanner(System.in);
        Logavel l = null;
        int escolha = 0;
        String user = "";
        System.out.println("Agência logada: " + nomeFicticio);
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção
                    1 - Logar como funcionario
                    2 - Logar como gerente
                    3 - Logar como cliente
                    -1 - Sair""");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    System.out.println("Digite cpf funcionário");
                    String cpf = sc.nextLine();
                    l = funcs.get(cpf);
                    user = cpf;
                    if(l == null) {
                        System.out.println("Funcionario de cpf " + cpf + " não encontrado!");
                        continue;
                    }
                    break;
                }

                case 2: {
                    l = gerente;
                    user = gerente.getCpf();
                    break;
                }

                case 3: {
                    System.out.println("Digite cpf do cliente:");
                    String cpf = sc.nextLine();
                    l = clientes.get(cpf);
                    user = cpf;
                    if(l == null) {
                        System.out.println("Cliente de cpf " + cpf + " não encontrado!");
                        continue;
                    }
                    break;
                }
                case -1: {
                    return;
                }
                default: {
                    System.out.println("Escolha invalida!");
                    continue;
                }
            }
        }
        System.out.println("Digite senha: ");
        String senha = sc.nextLine();
        if(l != null) l.Login(user, senha);
    }




    //Gera um número para uma conta, baseando-se no CPF do cliente, no nro da agencia e um numero aleatorio
    public String GerarNumeroDeConta(Cliente dono) {
        Random r = new Random();
        String result = "";
        int j = 0; //usado para indexar o cpf (ignorar '.' e '-')
        //String x1, x2, x3, x4, x5, x6;
        for(int i = 0; i < 6; ++i) {
            if(dono.getCpf().charAt(j) == '.' || dono.getCpf().charAt(j) == '-') j++; //ignorar '.' e '-'
            int aux = Integer.parseInt(String.valueOf(nroAgencia.charAt(i))) +
                    Integer.parseInt(String.valueOf(dono.getCpf().charAt(j++))) * r.nextInt(1, 100);
            aux = (aux % 9)+1;
            result = result.concat(String.valueOf(aux));
        }
        return result;
    }

    public String toString() {
        return "Nome: " + nomeFicticio + ". Número: " + nroAgencia; //+
//                "\nEndereço: " + end +
//                "\nGerente: " + gerente.getNome();
    }


//Getters and Setters
    public String getNomeFicticio() {
        return nomeFicticio;
    }
    public void setNomeFicticio(String nomeFicticio) {
        this.nomeFicticio = nomeFicticio;
    }

    public String getNroAgencia() {
        return nroAgencia;
    }
    public void setNroAgencia(String nroAgencia) {
        this.nroAgencia = nroAgencia;
    }

    public Endereco getEnd() {
        return end;
    }
    public void setEnd(Endereco end) {
        this.end = end;
    }

    public Gerente getGerente() {
        return gerente;
    }
    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }



}
