package model;

// class for tile to be used in tileMap, tile class is a generic class
// representing the smallest components of the map image
// it has various shapes, colours, and sizes
// each tile can hold many assets

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Tile implements Writable {
    private int length;
    private String color;
    private String shape;
    private List<Asset> assets;

    public Tile(int length, String color, String shape, List<Asset> assets) {
        this.length = length;
        this.color  = color;
        this.shape = shape;
        this.assets = assets;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int l) {
        length = l;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String colour) {
        color = colour;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String s) {
        shape = s;
    }

    public void setAssets(List<Asset> loa) {
        assets = loa;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("length", length);
        json.put("color", color);
        json.put("shape", shape);
        json.put("assets", assetsToJson());
        return json;
    }

    private JSONArray assetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Asset a : assets) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }
}
