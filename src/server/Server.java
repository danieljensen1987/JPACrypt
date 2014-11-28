package server;

import com.sun.net.httpserver.HttpServer;
import exceptions.UserNotFoundException;
import handlers.LoginHandler;
import handlers.UserHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server 
{
    static int port = 9191;
    static String ip = "localhost";
    
    public void run() throws IOException, UserNotFoundException{
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/login" , new LoginHandler());
        server.createContext("/user" , new UserHandler());
        server.start();
        System.out.println("Server is listening on port: " + port);
    }
    
    public static void main(String[] args) throws IOException, UserNotFoundException
    {
        if (args.length >= 2){
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        new Server().run();
    }
}
