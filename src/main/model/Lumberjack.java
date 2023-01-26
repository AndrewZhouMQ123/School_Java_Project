package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Lumberjack implements Writable {
    private int xcolPos;
    private int yrowPos;
    private String name;
    private int money;
    private LinkedList<Asset> inventory;

    // REQUIRES: money, name, assets
    // MODIFIES: money, name, inventory
    // EFFECTS: constructor for Lumberjack class
    public Lumberjack(int money, String name, LinkedList<Asset> assets) {
        this.inventory = assets;
        this.money = money;
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInventory(LinkedList<Asset> assets) {
        inventory = assets;
    }

    public LinkedList<Asset> getInventory() {
        return inventory;
    }

    public void printInventory() {
        for (Asset item : inventory) {
            int val = item.getAssetValue();
            int size = item.getAssetSize();
            String type = item.getType();
            System.out.println("type: " + type + "  value: " + val + "  quantity: " + size);
        }
    }

    public void addToInventory(Asset a) {
        inventory.addLast(a);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: observe the properties of the tile you are standing on
    public void observeTile(StoryGame storyGame, int col, int row) {
        Tile tile = storyGame.getTileMap().getListOfTiles().get(row).get(col);
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(row).get(col).getAssets();
        String shape = tile.getShape();
        String color = tile.getColor();
        int length = tile.getLength();
        EventLog.getInstance().logEvent(new Event("look at tile"));
        System.out.println("tile (" + col + "," + row + ")");
        System.out.println("shape: " + shape);
        System.out.println("color: " + color);
        System.out.println("length: " + length);
        System.out.println("assets: " + assets);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: remove one instanceof Sapling, plants a tree on the tile
    public void plantSapling(StoryGame storyGame, int col, int row) {
        LinkedList<Asset> toRemove = new LinkedList<>();
        for (Asset asset : inventory) {
            if (asset instanceof Sapling) {
                toRemove.add(asset);
                storyGame.getTileMap().getListOfTiles().get(row).get(col).addAsset(asset);
                break;
            }
        }
        inventory.removeAll(toRemove);
        EventLog.getInstance().logEvent(new Event("plant sapling"));
        System.out.println("planted saplings:" + toRemove);
        System.out.println("inventory update:" + inventory);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: remove first tree instance from tile
    // process trees into lumber and store in inventory
    public void cutDownOneTree(StoryGame storyGame, int col, int row) {
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(row).get(col).getAssets();
        List<Asset> toRemove = new ArrayList<>();
        int logVal = 20;
        for (Asset a : assets) {
            if (a instanceof Tree) {
                int treeQuantity = a.getAssetSize();
                int treeValue = a.getAssetValue();
                int ecoValue = treeQuantity * treeValue;
                int logQuantity = ecoValue / logVal;
                Lumber lumber = new Lumber("lumber", logQuantity, logVal);
                inventory.addLast(lumber);
                toRemove.add(a);
                break;
            }
        }
        assets.removeAll(toRemove);
        storyGame.getTileMap().getListOfTiles().get(row).get(col).setAssets(assets);
        EventLog.getInstance().logEvent(new Event("cut down one tree"));
        System.out.println("chopped trees:" + toRemove);
        System.out.println("inventory update:" + inventory);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: remove all trees from tile
    // process trees into lumber and store in inventory
    public void deForest(StoryGame storyGame, int col, int row) {
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(row).get(col).getAssets();
        List<Asset> toRemove = new ArrayList<>();
        int logVal = 20;
        for (Asset a : assets) {
            if (a instanceof Tree) {
                int treeQuantity = a.getAssetSize();
                int treeValue = a.getAssetValue();
                int ecoValue = treeQuantity * treeValue;
                int logQuantity = ecoValue / logVal;
                Lumber lumber = new Lumber("lumber", logQuantity, logVal);
                inventory.addLast(lumber);
                toRemove.add(a);
            }
        }
        assets.removeAll(toRemove);
        storyGame.getTileMap().getListOfTiles().get(row).get(col).setAssets(assets);
        EventLog.getInstance().logEvent(new Event("cut down one forest"));
        System.out.println("chopped trees:" + toRemove);
        System.out.println("inventory update:" + inventory);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: remove wood from inventory and drop wood on tile
    public void drop(StoryGame storyGame, int col, int row) {
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(row).get(col).getAssets();
        LinkedList<Asset> toRemove = new LinkedList<>();
        int i = 0;
        for (Asset a : inventory) {
            i += 1;
            if (a instanceof Lumber) {
                toRemove.add(a);
                assets.add(a);
                break;
            }
        }
        inventory.removeAll(toRemove);
        storyGame.getTileMap().getListOfTiles().get(row).get(col).setAssets(assets);
        EventLog.getInstance().logEvent(new Event("dropped something"));
        System.out.println("dropped:" + toRemove);
    }

    // REQUIRES: StoryGame, column, row
    // MODIFIES: inventory, tile at given column and row
    // EFFECTS: pick up wood on tile and store in inventory
    public void pick(StoryGame storyGame, int col, int row) {
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(row).get(col).getAssets();
        List<Asset> toRemove = new ArrayList<>();
        for (Asset a : assets) {
            if (a instanceof Lumber) {
                toRemove.add(a);
                inventory.addLast(a);
                break;
            }
        }
        assets.removeAll(toRemove);
        storyGame.getTileMap().getListOfTiles().get(row).get(col).setAssets(assets);
        EventLog.getInstance().logEvent(new Event("picked up something"));
        System.out.println("picked up:" + toRemove);
    }

    // MODIFIES: inventory, money
    // EFFECTS: remove all instance of Lumber from inventory, gain money equal to asset quantity * value
    public void sellAllLumber() {
        List<Asset> toRemove = new ArrayList<>();
        for (Asset a : inventory) {
            if (a instanceof Lumber) {
                toRemove.add(a);
                money += (a.getAssetSize() * a.getAssetValue());
            }
        }
        inventory.removeAll(toRemove);
        EventLog.getInstance().logEvent(new Event("sell all lumber"));
    }

    // MODIFIES: inventory, money
    // EFFECTS: remove all instance of Sapling from inventory, gain money equal to asset quantity * value
    public void sellAllSapling() {
        List<Asset> toRemove = new ArrayList<>();
        for (Asset a : inventory) {
            if (a instanceof Sapling) {
                toRemove.add(a);
                money += (a.getAssetSize() * (a.getAssetValue() / 2));
            }
        }
        inventory.removeAll(toRemove);
        EventLog.getInstance().logEvent(new Event("sell all sapling"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("money", money);
        json.put("inventory", inventoryToJson());
        return json;
    }

    public JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Asset a : inventory) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }
}
