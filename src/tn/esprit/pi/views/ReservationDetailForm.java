package tn.esprit.pi.views;

import com.codename1.messaging.Message;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pi.entities.Reservation;
import tn.esprit.pi.services.ReservationService;

public class ReservationDetailForm extends Form{
    public ReservationDetailForm(Form previous, Reservation reservation) {
        setTitle("Reservation Details");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        Label reservationLabel = new Label("ID de Reservation: " + reservation.getId());
        Label restaurantLabel = new Label("ID de Restaurant: " + reservation.getIdRestaurant());
        Label placeLabel = new Label("Places reservé: " + reservation.getPlaces());
        addAll(reservationLabel, restaurantLabel, placeLabel);
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Share", null, (evt) -> {
            Display.getInstance().sendMessage(new String[]{""}, "Join Me !", new Message("Check out this restaurant: " + reservation.getIdRestaurant()));
        });
    }
}
