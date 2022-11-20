package com.example.villageplanner.HomePageTests;

import static org.junit.Assert.assertEquals;
import com.google.android.gms.maps.model.LatLng;
import org.junit.Test;

import static com.example.villageplanner.HomeLogic.HomepageActivity.getUrl;

public class URLTest {
    @Test
    public void urlCorrect() {
        LatLng home = new LatLng(34.023893213226344, -118.29465112595155);
        LatLng storeOne = new LatLng(34.025289107299386, -118.28452640000003);
        LatLng storeTwo = new LatLng(34.02479510266673, -118.28598891350403);
        String urlOne = "https://maps.googleapis.com/maps/api/directions/json?origin=34.023893213226344,-118.29465112595155&destination=34.02528910729938-118.28452640000003&mode=walking&key=AIzaSyBe27v3Nkt9BwKJ8h2MyggirRIen7A0eEE";
        String urlTwo = "https://maps.googleapis.com/maps/api/directions/json?origin=34.023893213226344,-118.29465112595155&destination=34.025289107299386,-118.28452640000003&mode=driving&key=AIzaSyBe27v3Nkt9BwKJ8h2MyggirRIen7A0eEE";
        String urlThree = "https://maps.googleapis.com/maps/api/directions/json?origin=34.023893213226344,-118.29465112595155&destination=34.02479510266673,-118.28598891350403&mode=walking&key=AIzaSyBe27v3Nkt9BwKJ8h2MyggirRIen7A0eEE";
        String urlFour = "https://maps.googleapis.com/maps/api/directions/json?origin=34.023893213226344,-118.29465112595155&destination=34.02479510266673,-118.28598891350403&mode=driving&key=AIzaSyBe27v3Nkt9BwKJ8h2MyggirRIen7A0eEE";
        assertEquals(urlOne, getUrl(home, storeOne, "walking"));
        assertEquals(urlTwo, getUrl(home, storeOne, "driving"));
        assertEquals(urlThree, getUrl(home, storeTwo, "walking"));
        assertEquals(urlFour, getUrl(home, storeTwo, "driving"));
    }
}
