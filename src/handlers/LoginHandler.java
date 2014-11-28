package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.UserNotFoundException;
import facades.UserFacadeDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class LoginHandler implements HttpHandler
{

    UserFacadeDB facade;
    private static final boolean dev = false;

    public LoginHandler() throws UserNotFoundException
    {
        facade = UserFacadeDB.getFacade(false);
        if (dev) {
//            facade.createTestData();
        }
    }

    @Override
    public void handle(HttpExchange he) throws IOException
    {
        String response = "";
        int statusCode = 200;
        String method = he.getRequestMethod().toUpperCase();

        switch (method) {
            case "GET":
                try {
                    String path = he.getRequestURI().getPath();
                    String last = path.substring(7);
                    String[] and = last.split("&");

                    String username = and[0];
                    String password = and[1];

                    if (and.length != 0) {
                        response = facade.login(username, password);
                    } else {
                        response = "ERROR";
                    }

                } catch (NumberFormatException nufe) {
                    response = "Id is not a number";
                    statusCode = 404;
                } catch (UserNotFoundException nofe) {
                    response = nofe.getMessage();
                    statusCode = 404;
                }
                break;

            case "POST":
                InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String jsonQuery = br.readLine();

                JsonParser jp = new JsonParser();
                JsonObject jo = (JsonObject) jp.parse(jsonQuery);

                String username = jo.get("username").getAsString();
                String password = jo.get("password").getAsString();

                 {
                    try {
                        response = facade.login(username, password);
                    } catch (UserNotFoundException nofe) {
                        response = nofe.getMessage();
                        statusCode = 404;
                    }
                }
                break;

            case "PUT":
                break;

            case "DELETE":
                break;
        }

        he.getResponseHeaders().add("Content-Type", "application/json");
        he.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
