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
        // ToDo: Pull from a JSON/Firebase
        switch (id) {
            case "amazon locker":
                openingTime=9;
                closingTime=21;
                latitude=34.025808154311036;
                longitude=-118.28539312174385;
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
                latitude=34.024665246863506;
                longitude=-118.28394359195589;
                break;
            case "city tacos":
                openingTime=11.5;
                closingTime=21;
                latitude=34.024304277502345;
                longitude=-118.28465076037423;
                break;
            case "credit union":
                openingTime=9;
                closingTime=18;
                latitude=34.025983134719304;
                longitude=-118.28526646222342;
                break;
            case "fedex":
                openingTime=9;
                closingTime=18;
                latitude=34.02603615644265;
                longitude=-118.2860711644111;
                break;
            case "fruit + candy":
                openingTime=10;
                closingTime=20;
                latitude=34.02458338967535;
                longitude=-118.28430066037421;
                break;
            case "greenleaf":
                openingTime=11;
                closingTime=21;
                latitude=34.02481796607293;
                longitude=-118.28573927406285;
                break;
            case "insomnia":
                openingTime=11;
                closingTime=1;
                latitude=34.02521769609158;
                longitude=-118.28535583153861;
                break;
            case "kaitlyn":
                openingTime=11;
                closingTime=19;
                latitude=34.02983041258225;
                longitude=-118.2829445172315;
                break;
            case "kobunga":
                openingTime=11;
                closingTime=20;
                latitude=34.02482058871474;
                longitude=-118.28551882174382;
                break;
            case "mac repair clinic":
                openingTime=11;
                closingTime=18;
                latitude=34.02461220892162;
                longitude=-118.28532907201817;
                break;
            case "ramen kenjo":
                openingTime=11;
                closingTime=22;
                latitude=34.024721160531946;
                longitude=-118.28552280825059;
                break;
            case "solé bicycles":
                openingTime=10;
                closingTime=18;
                latitude=34.02885324684136;
                longitude=-118.28437379634839;
                break;
            case "starbucks":
                openingTime=8;
                closingTime=20;
                latitude=34.02499276658639;
                longitude=-118.2840845226406;
                break;
            case "sunlife organics":
                openingTime=8;
                closingTime=18;
                latitude=34.024612998127054;
                longitude=-118.28534649935264;
                break;
            case "target":
                openingTime=7;
                closingTime=22;
                latitude=34.026132234437995;
                longitude=-118.2841647161962;
                break;
            case "trader joe's":
                openingTime=8;
                closingTime=22;
                latitude=34.026316060943586;
                longitude=-118.28452502784029;
                break;
            case "fitness center":
                openingTime=8;
                closingTime=23;
                latitude=34.02489726921062;
                longitude=-118.28591982174385;
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
