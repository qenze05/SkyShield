package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.buildings.*;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.CountryTerritory;
import com.skyshield.game.utils.ItemsList;

import java.util.Map;

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
    public static Array<Table> hpBars = new Array<>();
    public static final Texture hpBarTexture = new Texture(Gdx.files.internal("hp-bar/bg.png"));

    public static boolean allStationsDestroyed() {
        for(PowerStation station : powerStations) {
            if(!station.isDisabled() && station.getHealth() > 0) return false;
        }
        return true;
    }
    public static boolean isDestroyed(String building) {
        String name = building.split("-")[0];
        int number = Integer.parseInt(building.split("-")[1]);

        switch (name.toLowerCase()) {
            case "barrack" -> {
                return Buildings.barracks.get(number).getHealth() <= 0;
            }
            case "city" -> {
                return Buildings.cities.get(number).getHealth() <= 0;
            }
            case "dam" -> {
                return Buildings.dams.get(number).getHealth() <= 0;
            }
            case "factory" -> {
                return Buildings.factories.get(number).getHealth() <= 0;
            }
            case "hub1" -> {
                return Buildings.hub1s.get(number).getHealth() <= 0;
            }
            case "hub2" -> {
                return Buildings.hub2s.get(number).getHealth() <= 0;
            }
            case "hub3" -> {
                return Buildings.hub3s.get(number).getHealth() <= 0;
            }
            case "powerstation" -> {
                return Buildings.powerStations.get(number).getHealth() <= 0;
            }
        }
        return false;
    }
    public static void addHpBar(Rectangle hitbox) {

        String name = "";
        int number = 0;
        for (Map.Entry<String, Rectangle> entry : ItemsList.buildings.entrySet()) {
            if(entry.getValue().equals(hitbox)) {
                name = entry.getKey().toLowerCase().split("-")[0];
                number = Integer.parseInt(entry.getKey().toLowerCase().split("-")[1]);
                break;
            }
        }

        float percentage = 0;

        switch (name) {
            case "barrack" -> percentage = (float) Buildings.barracks.get(number).getHealth() / Buildings.barracks.get(number).getMaxhealth();
            case "city" -> percentage = (float) Buildings.cities.get(number).getHealth() / Buildings.cities.get(number).getMaxhealth();
            case "dam" -> percentage = (float) Buildings.dams.get(number).getHealth() / Buildings.dams.get(number).getMaxhealth();
            case "factory" -> percentage = (float) Buildings.factories.get(number).getHealth() / Buildings.factories.get(number).getMaxhealth();
            case "hub1" -> percentage = (float) Buildings.hub1s.get(number).getHealth() / Buildings.hub1s.get(number).getMaxhealth();
            case "hub2" -> percentage = (float) Buildings.hub2s.get(number).getHealth() / Buildings.hub2s.get(number).getMaxhealth();
            case "hub3" -> percentage = (float) Buildings.hub3s.get(number).getHealth() / Buildings.hub3s.get(number).getMaxhealth();
            case "powerstation" -> percentage = (float) Buildings.powerStations.get(number).getHealth() / Buildings.powerStations.get(number).getMaxhealth();
        }

        addHpBarTable(hitbox, percentage);
    }

    public static void addHpBarTable(Rectangle hitbox, float percentage) {

        Table table = new Table();
        table.setBackground(new Image(hpBarTexture).getDrawable());

        table.setBounds(hitbox.x + hitbox.width/2 - 25,
                hitbox.y - 10,
                50, 8);

        float red, green;
        if(percentage >= 0.5f) {
            red = 1 - 2 * percentage;
            green = 1;
        }else {
            green = 2 * percentage;
            red = 1;
        }
        Pixmap pixmap = new Pixmap(46, 6, Pixmap.Format.RGBA8888);
        pixmap.setColor(red, green, 0, 1);
        pixmap.fillRectangle(0,
                1,
                (int) (46 * percentage), 6);
        Image img = new Image(new Texture(pixmap));
        pixmap.dispose();
        if(percentage > 0) table.add(img);

        for(int i = 0; i < hpBars.size; i++) {
            if(hpBars.get(i).getX() == table.getX() && hpBars.get(i).getY() == table.getY()) {
               hpBars.removeIndex(i);
               break;
            }
        }

        hpBars.add(table);
    }

    public static void removeHpBar(Rectangle rect) {
        for(int i = 0; i < hpBars.size; i++) {
            if(hpBars.get(i).getX() == rect.getX() + rect.getWidth()/2 - 25 && hpBars.get(i).getY() == rect.getY() - 10) {
                hpBars.removeIndex(i);
                break;
            }
        }
    }

    /**
     * @param hitbox - target hitbox
     * @param hp - hp modifier
     * @param elektra - elektra ability
     */
    public static void changeHp(Rectangle hitbox, int hp, boolean elektra) {
        String name = "";
        int number = 0;
        for (Map.Entry<String, Rectangle> entry : ItemsList.buildings.entrySet()) {
            if(entry.getValue().equals(hitbox)) {
                name = entry.getKey().toLowerCase().split("-")[0];
                number = Integer.parseInt(entry.getKey().toLowerCase().split("-")[1]);
                break;
            }
        }

        switch (name) {
            case "barrack" -> Buildings.barracks.get(number).setHealth(hp);
            case "city" -> Buildings.cities.get(number).setHealth(hp);
            case "dam" -> Buildings.dams.get(number).setHealth(hp);
            case "factory" -> Buildings.factories.get(number).setHealth(hp);
            case "hub1" -> Buildings.hub1s.get(number).setHealth(hp);
            case "hub2" -> Buildings.hub2s.get(number).setHealth(hp);
            case "hub3" -> Buildings.hub3s.get(number).setHealth(hp);
            case "powerstation" -> {
                if(elektra) {
                    for(int i = 0; i < powerStations.size; i++) {
                        if(i==number) Buildings.powerStations.get(i).setHealth(hp);
                        else Buildings.powerStations.get(i).setHealth(-5);
                    }
                }

            }
        }
    }
    public static void setDisabled() {
        Polygon territory = CountryTerritory.map;

        for(Hub1 hub1 : hub1s) {
            hub1.setDisabled(!territory.contains(hub1.getPos()[0] + 20, hub1.getPos()[1] + 20));
        }

        for(Hub2 hub2 : hub2s) {
            hub2.setDisabled(!territory.contains(hub2.getPos()[0] + 20, hub2.getPos()[1] + 20));
        }

        for(Hub3 hub3 : hub3s) {
            hub3.setDisabled(!territory.contains(hub3.getPos()[0] + 20, hub3.getPos()[1] + 20));
        }

        for(PowerStation powerStation : powerStations) {
            powerStation.setDisabled(!territory.contains(powerStation.getPos()[0] + 20, powerStation.getPos()[1] + 20));
        }

        for(Dam dam : dams) {
            dam.setDisabled(!territory.contains(dam.getPos()[0] + 20, dam.getPos()[1] + 20));
        }

        for(City city : cities) {
            city.setDisabled(!territory.contains(city.getPos()[0] + 20, city.getPos()[1] + 20));
        }

        for(Factory factory : factories) {
            factory.setDisabled(!territory.contains(factory.getPos()[0] + 20, factory.getPos()[1] + 20));
        }

        for(Barracks barrack : barracks) {
            barrack.setDisabled(!territory.contains(barrack.getPos()[0] + 20, barrack.getPos()[1] + 20));
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
        GameScreen.game.font.draw(GameScreen.game.batch, "Soldiers: " + Barracks.getTotalTrainedSoldiers(), 1120, 300);
        GameScreen.game.font.draw(GameScreen.game.batch, "Weapons: " + Factory.getRocketCount(), 1120, 250);
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
        barracks.add( new Barracks(new float[]{200, 360}, cities.get(0), powerStations.get(0),200));
        barracks.add( new Barracks(new float[]{330, 490}, cities.get(1), powerStations.get(1),250));
        barracks.add( new Barracks(new float[]{310, 360}, cities.get(2), powerStations.get(2),500));
        barracks.add( new Barracks(new float[]{570, 280}, cities.get(3), powerStations.get(3),250));
        barracks.add( new Barracks(new float[]{395, 230}, cities.get(4), powerStations.get(4),250));
        barracks.add( new Barracks(new float[]{570,540}, cities.get(5), powerStations.get(5),150));
        barracks.add( new Barracks(new float[]{520,460}, cities.get(6), powerStations.get(6),375));
        barracks.add( new Barracks(new float[]{730,440}, cities.get(7), powerStations.get(7),375));
        barracks.add( new Barracks(new float[]{760,590}, cities.get(8), powerStations.get(8),200));
        barracks.add( new Barracks(new float[]{770,500}, cities.get(9), powerStations.get(9),375));
        barracks.add( new Barracks(new float[]{930,400}, cities.get(10), powerStations.get(10),250));
        barracks.add( new Barracks(new float[]{1080, 210}, cities.get(11), powerStations.get(11),150));
        barracks.add( new Barracks(new float[]{655, 185}, cities.get(12), powerStations.get(12),150));
    }
    private static void addPowerStations() {
        powerStations.add( new PowerStation(new float[]{240,310}, 400));
        powerStations.add( new PowerStation(new float[]{210, 470}, 500));
        powerStations.add( new PowerStation(new float[]{405, 335}, 1000));
        powerStations.add( new PowerStation(new float[]{500, 270}, 500));
        powerStations.add( new PowerStation(new float[]{500, 120}, 500));
        powerStations.add( new PowerStation(new float[]{400, 490}, 300));
        powerStations.add( new PowerStation(new float[]{630, 500}, 750));
        powerStations.add( new PowerStation(new float[]{830, 350}, 750));
        powerStations.add( new PowerStation(new float[]{720,620}, 400));
        powerStations.add( new PowerStation(new float[]{820,530}, 750));
        powerStations.add( new PowerStation(new float[]{930, 340}, 500));
        powerStations.add( new PowerStation(new float[]{1050, 260}, 300));
        powerStations.add( new PowerStation(new float[]{740,160}, 300));
    }

    private static void addFactories() {
        factories.add( new Factory(new float[]{150,250}, powerStations.get(0),200));
        factories.add( new Factory(new float[]{250, 420}, powerStations.get(1),250));
        factories.add( new Factory(new float[]{405, 415}, powerStations.get(2),500));
        factories.add( new Factory(new float[]{400, 300}, powerStations.get(3),250));
        factories.add( new Factory(new float[]{510, 190}, powerStations.get(4),250));
        factories.add( new Factory(new float[]{450, 530}, powerStations.get(5),150));
        factories.add( new Factory(new float[]{480, 420}, powerStations.get(6),375));
        factories.add( new Factory(new float[]{855, 305}, powerStations.get(7),375));
        factories.add( new Factory(new float[]{610, 620}, powerStations.get(8),200));
        factories.add( new Factory(new float[]{860, 450}, powerStations.get(9),375));
        factories.add( new Factory(new float[]{1070, 360}, powerStations.get(10),250));
        factories.add( new Factory(new float[]{1080, 310}, powerStations.get(11),150));
        factories.add( new Factory(new float[]{660, 160}, powerStations.get(12),150));
    }
    private static void addCities() {
        cities.add( new City(new float[]{200, 250},powerStations.get(0),400));
        cities.add( new City(new float[]{250,450},powerStations.get(1),500));
        cities.add( new City(new float[]{345,330},powerStations.get(2),1000));
        cities.add( new City(new float[]{450,215},powerStations.get(3),500));
        cities.add( new City(new float[]{420,100},powerStations.get(4),500));
        cities.add( new City(new float[]{480,510},powerStations.get(5),300));
        cities.add( new City(new float[]{555,430},powerStations.get(6),750));
        cities.add( new City(new float[]{780,265},powerStations.get(7),750));
        cities.add( new City(new float[]{660, 585},powerStations.get(8),400));
        cities.add( new City(new float[]{870, 530},powerStations.get(9),750));
        cities.add( new City(new float[]{980,350},powerStations.get(10),500));
        cities.add( new City(new float[]{1030, 190},powerStations.get(11),300));
        cities.add( new City(new float[]{690, 140},powerStations.get(12),300));
    }

    private static void addDams() {
        dams.add( new Dam(new float[]{455, 185},15000));
        dams.add( new Dam(new float[]{610, 440},15000));
        dams.add( new Dam(new float[]{500, 310},15000));
        dams.add( new Dam(new float[]{275, 400},15000));
    }

    private static void addSuperFactories() {
        superFactories.add( new SuperFactory(new float[]{50,580}));
        superFactories.add( new SuperFactory(new float[]{60,370}));
        superFactories.add( new SuperFactory(new float[]{145,70}));
    }

    private static void addHub1() {
        hub1s.add(new Hub1(new float[]{220,540},superFactories.get(0),powerStations.get(1),500,120));
        hub1s.add(new Hub1(new float[]{150,280},superFactories.get(1),powerStations.get(0),500,120));
        hub1s.add(new Hub1(new float[]{380,160},superFactories.get(2),powerStations.get(4),500,120));
    }

    private static void addHub2() {
        hub2s.add( new Hub2(new float[]{510,580},hub1s.get(0),powerStations.get(5),500,120));
        hub2s.add( new Hub2(new float[]{350,380},hub1s.get(1),powerStations.get(2),500,120));
        hub2s.add( new Hub2(new float[]{560,330},hub1s.get(2),powerStations.get(3),500,120));
    }

    private static void addHub3() {
        hub3s.add( new Hub3(new float[]{700,580},hub2s.get(0),powerStations.get(8),500,120));
        hub3s.add( new Hub3(new float[]{680,480},hub2s.get(1),powerStations.get(6),500,120));
        hub3s.add( new Hub3(new float[]{750,350},hub2s.get(2),powerStations.get(7),500,120));
    }
}
