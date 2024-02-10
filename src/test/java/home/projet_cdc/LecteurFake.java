package home.projet_cdc;

//LecteurFake est une classe qui doit implementer une interface
public class LecteurFake implements LecteurInterface {

    private boolean _aDetecteBadge = false;
    private Integer _aDetecteBadgeId = null;
    private PorteInterface[] _portes;

    public void simulerDetectionBadge(Badge badge) {
        _aDetecteBadge = !badge.isEstBloque() ;
        _aDetecteBadgeId = -1;
    }

    @Override
    public boolean aDetecteBadge() {
        return _aDetecteBadge;
    }

    @Override
    public Integer aDetecteBadgeId() {
        return _aDetecteBadgeId;
    }

    public LecteurFake(PorteInterface... porteLiee){
        this._portes = porteLiee;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }

}
