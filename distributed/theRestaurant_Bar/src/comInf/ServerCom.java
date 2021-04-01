package comInf;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *  Implementation of the communication channel, server's side, for a communication based on sending/receiving messages through sockets using TCP Protocol.
 *  The data transfer is based on objects, one at a time.
 */
public class ServerCom {
    
    /* Attributes */
    
    /**
     *  Listening socket
     *  @serialField listeningSocket
     */
    private ServerSocket listeningSocket = null;
    /**
     *  Communication Socket
     *    @serialField commSocket
     */
    private Socket commSocket = null;
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
     *  @param portNumb server's port number
     */
    public ServerCom (int portNumb) {
        serverPortNumb = portNumb;
    }

    /**
     *  Communication channel instantiation (2nd form).
     *  @param portNumb server's port number
     *  @param lSocket listening socket
     */
    public ServerCom (int portNumb, ServerSocket lSocket) {
        serverPortNumb = portNumb;
        listeningSocket = lSocket;
    }

    /**
     *  Establishment of the service.
     *  Listening socket's instantiation and his association to the local machine's address and the public port.
     */
    public void start () {
        try { 
            listeningSocket = new ServerSocket (serverPortNumb);
            setTimeout (10000);
        } catch (BindException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - não foi possível a associação do socket de escuta ao port: " +
                                    serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - ocorreu um erro indeterminado na associação do socket de escuta ao port: " +
                                    serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     *  Ending of the service.
     *  Closing of the listening socket.
     */
    public void end () {
        try { 
            listeningSocket.close ();
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - não foi possível fechar o socket de escuta!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     *  Listening process.
     *  Creation of a communication channel for a pending request.
     *  Communication socket's instantiation and its association to the server's address.
     *  Opening of the socket's entry and exit streams.
     * 
     *  @return returns the communication channel
     *  @throws SocketTimeoutException throws this exception when a timeout occurs on the listening socket
     */
    public ServerCom accept () throws SocketTimeoutException {
        ServerCom scon;                                      // canal de comunicação

        scon = new ServerCom(serverPortNumb, listeningSocket);
        try { 
            scon.commSocket = listeningSocket.accept();
        } catch (SocketTimeoutException e) { 
            throw new SocketTimeoutException ("Timeout!");
        } catch (SocketException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - foi fechado o socket de escuta durante o processo de escuta!");
            e.printStackTrace ();
            System.exit (1);
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - não foi possível abrir um canal de comunicação para um pedido pendente!");
            e.printStackTrace ();
            System.exit (1);
        }

        try { 
            scon.in = new ObjectInputStream (scon.commSocket.getInputStream ());
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - não foi possível abrir o canal de entrada do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try { 
            scon.out = new ObjectOutputStream (scon.commSocket.getOutputStream ());
        } catch (IOException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - não foi possível abrir o canal de saída do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        return scon;
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
     *  Definition of a timeout for the listening socket.
     *  @param time time margin in which the timeout does not occur
     */
    public void setTimeout (int time) {
        try { 
            listeningSocket.setSoTimeout (time);
        } catch (SocketException e) { 
            GenericIO.writelnString (Thread.currentThread ().getName () +
                                    " - ocorreu um erro na fixação de um timeout de escuta!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     *  Reading of an oject from the communication channel.
     *  @return returns object read
     */
    public Object readObject () {
        Object fromClient = null;                            // objecto

        try { 
            fromClient = in.readObject ();
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

        return fromClient;
    }

    /**
     *  Writing of an object on the communication channel.
     *  @param toClient object to be written
     */
    public void writeObject (Object toClient) {
        try { 
            out.writeObject (toClient);
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
