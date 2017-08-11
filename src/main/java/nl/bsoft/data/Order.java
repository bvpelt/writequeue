package nl.bsoft.data;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 2932423412341L;

    private long itemCode;
    private String itemName;
    private int itemQuantity;

    public Order() {

    }

    public Order(final long itemCode, final String itemName, final int itemQuantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public Order(final long itemCode, final String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQuantity = 1;
    }

    public String toString() {
        return "item: " + Long.toString(itemCode) + " " +
                "itemName: " + itemName + " " +
                "itemQuantity: " + Integer.toString(itemQuantity);
    }

    public long getItemCode() {
        return itemCode;
    }

    public void setItemCode(long itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantaty() {
        return itemQuantity;
    }

    public void setItemQuantaty(int itemQuantaty) {
        this.itemQuantity = itemQuantaty;
    }
}
