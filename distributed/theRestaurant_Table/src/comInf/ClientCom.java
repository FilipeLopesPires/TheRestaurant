package comInf;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *  Implementation of the communication channel, client's side, for a communication based on sending/receiving messages through sockets using TCP Protocol.
 *  The data transfer is based on objects, one at a time.
 */
public class ClientCom {
    
    /* Attributes */
    
    /**
     *  Communication Socket
     *    @serialField commSocket
     */
    private Socket commSocket = null;
    /**
     *  Computational system's name, where the server is localized
     *    @serialField serverHostName
     */
    private String serverHostName = null;
    /**
     *  Server's port number
     *    @serialField serverPortNumb
     */
    private int serverPortNumb;
    /**
     *  Communication channel's entry stream
     *    @serialField in
     */
    private ObjectInputStream in = null;
    /**
     *  Communication channel's exit stream
     *    @serialField out
     */
    private ObjectOutputStream out = null;
    
    /* Constructors */
    
    /**
     *  Communication channel instantiation.
     *
     *    @param hostName computational system's name, where the server is localized
     *    @param portNumb server's port number
     */
    public ClientCom (String hostName, int portNumb) {
        serverHostName = hostName;
        serverPortNumb = portNumb;
    }

    /* Methods */
    
    /**
     *  Communication channel opening.
     *  Communication socket's instantiation and its association to the server's address.
     *  Opening of the socket's entry and exit streams.
     *
     *  @return returns true, if communication channel was opened, false if not
     */
    public boolean open() {
        boolean success = true;
        SocketAddress serverAddress = new InetSocketAddress (serverHostName, serverPortNumb);

        try { commSocket = new Socket();
          commSocket.connect (serverAddress);
        } catch (UnknownHostException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - o nome do sistema computacional onde reside o servidor é desconhecido: " +
                                serverHostName + "!");
            e.printStackTrace ();
            System.exit (1);
        } catch (NoRouteToHostException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - o nome do sistema computacional onde reside o servidor é inatingível: " +
                                serverHostName + "!");
            e.printStackTrace ();
            System.exit (1);
        } catch (ConnectException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - o servidor não responde em: " + serverHostName + "." + serverPortNumb + "!");
            if (e.getMessage ().equals ("Connection refused")) { 
                success = false; 
            } else {
                GenericIO.writelnString (e.getMessage () + "!");
                e.printStackTrace ();
                System.exit (1);
            }
        } catch (SocketTimeoutException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - ocorreu um time out no estabelecimento da ligação a: " +
                                serverHostName + "." + serverPortNumb + "!");
            success = false;
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - ocorreu um erro indeterminado no estabelecimento da ligação a: " +
                                serverHostName + "." + serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        }

        if (!success) return (success);

        try { 
            out = new ObjectOutputStream (commSocket.getOutputStream ());
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - não foi possível abrir o canal de saída do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            in = new ObjectInputStream (commSocket.getInputStream ());
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                " - não foi possível abrir o canal de entrada do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        return (success);
    }

    /**
     *  Closing of the communication channel, it's streams and it's socket.
     */
    public void close () {
        try {
            in.close();
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o canal de entrada do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            out.close();
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o canal de saída do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - não foi possível fechar o socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     *  Reading of an oject from the communication channel.
     *  @return returns object read
     */
    public Object readObject () {
        Object fromServer = null;                            // objecto

        try { fromServer = in.readObject ();
        } catch (InvalidClassException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto lido não é passível de desserialização!");
            e.printStackTrace ();
            System.exit (1);
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - erro na leitura de um objecto do canal de entrada do socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto lido corresponde a um tipo de dados desconhecido!");
            e.printStackTrace ();
            System.exit (1);
        }

        return fromServer;
    }

    /**
     *  Writing of an object on the communication channel.
     *  @param toServer object to be written
     */
    public void writeObject (Object toServer) {
        try {
            out.writeObject (toServer);
        } catch (InvalidClassException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto a ser escrito não é passível de serialização!");
            e.printStackTrace ();
            System.exit (1);
        } catch (NotSerializableException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - o objecto a ser escrito pertence a um tipo de dados não serializável!");
            e.printStackTrace ();
            System.exit (1);
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - erro na escrita de um objecto do canal de saída do socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
