package home.projet_cdc;

//LecteurFake est une classe qui doit implementer une interface
public class LecteurFake implements LecteurInterface {

    private boolean _aDetecteBadge = false;

    private PorteInterface[] _portes;

    public void simulerDetectionBadge() {
        _aDetecteBadge = true;
    }

    @Override
    public boolean aDetecteBadge() {
        return _aDetecteBadge;
    }

    public LecteurFake(PorteInterface... porteLiee){
        this._portes = porteLiee;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }

}
