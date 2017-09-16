package nossafirma.com.br.meuapp.model;

import java.io.Serializable;

/**
 * Created by Rodrigo on 11/09/2017.
 */

public class LocalAddress implements Serializable {

    private Integer Id;
    private String streetName;
    private String complement; // Número e complementos
    private Double latitude;
    private Double longitude;
    private Long storeId;

    public LocalAddress(Integer id, String streetName, String complement, Double latitude, Double longitude, Long storeId) {
        Id = id;
        this.streetName = streetName;
        this.complement = complement;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeId = storeId;
    }

    public LocalAddress() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
