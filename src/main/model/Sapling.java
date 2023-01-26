package model;

// A sapling that can grow into a tree once planted in tile

public class Sapling extends Asset {
    private String type;
    private int assetSize;
    private int assetValue;

    // REQUIRES: type, quantity, value
    // MODIFIES: type, assetSize, assetValue
    // EFFECTS: constructor for Sapling class
    public Sapling(String type, int assetSize, int assetValue) {
        super("sapling", assetSize, assetValue);
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
