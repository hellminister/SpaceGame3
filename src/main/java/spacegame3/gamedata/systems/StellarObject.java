package spacegame3.gamedata.systems;

public abstract class StellarObject {

    protected String name;
    protected StarSystem inSystem;

    public StellarObject(String name) {
        this.name = name;
    }

    public boolean isLandable(){
        return false;
    }

    public String getName() {
        return name;
    }

    public StarSystem getInSystem() {
        return inSystem;
    }

    public void setInSystem(StarSystem inSystem) {
        this.inSystem = inSystem;
    }
}
