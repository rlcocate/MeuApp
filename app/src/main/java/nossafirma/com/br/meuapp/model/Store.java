package nossafirma.com.br.meuapp.model;

import java.io.Serializable;

public class Store implements Serializable {

    private Integer id;
    private String name;
    private Region region;
    private Beer beer;
    private LocalAddress localAddress;
    private Double beerValue;

    public Store() {
    }

    public Store(Integer id, String name,
                 Region region, Beer beer,
                 LocalAddress localAddress,
                 Double value) {
        this.setId(id);
        this.setName(name);
        this.setRegion(region);
        this.setBeer(beer);
        this.setLocalAddress(localAddress);
        this.setBeerValue(value);
    }

    public Integer getId() {
        return id;
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

    public LocalAddress getLocalAddress() {return localAddress;}

    public void setLocalAddress(LocalAddress localAddress) {
        this.localAddress = localAddress;
    }

    public Double getBeerValue() {
        return beerValue;
    }

    public void setBeerValue(Double value) {
        this.beerValue = value;
    }
}