import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Networking {

    private final String APIKEY = "9d3364870e904465806170034221605";

    public String getJSON(String zip) {
        try {
            URI myUri = URI.create("http://api.weatherapi.com/v1/current.json" + "?key=" + APIKEY + "&q=" + zip);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // UNCOMMENT TO PRINT OTHER PARTS OF THE RESPONSE
            //System.out.println(response.statusCode());
            //System.out.println(response.headers());
            System.out.println(response.body());
            return (response.body());

        } catch (Exception e) {
            return (e.getMessage());
        }
    }

    public double parseTemp(String json)
    {
        JSONObject woo = new JSONObject(json);
        JSONObject woo2 = woo.getJSONObject("current");
        return woo2.getDouble("temp_f");
    }

    public String parseImageURL(String json)
    {
        JSONObject woo = new JSONObject(json);
        JSONObject woo2 = woo.getJSONObject("current");
        JSONObject woo3 = woo2.getJSONObject("condition");
        return woo3.getString("icon");
    }

    public String parseCondition(String json)
    {
        JSONObject woo = new JSONObject(json);
        JSONObject woo2 = woo.getJSONObject("current");
        JSONObject woo3 = woo2.getJSONObject("condition");
        return woo3.getString("text");
    }
}
