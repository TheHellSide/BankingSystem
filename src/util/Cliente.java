package util;

import util.conti.ContoBancario;

import java.util.ArrayList;

public class Cliente {
    private static int counter = 0;
    public int id;
    public String nome;
    public String cognome;
    public ArrayList<ContoBancario> conti = new ArrayList<>();

    public Cliente(String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
        this.id = counter++;
    }

    public void stampaCliente(){
        System.out.println("\n┌──────────────────────── \u001B[1mINFORMAZIONI CLIENTE\u001B[0m ───────────────────────┐\n");

        System.out.println("\u001B[1m  Cliente: " + cognome + " " + nome + "\u001B[0m");
        System.out.println();

        if (conti == null || conti.isEmpty()) {
            System.out.println("  Nessun conto associato.\n");
            System.out.println("└─────────────────────────────────────────────────────────────────────┘\n");
            return;
        }

        for(ContoBancario conto : conti) {
            System.out.println("  Tipo Conto: " + conto.tipo);
            System.out.println("  IBAN: " + conto.iban);
            System.out.println();
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────┘\n");
    }
}