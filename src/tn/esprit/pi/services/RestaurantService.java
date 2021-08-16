package tn.esprit.pi.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pi.entities.Restaurant;
import tn.esprit.pi.utils.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RestaurantService {
    public ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    public static RestaurantService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public RestaurantService() {
        req = new ConnectionRequest();
    }

    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }

    public ArrayList<Restaurant> parseRestaurant(String jsonText) {
        try {
            restaurantArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> restaurantListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) restaurantListJson.get("root");
            for (Map<String, Object> obj : list) {
                Restaurant restaurant = new Restaurant((int) Float.parseFloat(obj.get("id").toString()),
                        obj.get("nom").toString(),
                        obj.get("specialite").toString(),
                        (int) Float.parseFloat(obj.get("places").toString()));
                restaurantArrayList.add(restaurant);
            }
        } catch (IOException ex) {
        }
        return restaurantArrayList;
    }
    public ArrayList<Restaurant> showAll() {
        String url = Database.BASE_URL + "restaurant/api/show"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                restaurantArrayList = parseRestaurant(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return restaurantArrayList;
    }
}

/*
 parseInt(obj.get("id").toString()),
 parseInt(obj.get("idRestaurant").toString()),
 parseInt(obj.get("idUser").toString()),
 parseInt(obj.get("place").toString())
*/