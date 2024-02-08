package home.projet_cdc;

public class PorteBuilder {
    private boolean _estBloquee = false;
    public static PorteSpy parDefaut(){
        return new PorteBuilder().Build();
    }
    public PorteSpy Build(){
        var fake = new PorteFake(_estBloquee);
        return new PorteSpy(fake);
    }

    public PorteBuilder Bloquee(){
        _estBloquee = true;
        return this;
    }

}
