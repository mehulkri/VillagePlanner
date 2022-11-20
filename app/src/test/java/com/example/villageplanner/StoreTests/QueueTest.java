package com.example.villageplanner.StoreTests;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.example.villageplanner.Store;

public class QueueTest {
    @Test
    public void incorrectStore_getZero() {
        Store storeOne = new Store("Bava");
        Store storeTwo = new Store("Gym of Village");
        Store storeThree = new Store("Chickfila");
        Store storeFour = new Store("mxcnvb2e4");
        assertEquals(0, storeOne.queueTime());
        assertEquals(0, storeTwo.queueTime());
        assertEquals(0, storeThree.queueTime());
        assertEquals(0, storeFour.queueTime());
    }

    @Test
    public void correctStore() {
        Store storeOne = new Store("AmAzoN Locker");
        Store storeTwo = new Store("Cava");
        Store storeThree = new Store("village DINING HALL");
        Store storeFour = new Store("usc credit union");
        assertEquals(12, storeOne.queueTime());
        assertEquals(11, storeTwo.queueTime());
        assertEquals(15 , storeThree.queueTime());
        assertEquals(9, storeFour.queueTime());
    }
}
