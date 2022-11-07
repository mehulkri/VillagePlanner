package com.example.villageplanner.ReminderLogic;

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
            default:
                return R.drawable.defaultvillage;
        }
    }
}
