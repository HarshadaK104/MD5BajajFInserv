package com.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String inputFilePath = "src/main/resources/input.json";
        String outputFilePath = "output.txt";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObject = objectMapper.readTree(new File(inputFilePath));

            String firstName = jsonObject.get("student").get("first_name").asText();
            String rollNumber = jsonObject.get("student").get("roll_number").asText();

            String concatenatedString = firstName + rollNumber;
            String md5HashString = generateMD5Hash(concatenatedString);
            
            Files.write(Paths.get(outputFilePath), md5HashString.getBytes());

            System.out.println("MD5 Hash written to output text file:"+ md5HashString);
            
        } catch (Exception e) {
            System.err.println("Error reading the JSON file"+ e.getMessage());
        }
    }
    private static String generateMD5Hash(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for( byte b: messageDigest){
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 Algorithm not found");
        }
    }
}
