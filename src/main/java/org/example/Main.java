package org.example;

import HES.Systems.EnvironmentalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EnvironmentalSystem system = new EnvironmentalSystem();
        TempMonitor Northward = new TempMonitor("North Ward",0,23,system);
        TempMonitor Centralward = new TempMonitor("Central Ward",1,22,system);
        TempMonitor Southward = new TempMonitor("South Ward",2,20,system);
        PollutionMonitor Polmon = new PollutionMonitor("Pollution",0,5,system);
        HumidityMonitor HumidMon = new HumidityMonitor("Humidity",0,0,system);
        JFrame frame = new JFrame("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 400);

        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 5, 5));   // 5 cells fit in 2x3

        String[] names = {"North Ward", "South Ward", "Central Ward", "Pollution", "Humidity"};

        // name -> label, so each reading goes to the right cell
        Map<String, JLabel> labels = new LinkedHashMap<>();

        for (String name : names) {
            JPanel cell = new JPanel(new GridLayout(1, 1));
            cell.setBorder(BorderFactory.createTitledBorder(name));

            JLabel text = new JLabel("--", SwingConstants.CENTER);
            cell.add(text);

            labels.put(name, text);
            mainPanel.add(cell);
        }

        frame.add(mainPanel);
        frame.setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ward temperatures: the number is the feed index (0, 1, 2 assumed)
                Northward.update();
                labels.get("North Ward").setText(String.format(
                        "<html><center>Temp: %.1f C"+"<br> Heater:%s "+ "<br> IDEAL : 23C</center></html>",
                        system.getTempFeed().getTemperature(0),
                        system.isHeatingOn(0) ? "ON" : "OFF"));
                labels.get("North Ward").setForeground(
                        Northward.isOutOfRange() ? Color.RED : Color.BLACK);

                Centralward.update();
                labels.get("Central Ward").setText(String.format(
                        "<html><center>"+ "Temp: %.1f C"+"<br>Heater:%s" + "<br>IDEAL : 22C"+"</center></html>",
                        system.getTempFeed().getTemperature(1),
                        system.isHeatingOn(1) ? "ON" : "OFF"));
                labels.get("Central Ward").setForeground(
                        Centralward.isOutOfRange() ? Color.RED : Color.BLACK);

                Southward.update();
                labels.get("South Ward").setText(String.format(
                        "<html><center>"+ "Temp: %.1f C<br>  " + "Heater: %s<br>"+ "IDEAL : 20C" + "</center></html>",
                        system.getTempFeed().getTemperature(2),
                        system.isHeatingOn(2) ? "ON" : "OFF"));
                labels.get("South Ward").setForeground(
                        Southward.isOutOfRange() ? Color.RED : Color.BLACK);
                Polmon.update();
                labels.get("Pollution").setText(String.format(
                        "<html><center>Pollution %s <br> Purifier : %s<br></center></html>",
                        system.getPollutionFeed().getPollution(),
                        system.isAirPurifierOn() ? "ON" : "OFF"));
                labels.get("Pollution").setForeground(
                        Polmon.isOutOfRange() ? Color.RED : Color.BLACK);
                HumidMon.update();
                labels.get("Humidity").setText(String.format(
                        "<html><center>Inside %s " + "<br> Outside : %s <br>" + "%s"+"</center></html>",
                        system.getHumidityFeed().getInsideHumidity(),
                        system.getHumidityFeed().getOutsideHumidity(),
                        Math.abs(system.getHumidityFeed().getInsideHumidity()
                                - system.getHumidityFeed().getOutsideHumidity())));
                labels.get("Humidity").setForeground(
                        HumidMon.isOutOfRange() ? Color.RED : Color.BLACK);



            }
        });
        timer.start();
        Timer logTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("North Ward Temperature :" + system.getTempFeed().getTemperature(0));
                System.out.println("North Ward Temperature :" + (system.isAirPurifierOn() ? "ON" : "OFF"));

                System.out.println("Central Ward Temperature :" + system.getTempFeed().getTemperature(1) );

                System.out.println("South Ward Temperature :" + system.getTempFeed().getTemperature(2) );

                System.out.println("30 seconds passed");

                System.out.println(system.getCTScannerStatus());
                System.out.println(system.getUltrasoundScannerStatus());
            }
        });
        logTimer.start();

    }
}