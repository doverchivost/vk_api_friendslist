import java.io.*;
import java.net.*;
import java.lang.*;
import org.json.*;
import java.nio.charset.Charset;

import org.json.*;

public class Main {
    public static void main (String[] args) throws JSONException, IOException {
        String token = "TOKEN";
        String urlFriends = "https://api.vk.com/method/friends.get?v=5.52&fields=%22first_name%22&access_token=" + token;
        JSONObject obj = readJsonFromUrl(urlFriends);
        int counter = obj.getJSONObject("response").getInt("count");
        System.out.println("Количество друзей: " + counter);
        System.out.println("                Список друзей");

        JSONArray friends = obj.getJSONObject("response").getJSONArray("items");

        for (int i = 0; i < counter; i++) {
            JSONObject user = friends.getJSONObject(i);
            int userID = (int) user.get("id");
            String name = user.getString("first_name");
            String surname = user.getString("last_name");

            tablePrint(i+1, name, surname, userID);
        }
    }
        // number Name Surname ID
    public static void tablePrint (int n, String name, String surname, int id) {
        System.out.format("%3s%10s%20s%20s", n, name, surname, id);
        System.out.println();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
