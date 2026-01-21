package me.sfclog.entitymodels.utils;



import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    public static void tran(List<String> line) {
        line.replaceAll(e -> e.replace(e,Color.tran(e)));
    }
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    public static String tran(String msg) {
        if (msg != null) {
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                String color = msg.substring(matcher.start(), matcher.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(msg);
            }
            String msgok = ChatColor.translateAlternateColorCodes('&', msg);
            return msgok.replace("&", "");
        }
        return "...";
    }


}