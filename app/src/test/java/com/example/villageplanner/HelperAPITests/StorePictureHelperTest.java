package com.example.villageplanner.HelperAPITests;

import static com.example.villageplanner.helperAPI.StorePictureHelper.getIndexFromValue;
import static com.example.villageplanner.helperAPI.StorePictureHelper.getPicture;

import com.example.villageplanner.R;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StorePictureHelperTest {
    @Test
    public void getPictureTest() {
        String[] stores = {"Village Gym","Cava", "Village Dining Hall", "KOBUNGA",
                "City Tacos", "Ramen Kenjo", "Sunlife Organics", "Credit Union", "Insomnia Cookies",
                "Dulce", "Amazon Locker", "Trader Joe\'s", "Target", "Solé Bicycles", "Honeybird", "Hkjkljksd"};
        int[] vals = {R.drawable.villagegym, R.drawable.cava, R.drawable.dining,
                R.drawable.kobunga, R.drawable.citytacos, R.drawable.ramen, R.drawable.sunlife, R.drawable.credit_union,
                R.drawable.insomnia, R.drawable.dulce, R.drawable.amazon_two,
                R.drawable.trader_joes, R.drawable.target, R.drawable.sole, R.drawable.honeybird, R.drawable.defaultvillage,
        };
        for(int i=0; i < stores.length; i++) {
            assertEquals(stores[i],vals[i], getPicture(stores[i]));
        }
    }

    @Test
    public void testGetIndexFromValue() {
        String[] locations = {"One", "Two", "Three"};
        assertEquals(1, getIndexFromValue("Two", locations));
        assertEquals(-1, getIndexFromValue("Four", locations));
    }
}