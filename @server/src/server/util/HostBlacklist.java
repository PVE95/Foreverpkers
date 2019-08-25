package server.util;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import server.util.*;
import java.net.InetSocketAddress;
 
public class HostBlacklist {
 
    private static final String BLACKLIST_DIR = "./data/bans/blacklist.txt";
 
    public static List<String> blockedHostnames = new ArrayList<String>();
 
    public static List<String> getBlockedHostnames() {
        return blockedHostnames;
    }
 
    public static boolean isBlocked(String host) {
        return blockedHostnames.contains(host.toLowerCase());
    }
 
    public static void loadBlacklist() {
        String word = null;
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(BLACKLIST_DIR));
            while ((word = in.readLine()) != null)
                blockedHostnames.add(word.toLowerCase());
            in.close();
            in = null;
        } catch (final Exception e) {
            System.out.println("Could not load blacklisted hosts.");
        }
    }
}