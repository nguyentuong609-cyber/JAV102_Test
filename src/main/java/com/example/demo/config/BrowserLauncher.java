package com.example.demo.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BrowserLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        String url = "http://localhost:8080/shop/menu";
        String os = System.getProperty("os.name").toLowerCase();

        System.out.println("🔄 Application ready! Attempting to launch browser via OS terminal...");

        try {
            if (os.contains("win")) {
                // For Windows: Uses the 'start' command via cmd
                new ProcessBuilder("cmd", "/c", "start", url).start();
            } else if (os.contains("mac")) {
                // For macOS: Uses the native 'open' command
                new ProcessBuilder("open", url).start();
            } else if (os.contains("nix") || os.contains("nux")) {
                // For Linux: Uses xdg-open
                new ProcessBuilder("xdg-open", url).start();
            } else {
                System.out.println("⚠️ Unknown OS. Could not trigger automatic browser launch.");
            }
            System.out.println("🚀 Browser command sent successfully!");
        } catch (IOException e) {
            System.err.println("❌ Failed to launch browser via native OS command: " + e.getMessage());
        }
    }
}