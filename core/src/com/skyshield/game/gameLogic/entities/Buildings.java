package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.buildings.*;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.CountryTerritory;

public class Buildings {

    public static Array<Barracks> barracks = new Array<>();
    public static Array<City> cities = new Array<>();
    public static Array<Dam> dams = new Array<>();
    public static Array<Factory> factories = new Array<>();
    public static Array<Hub1> hub1s = new Array<>();
    public static Array<Hub2> hub2s = new Array<>();
    public static Array<Hub3> hub3s = new Array<>();
    public static Array<SuperFactory> superFactories = new Array<>();
    public static Array<PowerStation> powerStations = new Array<>();


    public static void setDisabled() {
        Polygon territory = CountryTerritory.map;

        for(Hub1 hub1 : hub1s) {
            if(!territory.contains(hub1.getPos()[0], hub1.getPos()[1])) {
                hub1.setDisabled(true);
            }else{
                hub1.setDisabled(false);
            }
        }

        for(Hub2 hub2 : hub2s) {
            if(!territory.contains(hub2.getPos()[0], hub2.getPos()[1])) {
                hub2.setDisabled(true);
            }else{
                hub2.setDisabled(false);
            }
        }

        for(Hub3 hub3 : hub3s) {
            if(!territory.contains(hub3.getPos()[0], hub3.getPos()[1])) {
                hub3.setDisabled(true);
            }else{
                hub3.setDisabled(false);
            }
        }

        for(PowerStation powerStation : powerStations) {
            if(!territory.contains(powerStation.getPos()[0], powerStation.getPos()[1])) {
                powerStation.setDisabled(true);
            }else{
                powerStation.setDisabled(false);
            }
        }

        for(Dam dam : dams) {
            if(!territory.contains(dam.getPos()[0], dam.getPos()[1])) {
                dam.setDisabled(true);
            }else{
                dam.setDisabled(false);
            }
        }

        for(City city : cities) {
            if(!territory.contains(city.getPos()[0], city.getPos()[1])) {
                city.setDisabled(true);
            }else{
                city.setDisabled(false);
            }
        }

        for(Factory factory : factories) {
            if(!territory.contains(factory.getPos()[0], factory.getPos()[1])) {
                factory.setDisabled(true);
            }else{
                factory.setDisabled(false);
            }
        }

        for(Barracks barrack : barracks) {
            if(!territory.contains(barrack.getPos()[0], barrack.getPos()[1])) {
                barrack.setDisabled(true);
            }else{
                barrack.setDisabled(false);
            }
        }
    }
    public static void addBuildings() {
        addPowerStations();
        addFactories();
        addCities();
        addBarracks();
        addDams();
        addSuperFactories();
        addHub1();
        addHub2();
        addHub3();
    }

    public static void drawBuildings() {
        GameScreen.game.batch.begin();
        drawPowerStations();
        drawFactories();
        drawCities();
        drawBarracks();
        drawDams();
        drawSuperFactories();
        drawHub1();
        drawHub2();
        drawHub3();
        GameScreen.game.font.draw(GameScreen.game.batch, "Money: " + City.getTotalMoney(), 1120, 200);
//        GameScreen.game.font.draw(GameScreen.game.batch, "civil: " + City.totalPopulation, 1000, 50);
        GameScreen.game.font.draw(GameScreen.game.batch, "Soldiers: " + Barracks.getTotalTrainedSoldiers(), 1120, 300);
        GameScreen.game.font.draw(GameScreen.game.batch, "Weapons: " + Factory.getRocketCount(), 1120, 250);
//        GameScreen.game.font.draw(GameScreen.game.batch, "factory1: " + superFactories.get(0).weaponsProduced, 100, 200);
//        GameScreen.game.font.draw(GameScreen.game.batch, "factory2: " + superFactories.get(1).weaponsProduced, 100, 150);
//        GameScreen.game.font.draw(GameScreen.game.batch, "factory3: " + superFactories.get(2).weaponsProduced, 100, 100);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level11: " + hub1s.get(0).weapons, 300, 200);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level12: " + hub1s.get(1).weapons, 300, 150);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level13: " + hub1s.get(2).weapons, 300, 100);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level21: " + hub2s.get(0).weapons, 500, 200);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level22: " + hub2s.get(1).weapons, 500, 150);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level23: " + hub2s.get(2).weapons, 500, 100);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level31: " + hub3s.get(0).weapons, 700, 200);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level32: " + hub3s.get(1).weapons, 700, 150);
//        GameScreen.game.font.draw(GameScreen.game.batch, "level33: " + hub3s.get(2).weapons, 700, 100);
        GameScreen.game.batch.end();
    }

    private static void drawPowerStations() {
        for(PowerStation station : powerStations) {
            GameScreen.game.batch.draw(station.getTexture(),
                    station.getPos()[0],
                    station.getPos()[1],
                    station.getHitbox().width, station.getHitbox().height);
        }
    }

    private static void drawFactories() {
        for(Factory factory : factories) {
            GameScreen.game.batch.draw(factory.getTexture(),
                    factory.getPos()[0],
                    factory.getPos()[1],
                    factory.getHitbox().width, factory.getHitbox().height);
            factory.update(1);
        }
    }

    private static void drawCities() {
        for(City city : cities) {
            GameScreen.game.batch.draw(city.getTexture(),
                    city.getPos()[0],
                    city.getPos()[1],
                    city.getHitbox().width, city.getHitbox().height);
            city.update(1);
        }
    }

    private static void drawBarracks() {
        for(Barracks barrack : barracks) {
            GameScreen.game.batch.draw(barrack.getTexture(),
                    barrack.getPos()[0],
                    barrack.getPos()[1],
                    barrack.getHitbox().width, barrack.getHitbox().height);
            barrack.update(1);
        }
    }

    private static void drawDams() {
        for(Dam dam : dams) {
            GameScreen.game.batch.draw(dam.getTexture(),
                    dam.getPos()[0],
                    dam.getPos()[1],
                    dam.getHitbox().width, dam.getHitbox().height);
        }
    }

    private static void drawSuperFactories() {
        for(SuperFactory superFactory : superFactories) {
            GameScreen.game.batch.draw(superFactory.getTexture(),
                    superFactory.getPos()[0],
                    superFactory.getPos()[1],
                    superFactory.getHitbox().width, superFactory.getHitbox().height);
            superFactory.update(1);
        }
    }

    private static void drawHub1() {
        for(Hub1 hub1 : hub1s) {
            GameScreen.game.batch.draw(hub1.getTexture(),
                    hub1.getPos()[0],
                    hub1.getPos()[1],
                    hub1.getHitbox().width, hub1.getHitbox().height);
            hub1.update(1);
        }
    }

    private static void drawHub2() {
        for(Hub2 hub2 : hub2s) {
            GameScreen.game.batch.draw(hub2.getTexture(),
                    hub2.getPos()[0],
                    hub2.getPos()[1],
                    hub2.getHitbox().width, hub2.getHitbox().height);
            hub2.update(1);
        }
    }

    private static void drawHub3() {
        for(Hub3 hub3 : hub3s) {
            GameScreen.game.batch.draw(hub3.getTexture(),
                    hub3.getPos()[0],
                    hub3.getPos()[1],
                    hub3.getHitbox().width, hub3.getHitbox().height);
            hub3.update(1);
        }
    }
    private static void addBarracks() {
        barracks.add( new Barracks(new float[]{200, 360}, cities.get(0), powerStations.get(0),1500,1000));
        barracks.add( new Barracks(new float[]{330, 490}, cities.get(1), powerStations.get(1),1500,1000));
        barracks.add( new Barracks(new float[]{310, 360}, cities.get(2), powerStations.get(2),1500,1000));
        barracks.add( new Barracks(new float[]{570, 280}, cities.get(3), powerStations.get(3),1500,1000));
        barracks.add( new Barracks(new float[]{395, 230}, cities.get(4), powerStations.get(4),1500,1000));
        barracks.add( new Barracks(new float[]{570,540}, cities.get(5), powerStations.get(5),1500,1000));
        barracks.add( new Barracks(new float[]{520,460}, cities.get(6), powerStations.get(6),1500,1000));
        barracks.add( new Barracks(new float[]{730,440}, cities.get(7), powerStations.get(7),1500,1000));
        barracks.add( new Barracks(new float[]{760,590}, cities.get(8), powerStations.get(8),1500,1000));
        barracks.add( new Barracks(new float[]{770,500}, cities.get(9), powerStations.get(9),1500,1000));
        barracks.add( new Barracks(new float[]{930,400}, cities.get(10), powerStations.get(10),1500,1000));
        barracks.add( new Barracks(new float[]{1085, 200}, cities.get(11), powerStations.get(11),1500,1000));
        barracks.add( new Barracks(new float[]{655, 185}, cities.get(12), powerStations.get(12),1500,1000));
    }

    private static void addPowerStations() {
        powerStations.add( new PowerStation(new float[]{200, 270}, 1000, 1));
        powerStations.add( new PowerStation(new float[]{230, 470}, 1000, 2));
        powerStations.add( new PowerStation(new float[]{400, 360}, 1000, 3));
        powerStations.add( new PowerStation(new float[]{500, 270}, 1000, 4));
        powerStations.add( new PowerStation(new float[]{460, 150}, 1000, 5));
        powerStations.add( new PowerStation(new float[]{400, 490}, 1000, 6));
        powerStations.add( new PowerStation(new float[]{630, 500}, 1000, 7));
        powerStations.add( new PowerStation(new float[]{800, 330}, 1000, 8));
        powerStations.add( new PowerStation(new float[]{650, 620}, 1000, 9));
        powerStations.add( new PowerStation(new float[]{870, 530}, 1000, 10));
        powerStations.add( new PowerStation(new float[]{930, 340}, 1000, 11));
        powerStations.add( new PowerStation(new float[]{1050, 260}, 1000, 12));
        powerStations.add( new PowerStation(new float[]{700, 160}, 1000, 13));
    }

    private static void addFactories() {
        factories.add( new Factory(new float[]{150,250}, powerStations.get(0),2000,1));
        factories.add( new Factory(new float[]{260, 430}, powerStations.get(1),2000,2));
        factories.add( new Factory(new float[]{410, 400}, powerStations.get(2),2000,3));
        factories.add( new Factory(new float[]{400, 300}, powerStations.get(3),2000,4));
        factories.add( new Factory(new float[]{510, 190}, powerStations.get(4),2000,5));
        factories.add( new Factory(new float[]{450, 530}, powerStations.get(5),2000,6));
        factories.add( new Factory(new float[]{480, 420}, powerStations.get(6),2000,7));
        factories.add( new Factory(new float[]{855, 305}, powerStations.get(7),2000,8));
        factories.add( new Factory(new float[]{610, 620}, powerStations.get(8),2000,9));
        factories.add( new Factory(new float[]{860, 450}, powerStations.get(9),2000,10));
        factories.add( new Factory(new float[]{1070, 360}, powerStations.get(10),2000,11));
        factories.add( new Factory(new float[]{1080, 310}, powerStations.get(11),2000,12));
        factories.add( new Factory(new float[]{660, 160}, powerStations.get(12),2000,13));
    }

    private static void addCities() {
        cities.add( new City(new float[]{240,290},powerStations.get(0),10,10,1000));
        cities.add( new City(new float[]{280,550},powerStations.get(1),10,10,1000));
        cities.add( new City(new float[]{350,350},powerStations.get(2),10,10,10000));
        cities.add( new City(new float[]{450,250},powerStations.get(3),10,10,1000));
        cities.add( new City(new float[]{420,120},powerStations.get(4),10,10,1000));
        cities.add( new City(new float[]{510,540},powerStations.get(5),10,10,1000));
        cities.add( new City(new float[]{570,450},powerStations.get(6),10,10,1000));
        cities.add( new City(new float[]{740,300},powerStations.get(7),10,10,1000));
        cities.add( new City(new float[]{720,620},powerStations.get(8),10,10,1000));
        cities.add( new City(new float[]{830,500},powerStations.get(9),10,10,1000));
        cities.add( new City(new float[]{1000,350},powerStations.get(10),10,10,1000));
        cities.add( new City(new float[]{1010, 220},powerStations.get(11),10,10,1000));
        cities.add( new City(new float[]{740,160},powerStations.get(12),10,10,1000));
    }

    private static void addDams() {
        dams.add( new Dam(new float[]{455, 185},15000));
        dams.add( new Dam(new float[]{610, 450},15000));
        dams.add( new Dam(new float[]{500, 310},15000));
        dams.add( new Dam(new float[]{290, 400},15000));
    }

    private static void addSuperFactories() {
        superFactories.add( new SuperFactory(new float[]{50,580}));
        superFactories.add( new SuperFactory(new float[]{60,370}));
        superFactories.add( new SuperFactory(new float[]{200,120}));
    }

    private static void addHub1() {
        hub1s.add(new Hub1(new float[]{220,540},superFactories.get(0),powerStations.get(1),1500,10));
        hub1s.add(new Hub1(new float[]{150,280},superFactories.get(1),powerStations.get(0),1500,10));
        hub1s.add(new Hub1(new float[]{380,160},superFactories.get(2),powerStations.get(4),1500,10));
    }

    private static void addHub2() {
        hub2s.add( new Hub2(new float[]{510,580},hub1s.get(0),powerStations.get(5),1500,50));
        hub2s.add( new Hub2(new float[]{350,380},hub1s.get(1),powerStations.get(2),1500,50));
        hub2s.add( new Hub2(new float[]{560,330},hub1s.get(2),powerStations.get(3),1500,50));
    }

    private static void addHub3() {
        hub3s.add( new Hub3(new float[]{700,580},hub2s.get(0),powerStations.get(8),1500,150));
        hub3s.add( new Hub3(new float[]{680,480},hub2s.get(1),powerStations.get(6),1500,150));
        hub3s.add( new Hub3(new float[]{750,350},hub2s.get(2),powerStations.get(7),1500,150));
    }
}
