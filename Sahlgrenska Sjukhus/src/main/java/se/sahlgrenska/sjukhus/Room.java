package se.sahlgrenska.sjukhus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import se.sahlgrenska.sjukhus.item.Equipment;
import se.sahlgrenska.sjukhus.item.Item;
import se.sahlgrenska.sjukhus.item.Medicine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Room {
    private String name;
    private int size;
    private HashMap<Item, Integer> itemsInRoom = new HashMap<Item, Integer>();

    public Room(String name, int size) {
        this.name = name;
        this.size = size;

    }

    public Room(String name, int size, HashMap<Item, Integer> itemsInRoom) {
        this.name = name;
        this.size = size;
        this.itemsInRoom = itemsInRoom;
        addItemsToRoomTest();
    }



    private void addItemsToRoomTest() {
        Item item1 = new Equipment("Defibrilator", "Starts hearts", 2500.5f, true);
        Item item2 = new Equipment("MRI", "Scans body", 5000.0f, true);
        Item item3 = new Medicine("Panodil", "Pain relief", 15.0f, LocalDate.now());

        itemsInRoom.put(item1, 2);
        itemsInRoom.put(item1, 4);
        itemsInRoom.put(item2, 1);
        itemsInRoom.put(item3, 10);
    }


    public void printRoomItems() {
        for (Map.Entry<Item, Integer> roomItems :
                itemsInRoom.entrySet()) {

            // Printing all elements of a Map
            System.out.print(roomItems.getKey() + " Amount: "
                    + roomItems.getValue());
        }

    }

    public HashMap<Item, Integer> getItems() {
        return itemsInRoom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return size == room.size && Objects.equals(name, room.name) && Objects.equals(itemsInRoom, room.itemsInRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, itemsInRoom);
    }
}
