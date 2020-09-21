package card_class;
import javafx.scene.control.Alert;

import java.util.Map;

/**
 * card = new card("Name of the card(all upper case)")
 * card.getCard_name() return the name of card
 * card.getDamage() return the damage of the card, can be + and -, depends on the type
 * card.getType() return the type of the card ("DEFENSE", "ATTACK", etc))
 * card.getDescription() return description (not enough details)
 * error handling: not done yet
 */
public class Card {
    String card_name = "";

    String type = "";

    int damage = 0;

    String description = "";
    
    
    public Boolean PlayAtStart = false;
    public Card() {   	
    }
    
    public Card(String name) {
        this.card_name = name;
        Map<String, String> type1 = Map.ofEntries(
                Map.entry("BATTLE AXE", "ATTACK"),
                Map.entry("AXE", "ATTACK"),
                Map.entry("SWORD", "ATTACK"),
                Map.entry("STICK", "ATTACK"),
                Map.entry("MACE", "ATTACK"),
                Map.entry("CROSSBOW", "ATTACK"),
                Map.entry("STONE WALL", "DEFENSE"),
                Map.entry("BARBED WIRE", "DEFENSE"),
                Map.entry("REINFORCED GATE", "DEFENSE"),
                Map.entry("WOODEN WALL", "DEFENSE"),
                Map.entry("IRON DOOR", "DEFENSE"),
                Map.entry("STEEL CHAINS", "DEFENSE"),
                Map.entry("THUNDERSTORM", "DEFENSE"),
                Map.entry("EARTHQUAKE", "DEFENSE"),
                Map.entry("TORNADO", "DEFENSE"),
                Map.entry("FLOOD", "DEFENSE"),
                Map.entry("ARCHER TOWER", "SPECIAL"),
                Map.entry("TRADE", "SPECIAL"),
                Map.entry("SCOUT", "SPECIAL")
        );
        this.type = type1.get(name);
        Map<String, Integer> damage1 = Map.ofEntries(
                Map.entry("BATTLE AXE", 8),
                Map.entry("AXE", 3),
                Map.entry("SWORD", 4),
                Map.entry("STICK", 1),
                Map.entry("MACE", 5),
                Map.entry("CROSSBOW", 10),
                Map.entry("STONE WALL", 5),
                Map.entry("WOODEN WALL", 2),
                Map.entry("BARBED WIRE", 8),
                Map.entry("REINFORCED GATE", 14),
                Map.entry("IRON DOOR", 10),
                Map.entry("STEEL CHAINS", 7),
                Map.entry("THUNDERSTORM", -4),
                Map.entry("TORNADO", -6),
                Map.entry("FLOOD", -3),
                Map.entry("EARTHQUAKE", -8),
                Map.entry("ARCHER TOWER", 0),
                Map.entry("TRADE", 0),
                Map.entry("SCOUT", 0)
        );
        this.damage = damage1.get(name);
        if (!this.card_name.equals("") && !this.type.equals("")){
            if (this.type.equals("ATTACK")){
                this.description = "Deals " + this.damage + " damage to an opponent.";
            }
            else if (this.type.equals("DEFENSE")){
                if (this.damage > 0){
                    this.description = "Recover HP by " + this.damage + ".";
                }
                else{
                    this.description = "Reduce HP by " + (-this.damage) + ".";
                }
            }
        }
        else{
            System.out.println("Card not Found");
        }
    }

    public String getCard_name(){
        return card_name;
    }

    public String getDescription(){
        return description;
    }

    public int getDamage(){
        return this.damage;
    }

    public String getType(){
        return this.type;
    }
    
    public void SetName(String name) {
    	card_name = name;
    }
    
    public void SetType(String type) {
    	this.type = type;
    }
}
