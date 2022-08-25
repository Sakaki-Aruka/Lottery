package lottery.lottery;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;



import java.util.ArrayList;
import java.util.Arrays;

public final class Lottery extends JavaPlugin implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        Player player = e.getPlayer();
        Plugin pl = Bukkit.getPluginManager().getPlugin("Lottery");
        //write here
        ArrayList<String> Name = new ArrayList<>();
        ArrayList<String> EnchantName = new ArrayList<>();
        ArrayList<String> EnchantLevel = new ArrayList<>();
        ArrayList<String> MaterialName = new ArrayList<>();
        ArrayList<String> Restriction = new ArrayList<>();
        ArrayList<String> ItemFlagName = new ArrayList<>();
        ArrayList<Double> Weight = new ArrayList<>();

        //debug
        //e.getPlayer().sendMessage("items:"+getConfig().getInt("NumberOfItems"));

        int Items = getConfig().getInt("NumberOfItems");
        for(int i=1;i<= Items;i++){
            Name.add(getConfig().getString("Item"+i+".Name"));
            EnchantName.add(getConfig().getString("Item"+i+".Enchantment"));
            EnchantLevel.add(getConfig().getString("Item"+i+".Enchantment_level"));
            MaterialName.add(getConfig().getString("Item"+i+".Material"));
            Restriction.add(getConfig().getString("Item"+i+".Restriction"));
            ItemFlagName.add(getConfig().getString("Item"+i+".ItemFlag"));
            Weight.add(getConfig().getDouble("Item"+i+".Weight"));

        }

        //debug
        //e.getPlayer().sendMessage("ItemName:"+Name+"\nEnchantment:"+EnchantName+"\nEnchantment_level:"+EnchantLevel+"\nMaterial:"+MaterialName+"\nRestriction:"+Restriction+"\nItemFlag:"+ItemFlagName+"\nWeight:"+Weight);

        ItemStack MainHand = player.getInventory().getItemInMainHand();

        if(MainHand.containsEnchantment(Enchantment.MENDING)){
            if(MainHand.getType()== Material.CHEST){
                int player_have = MainHand.getAmount();
                if(1 < player_have){
                    MainHand.setAmount(player_have-1);
                }else{
                    MainHand.setAmount(0);
                }
                e.setCancelled(true);
                Location location = e.getBlock().getLocation();
                location.setY(location.getY()+0.25);
                World world = player.getWorld();
                double result = Math.random();

                double step = 0.0;
                double step_copy = 0.0;
                int counter = 0;

                for(double i:Weight){
                    step += i;
                    if(step_copy <= result && result < step){
                        //write here
                        this.template(Material.getMaterial(MaterialName.get(counter)),EnchantName.get(counter),EnchantLevel.get(counter),Restriction.get(counter),Name.get(counter),location,world,ItemFlagName.get(counter));
                        return;
                    }else{
                        step_copy =step;
                        counter++;
                    }
                }
            }

        }
    }

    public void template(Material item,String enchantment,String level,String restriction,String name,Location location,World world,String itemFlag){
        ItemStack itemStack = new ItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();


        //Enchantment and Flag
        //exist enchantments parameters
        if(!(enchantment.equalsIgnoreCase("Nothing"))){
            //write here
            String[] enchantString = enchantment.split(" ");
            ArrayList<String> enchantArray = new ArrayList<String>(Arrays.asList(enchantString));

            String[] levelString = level.split(" ");
            ArrayList<String> StringLevelArray = new ArrayList<String>(Arrays.asList(levelString));

            String[] restrictionString = restriction.split(" ");
            ArrayList<String> restrictionArray = new ArrayList<String>(Arrays.asList(restrictionString));

            for(int i=0;i<enchantArray.size();i++){
                itemMeta.addEnchant(Enchantment.getByName(enchantArray.get(i)),Integer.valueOf(StringLevelArray.get(i)),Boolean.valueOf(restrictionArray.get(i)));
            }
        }else{
            //exists "Nothing".
        }

        //exist item flag parameters
        if(!(itemFlag.equalsIgnoreCase("Nothing"))){
            //write here
            String[] FlagString = itemFlag.split(" ");
            ArrayList<String> FlagArray = new ArrayList<>(Arrays.asList(FlagString));
            for (int i=0;i<FlagArray.size();i++){
                itemMeta.addItemFlags(ItemFlag.valueOf(FlagArray.get(i)));
            }
        }else{
            //exists "Nothing
        }

        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        world.dropItemNaturally(location,itemStack);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this,this);
    }

     @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
