package com.blm.taskme;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Main{
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
    }
}