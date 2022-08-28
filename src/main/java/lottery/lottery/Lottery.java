package lottery.lottery;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

