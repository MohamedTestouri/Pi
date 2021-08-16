package tn.esprit.pi.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pi.entities.Reservation;
import tn.esprit.pi.entities.Restaurant;
import tn.esprit.pi.services.ReservationService;
import tn.esprit.pi.services.RestaurantService;

import java.util.ArrayList;
import java.util.Collections;

public class ReservationForm extends Form {
    Form current;
    ReservationService reservationService = new ReservationService();
    ArrayList<Reservation> reservationArrayList = new ArrayList<>();
    public ReservationForm(){
        current = this;
        setTitle("Reservation");
        setLayout(BoxLayout.y());
        reservationArrayList = reservationService.showAll();
        Collections.reverse(reservationArrayList);
        showReservation();
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {
        });
        getToolbar().addCommandToLeftSideMenu("Restaurants", null, (evt) -> {
            new RestaurantForm().show();
        });
        getToolbar().addCommandToLeftSideMenu("Reservations", null, (evt) -> {
            new ReservationForm().show();
        });
    }

    public void showReservation() {
        for (Reservation reservation : reservationArrayList) {
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1("ID de reservation: "+reservation.getId());
            multiButton.setTextLine2("ID de Restaurant: " + reservation.getIdRestaurant());
            multiButton.setTextLine3("Places reservé: " + reservation.getPlaces());
            multiButton.setUIID(reservation.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Button", 10.0f));
            multiButton.addActionListener(l -> {
                new ReservationDetailForm(current, reservation).show();
            });
            add(multiButton);
        }
    }
}
