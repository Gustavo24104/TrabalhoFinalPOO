import java.io.Serializable;
import java.util.*;

public class Agencia implements Serializable {
    private String nomeFicticio;
    private int nroAgencia;
    private Endereco end;
    private Gerente gerente;
    private ArrayList<Funcionario> funcs;
    private ArrayList<Cliente> clientes;

    public Agencia(String nomeFicticio, int nroAgencia, Endereco end, Gerente gerente, ArrayList<Funcionario> funcs){
        this.nomeFicticio = nomeFicticio;
        this.nroAgencia = nroAgencia;
        this.end = end;
        this.gerente = gerente;
        this.funcs = new ArrayList<Funcionario>();
        for(Funcionario f : funcs) {
            this.funcs.add(new Funcionario(f));
        }
    }

    public void CadastraFuncionario(Funcionario f) {
        funcs.add(new Funcionario(f));
    }

    public void DemiteFuncionario(Funcionario f){
        funcs.remove(f);
    }

    public void CadastraCliente(Cliente c) {
        clientes.add(new Cliente(c));
    }

//Getters and Setters
    public String getNomeFicticio() {
        return nomeFicticio;
    }
    public void setNomeFicticio(String nomeFicticio) {
        this.nomeFicticio = nomeFicticio;
    }

    public int getNroAgencia() {
        return nroAgencia;
    }
    public void setNroAgencia(int nroAgencia) {
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

    public ArrayList<Funcionario> getFuncs() {
        return funcs;
    }
    public void setFuncs(ArrayList<Funcionario> funcs) {
        this.funcs = funcs;
    }
}
