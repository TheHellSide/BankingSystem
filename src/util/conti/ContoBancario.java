package util.conti;

import util.Cliente;
import util.TipoConto;
import util.Transazione;

import java.util.ArrayList;

// CONTI
public abstract class ContoBancario {
    public String iban;
    public TipoConto tipo;
    public double saldo;
    public Cliente proprietario;
    public ArrayList<Transazione> transazioni = new ArrayList<>();

    public void deposita(double importo) {
        saldo += verificaOperazioneBancaria(importo);
    }

    public void preleva(double importo){
        saldo -= verificaOperazioneBancaria(importo);
    }

    double verificaOperazioneBancaria(double importo){
        String messaggio = importo > 0 ? "Operazione eseguita correttamente!" : "Importo non valido...";
        System.out.println(messaggio);
        if(messaggio.equals("Operazione eseguita correttamente!")){
            return importo;
        }

        return 0;
    }

    public abstract void stampaDettagli();
    public abstract double calcolaInteresse();
}
