package nossafirma.com.br.meuapp.model;

import java.io.Serializable;

/**
 * Created by Rodrigo on 26/08/2017.
 */

public class Region implements Serializable{
    private Integer id;
    private String initials;
    private String name;

    public Region() {
    }

    public Region(Integer id, String initials, String name) {
        this.id = id;
        this.initials = initials;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Para exibir corretamente os itens no combo.
    @Override
    public String toString() {
        return getName();
    }
}
