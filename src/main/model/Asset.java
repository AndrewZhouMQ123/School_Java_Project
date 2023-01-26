package model;

import org.json.JSONObject;
import persistence.Writable;

// The asset class is a generic class containing its type, quantity and money-value
// it is parent class for in game items, such as trees, saplings and lumber
public class Asset implements Writable {
    private String type;
    private int assetSize;
    private int assetValue;


    public Asset(String type, int assetSize, int assetValue) {
        this.type = type;
        this.assetSize = assetSize;
        this.assetValue = assetValue;
    }

    public void setType(String t) {
        type = t;
    }

    public String getType() {
        return type;
    }

    public int getAssetSize() {
        return assetSize;
    }

    public int getAssetValue() {
        return assetValue;
    }

    public void setAssetSize(int size) {
        assetSize = size;
    }

    public void setAssetValue(int val) {
        assetValue = val;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("assetSize", assetSize);
        json.put("assetValue", assetValue);
        return json;
    }
}
