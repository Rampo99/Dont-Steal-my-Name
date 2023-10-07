package me.Rampo.main;

import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class DontStealMyName extends Plugin implements Listener {

    private String urlString = "https://api.mojang.com/users/profiles/minecraft/";

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO,"Enabling DontStealMyName - Made by Rampo!");
        getProxy().getPluginManager().registerListener(this,this);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent e) {
        PendingConnection c = e.getConnection();
        String name = c.getName();
        URL url;
        HttpURLConnection con;
        try {
            url = new URL(urlString+name);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String x = reader.readLine();
                if(x != null) {
                    c.setOnlineMode(true);
                } else {
                    e.setCancelled(true);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }



    }

}

