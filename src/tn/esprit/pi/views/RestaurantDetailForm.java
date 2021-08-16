package tn.esprit.pi.views;

import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pi.entities.Reservation;
import tn.esprit.pi.entities.Restaurant;
import tn.esprit.pi.services.ReservationService;

import static java.lang.Integer.parseInt;

public class RestaurantDetailForm extends Form {
    ReservationService reservationService = new ReservationService();

    public RestaurantDetailForm(Form previous, Restaurant restaurant) {
        setTitle(restaurant.getName() + " Details");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        Label nameLabel = new Label("Name: " + restaurant.getName());
        Label speialiteLabel = new Label("Specialité: " + restaurant.getSpecialite());
        Label placeLabel = new Label("Places disponible: " + restaurant.getPlace());
        Label reservationPlaceLabel = new Label("Nombre de places à reserver?");

        TextField reservationPlaceTextField = new TextField("1", "Nombre de places à reserver");

        Button reserverButton = new Button("RESERVER");
        Button submitButton = new Button("RESERVER");
        submitButton.addActionListener(evt -> {
            Reservation reservation = new Reservation(restaurant.getId(), 10, parseInt(reservationPlaceTextField.getText().toString()));
            reservationService.reserver(reservation);
            previous.showBack();
        });
        reserverButton.addActionListener(evt -> {
            reservationPlaceLabel.setVisible(true);
            reservationPlaceTextField.setVisible(true);
            submitButton.setVisible(true);
            reserverButton.setVisible(false);
            this.repaint();
            this.revalidate();
        });
        submitButton.setVisible(false);
        reservationPlaceTextField.setVisible(false);
        reservationPlaceLabel.setVisible(false);
        addAll(nameLabel, speialiteLabel, placeLabel, reserverButton, reservationPlaceLabel, reservationPlaceTextField, submitButton); //ADD ALL COMPONENTS TO THE VIEW
        if (placeLabel.getText().equals("Places disponible: 0")) {
            reserverButton.setEnabled(false);
            add(new Label("Aucune place disponible!"));
        }
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Share", null, (evt) -> {
            Display.getInstance().sendMessage(new String[]{""}, "Join Me !", new Message("Check out this restaurant: " + restaurant.getName()));
        });
    }
}
