package lottery.lottery;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class Load {
    public static FileConfiguration FC;
    public void returnFC(FileConfiguration fileConf){
        FC = fileConf;
        this.getConfig();
    }

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
    private static ArrayList<Boolean> unbreakable = new ArrayList<>();

    public void getConfig(){

        Items = FC.getInt("NumberOfItems");
        for(int i=1;i<=Items;i++){
            Name.add(FC.getString("Item"+i+".Name"));
            EnchantName.add(FC.getString("Item"+i+".Enchantment"));
            EnchantLevel.add(FC.getString("Item"+i+".Enchantment_level"));
            MaterialName.add(FC.getString("Item"+i+".Material"));
            Restriction.add(FC.getString("Item"+i+".Restriction"));
            ItemFlagName.add(FC.getString("Item"+i+".ItemFlag"));
            Weight.add(FC.getDouble("Item"+i+".Weight"));
            FanfareName.add(FC.getString("Item"+i+".FanfareSound"));
            FanfareVolume.add(FC.getString("Item"+i+".FanfareSoundVolume"));
            FanfarePitch.add(FC.getString("Item"+i+".FanfareSoundPitch"));
            LoreUse.add(FC.getBoolean("Item"+i+".LoreUse"));
            unbreakable.add(FC.getBoolean("Item"+i+".unbreak"));

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

    public ArrayList<Boolean> Unbreakable(){
        return unbreakable;
    }
}
