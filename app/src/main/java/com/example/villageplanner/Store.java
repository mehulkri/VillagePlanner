package com.example.villageplanner;


public class Store {
    private final String name;
    // openingTimes[day #] = hour at which the store opens
    // closingTimes[day #] = hour at which the store closes
    // e.g. openingTimes[1] = 8.25 means store opens 8:15am on Monday
    // latitude and longitude describe physical location on globe
    private final double openingTime, closingTime;
    private final double latitude, longitude;

    public Store(String id) {
        name = id;
        id = id.toLowerCase();
        switch (id) {
            case "amazon locker":
                openingTime=9;
                closingTime=21;
                latitude=34.02567088542457;
                longitude=-118.28542463756773;
                break;
            case "cava":
                openingTime=10.75;
                closingTime=22;
                latitude=34.025289107299386;
                longitude=-118.28452640000003;
                break;
            case "chinese street food":
                openingTime=11;
                closingTime=1;
                latitude=34.02459601060275;
                longitude=-118.2840322648177;
                break;
            case "city tacos":
                openingTime=11.5;
                closingTime=21;
                latitude=34.024190347879475;
                longitude=-118.28462527924925;
                break;
            case "dulce":
                openingTime=8;
                closingTime=21;
                latitude=34.025480820155984;
                longitude=-118.28554934922441;
                break;
            case "fedex":
                openingTime=9;
                closingTime=18;
                latitude=34.02528930492094;
                longitude=-118.28579213632132;
                break;
            case "fruit + candy":
                openingTime=10;
                closingTime=20;
                latitude=34.024379367825766;
                longitude=-118.284234490211;
                break;
            case "greenleaf":
                openingTime=11;
                closingTime=21;
                latitude=34.024755952558415;
                longitude=-118.28563799665905;
                break;
            case "honeybird":
                openingTime=11;
                closingTime=20;
                latitude=34.0248498921169;
                longitude=-118.28451427311494;
                break;
            case "insomnia cookies":
                openingTime=11;
                closingTime=1;
                latitude=34.02502554830927;
                longitude=-118.28534174715357;
                break;
            case "kaitlyn":
                openingTime=11;
                closingTime=19;
                latitude=34.02427926857537;
                longitude=-118.2848257743731;
                break;
            case "kobunga":
                openingTime=11;
                closingTime=20;
                latitude=34.0246266497395;
                longitude=-118.2856006265842;
                break;
            case "mac repair clinic":
                openingTime=11;
                closingTime=18;
                latitude=34.02454440716868;
                longitude=-118.28540953814475;
                break;
            case "ramen kenjo":
                openingTime=11;
                closingTime=22;
                latitude=34.0246127889896;
                longitude=-118.28556840567323;
                break;
            case "rock and reilly's":
                openingTime=12;
                closingTime=0;
                latitude=34.02424327268651;
                longitude=-118.28415962834046;
                break;
            case "sammiche shoppe":
                openingTime=11;
                closingTime=8;
                latitude=34.02484185936931;
                longitude=-118.28413279090594;
                break;
            case "solé bicycles":
                openingTime=10;
                closingTime=18;
                latitude=34.02428760488583;
                longitude=-118.28477145964057;
                break;
            case "starbucks":
                openingTime=8;
                closingTime=20;
                latitude=34.02482777087195;
                longitude=-118.28407846759262;
                break;
            case "stout burgers and beers":
                openingTime=12;
                closingTime=22;
                latitude=34.024790030138334;
                longitude=-118.28468387924917;
                break;
            case "sunlife organics":
                openingTime=8;
                closingTime=18;
                latitude=34.02453970085991;
                longitude=-118.28538403021024;
                break;
            case "target":
                openingTime=7;
                closingTime=22;
                latitude=34.025973206535454;
                longitude=-118.28419948121343;
                break;
            case "trader joe's":
                openingTime=8;
                closingTime=22;
                latitude=34.02608709546345;
                longitude=-118.28466450256279;
                break;
            case "usc credit union":
                openingTime=9;
                closingTime=18;
                latitude=34.025851640717576;
                longitude=-118.28522459148573;
                break;
            case "village dining hall":
                openingTime=7;
                closingTime=22;
                latitude=34.02567531349429;
                longitude=-118.28622865380453;
                break;
            case "village fitness center":
                openingTime=8;
                closingTime=23;
                latitude=34.02479510266673;
                longitude=-118.28598891350403;
                break;
            default:
                openingTime=0;
                closingTime=0;
                latitude=0;
                longitude=0;
                break;
        }
    }

    public String getName() {
        return name;
    }
    public double getOpeningTime() {
        return openingTime;
    }
    public double getClosingTime() {
        return closingTime;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    boolean isClosed(double time){
        if (closingTime > openingTime) {
            // want to ensure time is in interval (openingTime, closingTime)
            return (time < closingTime && time > openingTime);
        }
        else {
            // store closes after midnight
            // want to ensure time is in interval (openingTime, 24) OR (0, closingTime)
            return (time > openingTime || time < closingTime);
        }
    }

    double queueTime(double time){ // returned in minutes
        if (isClosed(time)) {
            return -1;
        }
        else{
            int upper = 20;
            int lower = 10;
            return (int) (Math.random() * (upper - lower)) + lower;
        }
    }

    double walkTime(){
        int upper = 30;
        int lower = 20;
        return (int) (Math.random() * (upper - lower)) + lower;
    }
}
