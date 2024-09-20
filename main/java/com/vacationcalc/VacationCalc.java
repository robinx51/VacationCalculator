package com.vacationcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VacationCalc { 
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        ConfigurableApplicationContext  ctx = SpringApplication.run(VacationCalc.class, args);
        System.out.println("App started! To close the app, write \"stop\"");
        
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in,"Cp1251"));
        
        OUTER:
        while (true) {
            String str = console.readLine();
            switch (str) {
                case "stop", "стоп" -> {
                    ctx.close();
                    break OUTER;
                }
            }
        }
        
    }
}
