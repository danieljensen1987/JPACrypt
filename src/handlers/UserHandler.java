package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.AlreadyExcistException;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
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
    String jsonQuery, userName, password, role, currentPassword, newPassword;

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

                userName = jo.get("userName").getAsString();
                password = jo.get("password").getAsString();
                role = jo.get("role").getAsString();

                try {
                    response = "" + facade.addUser(userName, password, role);
                } catch (AlreadyExcistException ex) {
                    response = ex.getMessage();
                    statusCode = 404;
                }
                break;

            case "PUT":
                try {
                    isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                    br = new BufferedReader(isr);
                    jsonQuery = br.readLine();

                    jp = new JsonParser();
                    jo = (JsonObject) jp.parse(jsonQuery);
                    
                    userName = jo.get("userName").getAsString();
                    currentPassword = jo.get("currentPassword").getAsString();
                    newPassword = jo.get("newPassword").getAsString();
                    
                    if (!currentPassword.equals(newPassword)){
                        response = "" + facade.changePassword(userName, newPassword);
                    }
                } catch (UserNotFoundException ex) {
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

                userName = jo.get("userName").getAsString();

                try {
                    response = "" + facade.deleteUser(userName);
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
