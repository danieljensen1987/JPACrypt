package server;

import com.sun.net.httpserver.HttpServer;
import exceptions.NotFoundException;
import handlers.LoginHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server 
{
    static int port = 9191;
    static String ip = "localhost";
    
    public void run() throws IOException, NotFoundException{
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/login" , new LoginHandler());
        server.start();
        System.out.println("Server is listening on port: " + port);
    }
    
    public static void main(String[] args) throws IOException, NotFoundException
    {
        if (args.length >= 2){
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        new Server().run();
    }
}
