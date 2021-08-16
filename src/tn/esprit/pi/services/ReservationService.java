package tn.esprit.pi.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pi.entities.Reservation;
import tn.esprit.pi.utils.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ReservationService {
    public ArrayList<Reservation> reservationArrayList = new ArrayList<>();
    public static ReservationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ReservationService() {
        req = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public ArrayList<Reservation> parseReservation(String jsonText) {
        try {
            reservationArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> reservationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) reservationListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reservation reservation = new Reservation((int) Float.parseFloat(obj.get("id").toString()),
                        (int) Float.parseFloat(obj.get("idrestaurant").toString()),
                        (int) Float.parseFloat(obj.get("iduser").toString()),
                        (int) Float.parseFloat(obj.get("places").toString()));
                reservationArrayList.add(reservation);
            }
        } catch (IOException ex) {
        }
        return reservationArrayList;
    }

    public ArrayList<Reservation> showAll() {
        String url = Database.BASE_URL + "reservation/api/show?idUser=10"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservationArrayList = parseReservation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservationArrayList;
    }

    public boolean reserver(Reservation reservation) {
        String url = Database.BASE_URL + "reservation/api/reserver?idRestaurant=" + reservation.getIdRestaurant()
                + "&idUser=" + reservation.getIdUser()
                + "&places=" + reservation.getPlaces(); // Add Symfony URL Here
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
