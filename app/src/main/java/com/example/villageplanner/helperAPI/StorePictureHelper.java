package com.example.villageplanner.helperAPI;
import android.content.res.Resources;
import com.example.villageplanner.R;

public class StorePictureHelper {
    private StorePictureHelper() {}

    public static int getPicture(String store) {
        switch (store) {
            case "Village Gym":
                return R.drawable.villagegym;
            case "Cava":
                return R.drawable.cava;
            case "Village Dining Hall":
                return R.drawable.dining;
            case "KOBUNGA":
                return R.drawable.kobunga;
            case "City Tacos":
                return R.drawable.citytacos;
            case "Ramen Kenjo":
                return R.drawable.ramen;
            case "Sunlife Organics":
                return R.drawable.sunlife;
            case "Credit Union":
                return R.drawable.credit_union;
            case "Insomnia Cookies":
                 return R.drawable.insomnia;
            case "Dulce":
                return R.drawable.dulce;
            case "Amazon Locker":
                return R.drawable.amazon_two;
            case "Trader Joe\'s":
                return R.drawable.trader_joes;
            case "Target":
                return R.drawable.target;
            case "Solé Bicycles":
                return R.drawable.sole;
            case "Honeybird":
                return R.drawable.honeybird;

            default:
                return R.drawable.defaultvillage;
        }
    }

    public static int getIndexFromValue(String location, String[] locations) {
        for(int i=0; i < locations.length; i++) {
            if(locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }
}
