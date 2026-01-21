package me.sfclog.entitymodels.event;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import me.sfclog.entitymodels.hook.MyThicSkillHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerEvent implements Listener {


    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event) {

        event.register(new MyThicSkillHook(event.getConfig()));
    }

}
