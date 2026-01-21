package me.sfclog.entitymodels.commands;


import me.sfclog.entitymodels.MetadataHandler;
import me.sfclog.entitymodels.customentity.DynamicEntity;
import me.sfclog.entitymodels.customentity.StaticEntity;
import me.sfclog.entitymodels.dataconverter.FileModelConverter;
import me.sfclog.entitymodels.utils.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler implements TabExecutor, CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender,  Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.isOp() || p.hasPermission("*")) {

                if(args.length < 1) {
                    send(sender," ");
                    send(sender," &f/entitymodels reload &fĐể tải lại Plugin");
                    send(sender," &f/entitymodels killall &fĐể giết toàn bộ EntityModel");
                    send(sender," &f/entitymodels spawnstatic <id> &9Để Spawn ra EntityModel đứng yên");
                    send(sender," &f/entitymodels spawndynamic <id> &9Để Spawn ra EntityModel chuyển động");
                    send(sender," ");
                } else if(args[0].equalsIgnoreCase("reload")) {
                    send(sender,"&aĐã tải lại Plugin");
                    MetadataHandler.PLUGIN.onDisable();
                    MetadataHandler.PLUGIN.onEnable();
                } else if(args[0].equalsIgnoreCase("killall")) {
                    StaticEntity.shutdown();
                    DynamicEntity.shutdown();
                    send(sender,"&aĐã giết toàn bộ EntityModels");
                } else if(args[0].equalsIgnoreCase("spawnstatic")) {
                    if(args.length == 2) {
                        String id = args[1];
                        if (id != null && getEntityID().contains(id)) {
                            StaticEntity.create(id, p.getLocation());
                        } else {
                            send(sender,"&cLỗi &fKhông có ModelEntity nào tồn tại với ID: &e" + id);
                        }
                    } else {
                        send(sender," &f/entitymodels spawnstatic <id> &9Để Spawn ra EntityModel đứng yên");
                    }
                } else if(args[0].equalsIgnoreCase("spawndynamic")) {
                    if(args.length == 2) {
                        String id = args[1];
                        if (id != null && getEntityID().contains(id)) {
                            LivingEntity livi = (LivingEntity) p.getWorld().spawnEntity(p.getLocation(), EntityType.PIG);
                            DynamicEntity.create(id,livi, true);
                        } else {
                            send(sender,"&cLỗi &fKhông có ModelEntity nào tồn tại với ID: &e" + id);
                        }
                    } else {
                        send(sender," &f/entitymodels spawndynamic <id> &9Để Spawn ra EntityModel chuyển động");
                    }
                }
            } else {
                send(sender,"&cXin Lỗi! Bạn không có quyền dùng lệnh này!");
            }
        }
        return false;
    }


    public static void send(CommandSender send , String msg) {
        send.sendMessage(Color.tran(msg));
    }
    public static List<String> getEntityID() {
        List<String> entityIDs = new ArrayList<>();
        FileModelConverter.getConvertedFileModels().values().forEach(fileModelConverter -> entityIDs.add(fileModelConverter.getID()));
        return entityIDs;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> com = new ArrayList<>();
        com.add("killall");
        com.add("reload");
        com.add("spawnstatic");
        com.add("spawndynamic");


        if(args.length > 1) {
            if(args[0].equalsIgnoreCase("spawndynamic") || args[0].equalsIgnoreCase("spawnstatic")) {
                if(args.length == 2) {
                    return getEntityID().stream()
                            .filter(name -> name.startsWith(args[1]))
                            .collect(Collectors.toList());
                 } else {
                    return new ArrayList<>();
                }
            }
        }

        return com.stream()
                .filter(name -> name.startsWith(args[0]))
                .collect(Collectors.toList());
    }
}
