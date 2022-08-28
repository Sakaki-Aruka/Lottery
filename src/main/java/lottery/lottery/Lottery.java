package lottery.lottery;

import org.bukkit.*;
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

    private static int Items;
    private static ArrayList<String> Name = new ArrayList<>();
    private static ArrayList<String> EnchantName = new ArrayList<>();
    private static ArrayList<String> EnchantLevel = new ArrayList<>();
    private static ArrayList<String> MaterialName = new ArrayList<>();
    private static ArrayList<String> Restriction = new ArrayList<>();
    private static ArrayList<String> ItemFlagName = new ArrayList<>();
    private static ArrayList<Double> Weight = new ArrayList<>();
    private static ArrayList<String> FanfareName = new ArrayList<>();
    private static ArrayList<String> FanfareVolume = new ArrayList<>();
    private static ArrayList<String> FanfarePitch = new ArrayList<>();
    private static ArrayList<Boolean> LoreUse = new ArrayList<>();
    private static ArrayList<String> Lore = new ArrayList<>();

    public void load(){


        Items = getConfig().getInt("NumberOfItems");
        for(int i=1;i<=Items;i++){
            Name.add(getConfig().getString("Item"+i+".Name"));
            EnchantName.add(getConfig().getString("Item"+i+".Enchantment"));
            EnchantLevel.add(getConfig().getString("Item"+i+".Enchantment_level"));
            MaterialName.add(getConfig().getString("Item"+i+".Material"));
            Restriction.add(getConfig().getString("Item"+i+".Restriction"));
            ItemFlagName.add(getConfig().getString("Item"+i+".ItemFlag"));
            Weight.add(getConfig().getDouble("Item"+i+".Weight"));
            FanfareName.add(getConfig().getString("Item"+i+".FanfareSound"));
            FanfareVolume.add(getConfig().getString("Item"+i+".FanfareSoundVolume"));
            FanfarePitch.add(getConfig().getString("Item"+i+".FanfareSoundPitch"));
            LoreUse.add(getConfig().getBoolean("Item"+i+".LoreUse"));

        }

    }

    public ArrayList<String> Name(){
        return Name;
    }

    public ArrayList<String> EnchantName(){
        return EnchantName;
    }

    public ArrayList<String> EnchantLevel(){
        return EnchantLevel;
    }

    public ArrayList<String> MaterialName(){
        return MaterialName;
    }

    public ArrayList<String> Restriction(){
        return Restriction;
    }

    public ArrayList<String> ItemFlagName(){
        return ItemFlagName;
    }

    public ArrayList<Double> Weight(){
        return Weight;
    }

    public ArrayList<String> FanfareName(){
        return FanfareName;
    }

    public ArrayList<String> FanfareVolume(){
        return FanfareVolume;
    }

    public ArrayList<String> FanfarePitch(){
        return FanfarePitch;
    }

    public ArrayList<Boolean> LoreUse(){
        return LoreUse;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // config data load
        this.load();
        getServer().getPluginManager().registerEvents(this,this);
    }

     @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        Plugin pl = Bukkit.getPluginManager().getPlugin("Lottery");
        ItemStack MainHand = player.getInventory().getItemInMainHand();

        if(MainHand.containsEnchantment(Enchantment.MENDING)){
            if(MainHand.getType()== Material.CHEST){
                // the player place mending-chest(lottery event started)

                // data load start
                ArrayList<String> Name = this.Name();
                ArrayList<String> EnchantName = this.EnchantName();
                ArrayList<String> EnchantLevel = this.EnchantLevel();
                ArrayList<String> MaterialName = this.MaterialName();
                ArrayList<String> Restriction = this.Restriction();
                ArrayList<String> ItemFlagName = this.ItemFlagName();
                ArrayList<Double> Weight = this.Weight();
                ArrayList<String> FanfareName = this.FanfareName();
                ArrayList<String> FanfareVolume = this.FanfareVolume();
                ArrayList<String> FanfarePitch = this.FanfarePitch();
                ArrayList<Boolean> LoreUse = this.LoreUse();
                // data load finish

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

                // lottery start
                for(double i:Weight){
                    step += i;
                    if(step_copy <= result && result < step){
                        this.template(Material.getMaterial(MaterialName.get(counter)),EnchantName.get(counter),EnchantLevel.get(counter),Restriction.get(counter),Name.get(counter),location,world,ItemFlagName.get(counter),LoreUse.get(counter),counter+1);

                        //playSound
                        if(!(FanfareName.get(counter).equalsIgnoreCase("Nothing"))){
                            //exist sound name
                            float volume=0F;
                            float pitch=0F;
                            try{
                                volume = Float.valueOf(FanfareVolume.get(counter));
                                pitch = Float.valueOf(FanfarePitch.get(counter));
                            }catch (Exception exception){
                                System.out.println("[Lottery Plugin]-> TypeError:You should write FanfareSoundVolume and FanfareSoundPitch in float.");
                            }

                            this.fanfare(player,location,FanfareName.get(counter),volume,pitch);

                        }

                        return;
                    }else{
                        step_copy =step;
                        counter++;
                    }
                    // lottery finish
                }
            }
        }
    }

    public void template(Material item,String enchantment,String level,String restriction,String name,Location location,World world,String itemFlag,boolean LoreUse,int itemNumber){
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

        ArrayList<String> Lore = new ArrayList<>();
        if(LoreUse){
            //lore use
            for (int i=1;i<=10;i++){
                try{

                    Lore.add(getConfig().getString("Item"+itemNumber+".Lore."+i));
                }catch (Exception exception){
                    //debug
                    System.out.println("[Lottery Plugin]-> LoreException:"+exception);
                }
            }
        }

        itemMeta.setLore(Lore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        world.dropItemNaturally(location,itemStack);
    }

    public void fanfare(Player player, Location location, String soundName, float volume, float pitch){
        try{
            Sound sound = Sound.valueOf(soundName);
            player.playSound(location,sound,volume,pitch);

        }catch (Exception exception){
            System.out.println("[Lottery Plugin]-> SoundException:"+exception);
        }
    }



}
