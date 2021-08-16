package tn.esprit.pi.entities;

import java.util.Comparator;

public class Restaurant {
    private int id;
    private String name;
    private String specialite;
    private int place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Restaurant() {
    }

    public Restaurant(int id, String name, String specialite, int place) {
        this.id = id;
        this.name = name;
        this.specialite = specialite;
        this.place = place;
    }
    public static Comparator<Restaurant> nameRestaurantComparator = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return (int) (o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
        }
    };
    public static Comparator<Restaurant> specialiteRestaurantComparator = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return (int) (o1.getSpecialite().toLowerCase().compareTo(o2.getSpecialite().toLowerCase()));
        }
    };
    }
