package com.chillguy.simplerunes.commands;

import com.chillguy.simplerunes.abstracts.Runner;
import com.chillguy.simplerunes.utils.PDHUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final PDHUtils pdhUtils;

    public MainCommand(PDHUtils pdhUtils) {
        this.pdhUtils = pdhUtils;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by a player!");
            return true;
        }

        Player player = (Player) commandSender;

        if(args.length == 0) {
            player.sendMessage("SubCommands: giverunner, givegem, givebook");
            return true;
        }

        switch (args[0].toLowerCase()){
            case "giverunner":
                if(args.length < 2) {
                    player.sendMessage("Usage: /runner giverunner <runner>");
                    return true;
                }
                String runner = args[1];
                switch (runner.toLowerCase()) {
                    case "regen":
                        player.getInventory().addItem(pdhUtils.getRunnerRegen().createItem());
                        return true;
                    case "haste":
                        player.getInventory().addItem(pdhUtils.getRunnerHaste().createItem());
                        return true;
                    case "speed":
                        player.getInventory().addItem(pdhUtils.getRunnerSpeed().createItem());
                        return true;
                }
            case "givegem":
                player.getInventory().addItem(pdhUtils.createGem());
                return true;
            case "givebook":
                player.getInventory().addItem(pdhUtils.getBookRunner().getItem());
                return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("runner")) {
            if(args.length == 1){
                List<String> list = new ArrayList<>();
                list.add("giverunner");
                list.add("givegem");
                list.add("givebook");
                return list;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("giverunner")) {
                List<String> spellNames = new ArrayList<>();
                for (Runner spell : pdhUtils.getRunners()) {
                    spellNames.add(spell.getName());
                }
                return spellNames;
            }
        }
        return Collections.emptyList();
    }
}
