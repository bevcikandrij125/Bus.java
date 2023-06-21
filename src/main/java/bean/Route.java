package bean;

public class Route {
    private int id;
    private String startCity;
    private String targetCity;
    private int distance;

    public Route() {
        super();
    }

    public Route(int id, String startCity, String targetCity, int distance) {
        super();
        this.id = id;
        this.startCity = startCity;
        this.targetCity = targetCity;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}