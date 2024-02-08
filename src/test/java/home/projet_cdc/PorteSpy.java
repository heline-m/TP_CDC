package home.projet_cdc;

public class PorteSpy implements PorteInterface{
    private final PorteInterface decorateur;
    public PorteSpy(PorteInterface decorated){
        this.decorateur = decorated;
    }

    public PorteSpy(){
        this.decorateur = new PorteFake(false);
    }

    private boolean _ouvertureDemandee = false;

    public boolean isEstBloque() {
        return decorateur.isEstBloque();
    }

    @Override
    public void bloquer() { }

    @Override
    public void debloquer() {
        decorateur.debloquer();
    }

    public boolean verifierOuvertureDemandee() {
        return _ouvertureDemandee;
    }

    @Override
    public void ouvrir() {
        _ouvertureDemandee = true;
    }
}
