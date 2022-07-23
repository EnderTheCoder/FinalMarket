package dev.ender.finalmarket.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DemoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            sender.sendMessage("this message is sent by a player");
            Player player = (Player) sender;
            player.setHealth(0.0d);
            return true;
        } else {
            sender.sendMessage("this message is send by a console");
            return false;
        }




    }
}
