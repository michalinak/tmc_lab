package pg.eti.tmc.jeppensen.lab.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static spark.Spark.*;

/**
 *
 * @author piotr
 */
public class ServerCore {
    
    private static Route route = new Route();
    private static Gson gson = new Gson();
    
    public static void main(String[] args) {
        //GSON INIT
        gson = new GsonBuilder().create();
        
        //JVM CONFIG, IN SOME OS CONFIGURATIONS SPARK WON'T START
        System.setProperty("java.net.preferIPv4Stack", "true");
        
        //THIS MUST BE CHANGED IN LABORATORY TO PORT 80 DUE TO ADMINISTRATOR RIGHTS ON WINDOWS BASED SYSTEMS,
        //SOME UNIX MACHINES WILL RESERVE ALL PORTS BELOW 1024 FOR ROOT USERS
        port(8080);
        
        //http://localhost:8080/getCurrentPostion
        get("/getCurrentPostion", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return gson.toJson(route.getCurrentPosition());
        });
        
        //http://localhost:8080/setGate
        post("/setGate", (request, response) -> {
            route.setGate(Integer.parseInt(request.queryParams("id")));
            response.status(200);
            response.type("application/json");
            return gson.toJson("OK");
        });
        
        //THIS WILL WORK ONLY IF GATE IS SET
        //WORKS ONLY WITH ID = 1, MOCKED...
        //http://localhost:8080/getRouteToGate
        get("/getRouteToGate", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return gson.toJson(route.getRoute());
        });
        
        //http://localhost:8080/getGate
        get("/getGate", (request, response) -> {
            response.type("application/json");
            int gate = route.getGate();
            if( gate ==-1)
            {
                response.status(204);
                return gson.toJson("Gate not set");
            }
            else
            {
                response.status(200);
                return gson.toJson(gate);
            }
        });
    }

}
