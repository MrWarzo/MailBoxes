package fr.mrwarzo.mailboxes.tools;

import fr.mrwarzo.mailboxes.managers.ConfigManager;
import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandCompletionUtils {

	public static List<String> playersName(){
		List<String> playerNames = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
		return playerNames;
	}

	public static List<String> numberInRange(int n1, int n2){
		List<String> numbersList = new ArrayList<>();
		while (n1 <= n2){
			numbersList.add(String.valueOf(n1++));
		}
		return numbersList;
	}

	public static List<String> doubleChoice(String choice1, String choice2){
		return Arrays.asList(choice1, choice2);
	}

	public static List<String> tripleChoice(String choice1, String choice2, String choice3){
		return Arrays.asList(choice1, choice2, choice3);
	}

	public static List<String> getConfigurationFilesName() {
		List<String> cfgList = new ArrayList<>();
		ConfigManager configurationManager = Managers.getConfigManager();
		configurationManager.getFilesMap().forEach((file, fileConfiguration) -> {
			cfgList.add(file.getName());
		});
		return cfgList;
	}

	public static List<String> getBookID() {
		ConfigManager configurationManager = Managers.getConfigManager();
		FileConfiguration cfg = configurationManager.getConfigurationFile("wiki.yml");
		ConfigurationSection cs = cfg.getConfigurationSection("BOOKS_ID");
		assert cs != null;
		return new ArrayList<>(cs.getKeys(false));
	}
}
