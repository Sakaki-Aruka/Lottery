package lottery.lottery;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class Lottery extends JavaPlugin implements Listener {

    public static FileConfiguration fc = new YamlConfiguration();

    public void load(){
        fc = getConfig();
        Load load = new Load();
        load.returnFC(fc);
        }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // config data load
        this.load();
        getServer().getPluginManager().registerEvents(new BlockPlace(),this);
    }

     @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

