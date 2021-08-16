package tn.esprit.pi.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pi.entities.Restaurant;
import tn.esprit.pi.services.RestaurantService;

import java.util.ArrayList;
import java.util.Collections;

public class RestaurantForm extends Form {
    Form current;
    RestaurantService restaurantService = new RestaurantService();
    ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();

    public RestaurantForm() {
        current = this;
        setTitle("Restaurants");
        setLayout(BoxLayout.y());
        restaurantArrayList = restaurantService.showAll();
        Collections.reverse(restaurantArrayList);
        showRestaurant();
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Sort by Name", null, (evt) -> {
            removeAll();
            Collections.sort(restaurantArrayList, Restaurant.nameRestaurantComparator);
            showRestaurant();
        });
        getToolbar().addCommandToOverflowMenu("Sort by Speciality", null, (evt) -> {
            removeAll();
            Collections.sort(restaurantArrayList, Restaurant.specialiteRestaurantComparator);
            showRestaurant();
        });
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

    public void showRestaurant() {
        for (Restaurant restaurant : restaurantArrayList) {
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(restaurant.getName() + "");
            multiButton.setTextLine2("Specialité: " + restaurant.getSpecialite());
            multiButton.setTextLine3("Places disponible: " + restaurant.getPlace());
            multiButton.setUIID(restaurant.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Button", 10.0f));
            multiButton.addActionListener(l -> {
                new RestaurantDetailForm(current, restaurant).show();
            });
            add(multiButton);
        }
    }
}
