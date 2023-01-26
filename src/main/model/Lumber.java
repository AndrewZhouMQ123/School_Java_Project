package model;

// the lumber class corresponds to in game item lumber. in the game you plant saplings, chop down trees
// and collect lumber from the trees you chopped. the asset value of lumber always should be 20
// the assetSize is determined from the value of forest destroyed
// the string type is lumber

public class Lumber extends Asset {
    private String type;
    private int assetSize;
    private int assetValue;

    // REQUIRES: type, quantity, value
    // MODIFIES: type, assetSize, assetValue
    // EFFECTS: constructor for Lumber class
    public Lumber(String type, int assetSize, int assetValue) {
        super("lumber", assetSize, assetValue);
        this.assetValue = assetValue;
        this.type = type;
        this.assetSize = assetSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getAssetSize() {
        return assetSize;
    }

    @Override
    public void setAssetSize(int assetSize) {
        this.assetSize = assetSize;
    }

    @Override
    public int getAssetValue() {
        return assetValue;
    }

    @Override
    public void setAssetValue(int assetValue) {
        this.assetValue = assetValue;
    }
}
