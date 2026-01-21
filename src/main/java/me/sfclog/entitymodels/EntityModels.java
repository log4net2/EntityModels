package me.sfclog.entitymodels;

import com.magmaguy.easyminecraftgoals.NMSManager;
import me.sfclog.entitymodels.commands.CommandHandler;
import me.sfclog.entitymodels.config.ModelsFolder;
import me.sfclog.entitymodels.config.OutputFolder;
import me.sfclog.entitymodels.customentity.DynamicEntity;
import me.sfclog.entitymodels.customentity.ModeledEntityEvents;
import me.sfclog.entitymodels.customentity.StaticEntity;
import me.sfclog.entitymodels.customentity.core.LegacyHitDetection;
import me.sfclog.entitymodels.dataconverter.FileModelConverter;
import me.sfclog.entitymodels.event.ServerEvent;
import me.sfclog.entitymodels.hook.MyThicSkillHook;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityModels extends JavaPlugin {


    @Override
    public void onEnable() {
        MetadataHandler.PLUGIN = this;

        ModelsFolder.initializeConfig();
        OutputFolder.initializeConfig();
        Bukkit.getPluginManager().registerEvents(new ModeledEntityEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LegacyHitDetection(), this);
        Bukkit.getPluginManager().registerEvents(new ServerEvent(), this);
        NMSManager.initializeAdapter(this);


        getCommand("entitymodels").setExecutor(new CommandHandler());
        getCommand("entitymodels").setTabCompleter(new CommandHandler());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        FileModelConverter.shutdown();
        StaticEntity.shutdown();
        DynamicEntity.shutdown();
        LegacyHitDetection.shutdown();
        Bukkit.getServer().getScheduler().cancelTasks(MetadataHandler.PLUGIN);
        HandlerList.unregisterAll(MetadataHandler.PLUGIN);
    }
}
