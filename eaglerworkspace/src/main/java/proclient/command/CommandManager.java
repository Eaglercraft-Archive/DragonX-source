package proclient.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proclient.Dragon;
import proclient.command.commands.Help;
import proclient.command.commands.Vclip;
import proclient.event.events.EventChat;

public class CommandManager {

    public static List<Command> commands = new ArrayList<Command>();
    public static String prefix = ".";

    public CommandManager() {
        setup();
    }

    public void setup() {
        commands.add(new Vclip());
        commands.add(new Help());
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public static void handleChat(EventChat event) {
        String message = event.getMessage();

        if(!message.startsWith(prefix))
            return;

        event.setCancelled(true);

        message = message.substring(prefix.length());

        boolean foundCommand = false;

        if(message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            for(Command c : commands) {
                if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    foundCommand = true;
                    break;
                }
            }
        }
        if(!foundCommand) {
            Dragon.moduleManager.addChatMessage("command not found!");
        }
    }
    
}
