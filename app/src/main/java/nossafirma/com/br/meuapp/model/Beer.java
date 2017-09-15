package nossafirma.com.br.meuapp.model;

import java.io.Serializable;

/**
 * Created by Rodrigo on 26/08/2017.
 */

public class Beer implements Serializable{
    private Integer id;
    private String name;

    public Beer() {
    }

    public Beer(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    // Para exibir corretamente os itens no combo.
    @Override
    public String toString() {
        return getName();
    }
}
