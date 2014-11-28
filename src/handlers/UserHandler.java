package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.SameException;
import exceptions.UserNotFoundException;
import facades.UserFacadeDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class UserHandler implements HttpHandler
{

    private static final boolean dev = false;
    UserFacadeDB facade;
    InputStreamReader isr;
    BufferedReader br;
    JsonParser jp;
    JsonObject jo;
    String jsonQuery, username, password, role;

    public UserHandler() throws UserNotFoundException
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
                break;

            case "POST":

                isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                br = new BufferedReader(isr);
                jsonQuery = br.readLine();

                jp = new JsonParser();
                jo = (JsonObject) jp.parse(jsonQuery);

                username = jo.get("username").getAsString();
                password = jo.get("password").getAsString();
                role = jo.get("role").getAsString();

                try {
                    response = "" + facade.addUser(username, password, role);
                } catch (RoleNotFoundException | AlreadyExcistException ex) {
                    response = ex.getMessage();
                    statusCode = 404;
                }

                break;

            case "PUT":
                isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                br = new BufferedReader(isr);
                jsonQuery = br.readLine();

                jp = new JsonParser();
                jo = (JsonObject) jp.parse(jsonQuery);

                username = jo.get("username").getAsString();
                password = jo.get("password").getAsString();

                try {
                    response = "" + facade.changePassword(username, password);
                } catch (UserNotFoundException | SameException ex) {
                    response = ex.getMessage();
                    statusCode = 404;
                }

                break;

            case "DELETE":
                isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                br = new BufferedReader(isr);
                jsonQuery = br.readLine();

                jp = new JsonParser();
                jo = (JsonObject) jp.parse(jsonQuery);

                username = jo.get("username").getAsString();

                try {
                    response = "" + facade.deleteUser(username);
                } catch (UserNotFoundException ex) {
                    response = ex.getMessage();
                    statusCode = 404;
                }
                break;
        }

        he.getResponseHeaders().add("Content-Type", "application/json");
        he.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
