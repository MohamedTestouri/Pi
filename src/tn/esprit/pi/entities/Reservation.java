package tn.esprit.pi.entities;

public class Reservation {
    private int id;
    private int idRestaurant;
    private int idUser;
    private int places;

    public Reservation() {
    }

    public Reservation(int idRestaurant, int idUser, int places) {
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.places = places;
    }

    public Reservation(int id, int idRestaurant, int idUser, int places) {
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.places = places;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }
}
