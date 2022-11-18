package com.example.villageplanner.StoreTests;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.example.villageplanner.Store;

public class GetTest{
    @Test
    public void incorrectStore_getZero() {
        Store storeOne = new Store("Trader Poes");
        Store storeTwo = new Store("Marget");
        Store storeThree = new Store("Three Brother's Bike Shop");
        Store storeFour = new Store("fasdjfhaiwejfh");
        assertEquals(0, storeOne.getOpeningTime(), 0);
        assertEquals(0, storeTwo.getClosingTime(), 0);
        assertEquals(0, storeThree.getLatitude(), 0);
        assertEquals(0, storeFour.getLongitude(), 0);
    }

    @Test
    public void realStore_getOpeningTime() {
        Store storeOne = new Store("AmAzoN Locker");
        Store storeTwo = new Store("Chinese Street Food");
        Store storeThree = new Store("DULce");
        assertEquals(9, storeOne.getOpeningTime(), 0);
        assertEquals(11, storeTwo.getOpeningTime(), 0);
        assertEquals(8, storeThree.getOpeningTime(), 0);
    }

    @Test
    public void realStore_getClosingTime() {
        Store storeOne = new Store("Cava");
        Store storeTwo = new Store("City Tacos");
        Store storeThree = new Store("KobUNGa");
        assertEquals(22, storeOne.getClosingTime(), 0);
        assertEquals(21, storeTwo.getClosingTime(), 0);
        assertEquals(20, storeThree.getClosingTime(), 0);
    }

    @Test
    public void realStore_getLatitude() {
        Store storeOne = new Store("Insomnia Cookies");
        Store storeTwo = new Store("Mac Repair CLINic");
        Store storeThree = new Store("Ramen Kenjo");
        assertEquals(34.02502554830927, storeOne.getLatitude(), 0);
        assertEquals(34.02454440716868, storeTwo.getLatitude(), 0);
        assertEquals(34.0246127889896, storeThree.getLatitude(), 0);
    }

    @Test
    public void realStore_getLongitude() {
        Store storeOne = new Store("Rock and Reilly's");
        Store storeTwo = new Store("Solé bicycles");
        Store storeThree = new Store("StarBUCKs");
        assertEquals(-118.28415962834046, storeOne.getLongitude(), 0);
        assertEquals(-118.28477145964057, storeTwo.getLongitude(), 0);
        assertEquals(-118.28407846759262, storeThree.getLongitude(), 0);
    }

}
