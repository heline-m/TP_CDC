package home.projet_cdc;

import java.util.ArrayList;
import java.util.List;

public class PorteFake implements PorteInterface{

    public void setListeBadges(List<Badge> listeBadges) {
        this.listeBadges = listeBadges;
    }

    private List<Badge> listeBadges = new ArrayList<>();

    private boolean estBloque;

    public PorteFake(boolean estBloque){
        this.estBloque = estBloque;
    }

    public PorteFake(boolean estBloque, List<Badge> listeBadges){
        this.estBloque = estBloque;
        this.listeBadges = listeBadges;
    }

    public List<Badge> getListeBadges() {
        return listeBadges;
    }

    @Override
    public void ouvrir() {

    }

    @Override
    public void bloquer() {
        this.estBloque = true;
    }

    @Override
    public void debloquer() {
        this.estBloque = false;
    }

    @Override
    public boolean isEstBloque() {
        return this.estBloque;
    }

}
