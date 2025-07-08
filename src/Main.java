// IMPORT
import java.util.ArrayList;
import java.util.Scanner;
import util.*;
import util.conti.*;

// MAIN
public class Main {
    static ArrayList<Cliente> clienti = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static String MENU = """
┌───────── \u001B[1mBANKING SYSTEM MANAGEMENT\u001B[0m ────────┐

  1. Crea nuovo cliente
  2. Apri nuovo conto
  3. Visualizza conti di un cliente
  4. Deposita denaro
  5. Preleva denaro
  6. Mostra dettagli conto
  7. Calcola interessi
  8. Visualizza transazioni
  9. Visualizza clienti
  0. Esci dal programma
    
└────────────────────────────────────────────┘
""";

    public static void main(String [] args){
        boolean tmp = true;

        try{
            while (tmp) {
                // OPZIONE UTENTE
                System.out.println(MENU);

                int opzioneUtente = validInput();
                boolean val = true;
                double importo;
                ContoBancario conto;

                // SWITCH
                switch (opzioneUtente) {
                    case 1:
                        creaUtente();
                        break;

                    case 2:
                        apriNuovoConto();
                        break;

                    case 3:
                        visualizzaContiCliente();
                        break;

                    case 4:
                        do {
                            try {
                                val = true;
                                System.out.print("Importo da depositare: ");
                                importo = input.nextDouble();
                                input.nextLine();
                            }
                            catch (Exception e){
                                System.out.println("Valore di importo non valido...");
                                importo = -1;
                            }

                            if (importo <= 0) {
                                System.out.println("L'importo deve essere maggiore di zero!");
                                val = false;
                            }
                        }
                        while (!val);

                        conto = inputIban();
                        if(conto != null) {
                            depositaDenaro(conto, importo, true);
                        }
                        break;

                    case 5:
                        do {
                            try{
                                val = true;
                                System.out.print("Importo da prelevare: ");
                                importo = input.nextDouble();
                                input.nextLine();
                            }
                            catch (Exception e){
                                System.out.println("Valore di importo non valido...");
                                importo = -1;
                            }

                            if (importo <= 0) {
                                System.out.println("L'importo deve essere maggiore di zero!");
                                val = false;
                            }
                        }
                        while (!val);

                        conto = inputIban();
                        if(conto != null) {
                            depositaDenaro(conto, importo, false);
                        }
                        break;

                    case 6:
                        conto = inputIban();
                        if(conto != null) {
                            visualizzaDettagliConto(conto);
                        }
                        break;

                    case 7:
                        conto = inputIban();
                        if(conto != null) {
                            calcolaInteresseConto(conto);
                        }
                        break;

                    case 8:
                        conto = inputIban();

                        if(conto != null) {
                            visualizzaTransazioni(conto);
                        }
                        break;

                    case 9:
                        for (Cliente cliente : clienti) {
                            cliente.stampaCliente();
                        }
                        break;

                    case 0:
                        System.out.println("A presto...");
                        tmp = false;
                        break;

                    default:
                        System.out.println("Opzione non valida... Riprovare");
                }
            }
        }

        catch (Exception e){
            System.out.println("Qualcosa è andato storto...");
        }

        finally {
            if(!tmp){
                input.close();
            }

            else {
                System.out.println("Premere INVIO per continuare...");
                input.nextLine();

            }
        }
    }

    public static int validInput() {
        while(true){
            int opzioneUtente;

            try {
                System.out.print("Scegliere un opzione: ");
                opzioneUtente = input.nextInt();
                input.nextLine();

                return opzioneUtente;
            }

            catch (Exception e) {
                System.out.println("Valore non valido... Riprovare");
                input.nextLine();
            }
        }
    }

    public static void creaUtente(){
        String nomeUtente,cognomeUtente;

        System.out.print("Nome: ");
        nomeUtente = input.nextLine();

        System.out.print("Cognome: ");
        cognomeUtente = input.nextLine();

        Cliente cliente = new Cliente(nomeUtente, cognomeUtente);
        clienti.add(cliente);
        System.out.println("Cliente creato con ID: " + cliente.id + "\n");
    }

    public static void apriNuovoConto() {
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente presente. Creane uno prima.");
            return;
        }
        System.out.print("Inserisci ID cliente: ");
        int idCliente = input.nextInt();
        input.nextLine();

        Cliente cliente = null;
        for (Cliente c : clienti) {
            if (c.id == idCliente) {
                cliente = c;
                break;
            }
        }
        if (cliente == null) {
            System.out.print("\nCliente non trovato!");
            return;
        }

        System.out.print("""

┌───────── \u001B[1mCONTI BANCARI\u001B[0m ────────┐

  1. Conto Corrente
  2. Conto Risparmio

└────────────────────────────────┘

Selezionare un tipo di Conto bancario:\s""");
        String opzioneConto = input.nextLine();

        String ibanNuovo = generaIbanUnico();
        double saldoNuovo = 0.0;
        TipoConto tipo;

        switch (opzioneConto) {
            case "1":
                tipo = TipoConto.CORRENTE;
                cliente.conti.add(new ContoCorrente(ibanNuovo, saldoNuovo, cliente, tipo));
                System.out.println("Conto Corrente creato con IBAN: " + ibanNuovo);
                break;

            case "2":
                tipo = TipoConto.RISPARMIO;
                cliente.conti.add(new ContoRisparmio(ibanNuovo, saldoNuovo, cliente, tipo));
                System.out.println("Conto Risparmio creato con IBAN: " + ibanNuovo);
                break;

            default:
                System.out.println("Conto non valido...");
        }

        System.out.println();
    }

    public static String generaIbanUnico() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String iban;
        boolean unico;
        do {
            StringBuilder sb = new StringBuilder("IT");
            for (int i = 0; i < 25; i++) {
                int idx = (int) (Math.random() * caratteri.length());
                sb.append(caratteri.charAt(idx));
            }
            iban = sb.toString();
            unico = true;
            // Controlla tutti i clienti e tutti i loro conti
            for (Cliente cliente : clienti) {
                for (ContoBancario conto : cliente.conti) {
                    if (conto.iban.equals(iban)) {
                        unico = false;
                        break;
                    }
                }
                if (!unico)
                    break;
            }

        } while (!unico);

        return iban;
    }

    public static void visualizzaContiCliente(){
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente presente. Creane uno prima.");
            return;
        }

        System.out.print("Inserisci ID cliente: ");
        int idCliente = input.nextInt();
        input.nextLine();

        System.out.println();

        Cliente cliente = null;
        for (Cliente c : clienti) {
            if (c.id == idCliente) {
                cliente = c;
                break;
            }
        }

        if (cliente == null) {
            System.out.println("Cliente non trovato!\n");
            return;
        }

        if (cliente.conti == null || cliente.conti.isEmpty()) {
            System.out.println("Nessun conto associato.");
            System.out.println("\n");
            return;
        }

        System.out.println("┌──────────────────────── \u001B[1mCONTI CLIENTE\u001B[0m ───────────────────────┐\n");
        for(ContoBancario conto : cliente.conti) {
            System.out.println("  Tipo Conto: " + conto.tipo);
            System.out.println("  IBAN: " + conto.iban);
            System.out.println("  saldo: " + conto.saldo);
            System.out.println();
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘\n");
    }

    public static ContoBancario inputIban(){
        System.out.print("Inserire l'IBAN del conto da visualizzare: ");
        String iban = input.nextLine();

        System.out.println();

        for(Cliente cliente : clienti) {
            for (ContoBancario conto : cliente.conti) {
                if (conto.iban.equals(iban)) {
                    return conto;
                }
            }
        }

        System.out.println("Non è stato trovato alcun conto di IBAN: " + iban + "...");
        return null;
    }

    public static void visualizzaDettagliConto(ContoBancario conto){
        conto.stampaDettagli();
    }

    public static void calcolaInteresseConto(ContoBancario conto){
        double interesse = conto.calcolaInteresse();
        System.out.println("Interesse: " + interesse + "\n");
    }

    public static void depositaDenaro(ContoBancario conto, double importo, boolean deposita) {
        TipoTransazione tipo;

        if(deposita) {
            tipo = TipoTransazione.DEPOSITO;
            conto.deposita(importo);
        }
        else{
            tipo = TipoTransazione.PRELIEVO;
            if(conto instanceof ContoRisparmio) {
                if (importo > conto.saldo) {
                    System.out.println("Saldo insufficiente, Impossibile eseguire il prelievo...");
                    return;
                }
                else {
                    conto.preleva(importo);
                }
            }

            else{
                if (importo > conto.saldo + ((ContoCorrente) conto).scopertoMax) {
                    System.out.println("Saldo insufficiente, Impossibile eseguire il prelievo...");
                    return;
                }

                else {
                    conto.preleva(importo);
                }
            }
        }

        System.out.println("Descrizione: ");
        String descrizione = input.nextLine();

        conto.transazioni.add(new Transazione(importo, tipo, descrizione));
        System.out.println();
    }

    public static void visualizzaTransazioni(ContoBancario conto){
        System.out.println("┌──────────────────────── \u001B[1mTRANSAZIONI\u001B[0m ───────────────────────┐\n");

        for(Transazione transazione : conto.transazioni){
            System.out.println("  " + transazione.tipo);
            System.out.println("  Descrizione: " + transazione.descrizione);
            System.out.println("  Importo: " + transazione.importo);
            System.out.println("  Data: " + transazione.data);
            System.out.println();
        }

        System.out.println("└────────────────────────────────────────────────────────────┘\n");
    }
}