package Server;

import Client.TransAction;
import Server.Exceptions.NotFoundDeposit;

import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shima Abbasi on 7/19/2016.
 */
public class MainServer {
    static DepositParse depositParse = new DepositParse();
    static Deposit deposit = new Deposit();
    static ArrayList<Deposit> depositArray;

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        depositParse.jsonParserFunction();
        depositArray = depositParse.getDepositArray();
        mainServer.runServer();


    }

    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(depositParse.getServerPort());
            System.out.println("Server is up...");
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            List<TransAction> transActions = (List<TransAction>) objectInputStream.readObject();
            for (TransAction transAction : transActions) {
                System.out.println("eeeeeeeeeee");
                System.out.println(doTransAction(transAction));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public BigDecimal doTransAction(TransAction transAction) {
        BigDecimal newBalance = null;
        try {
            String type = transAction.getTransactionType();
            Deposit deposit1 = deposit.validation(transAction , depositArray);
            if (type.equalsIgnoreCase("deposit")) {
                newBalance = deposit.deposit(transAction.getTransactionAmount(), deposit1.getInitialBalance());
            } else if (type.equalsIgnoreCase("withdraw")) {
                newBalance = deposit.withDraw(transAction.getTransactionAmount(), deposit1.getInitialBalance());
            }
        } catch (NotFoundDeposit e) {
            System.out.println("??");
        }
        return newBalance;
    }
}
