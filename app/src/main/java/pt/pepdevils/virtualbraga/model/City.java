package pt.pepdevils.virtualbraga.model;

import java.util.ArrayList;

/**
 * Created by Pedro Fonseca on 10/10/2017.
 */

public class City {

    private String name;
    private ArrayList<ARPoint> points;
    private int drawBack;

    public City(String name, ArrayList<ARPoint> points) {
        this.name = name;
        this.points = points;
    }

    public City(String name, ArrayList<ARPoint> points, int drawBack) {
        this.name = name;
        this.points = points;
        this.drawBack = drawBack;
    }

    public int getDrawBack() {
        return drawBack;
    }

    public void setDrawBack(int drawBack) {
        this.drawBack = drawBack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ARPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<ARPoint> points) {
        this.points = points;
    }
}
