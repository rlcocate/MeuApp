package nossafirma.com.br.meuapp.model;

public class Store {

    private Integer id;
    private String name;
    private Region region;
    private Beer beer;
    private Double beerValue;

    public Store(){}

    public Store(Integer id, String name,
                 Region region, Beer beer,
                 Double value){
        this.setId(id);
        this.setName(name);
        this.setRegion(region);
        this.setBeer(beer);
        this.setBeerValue(value);
    }

    public Integer getId() {
        return id == null ? 0 : id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Double getBeerValue() {
        return beerValue;
    }

    public void setBeerValue(Double value) {
        this.beerValue = value;
    }
}