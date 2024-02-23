package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Enter input File path");
        String inputFilePath=sc.nextLine();
        System.out.println("Enter output File path:");
        String outputFilePath=sc.nextLine();
        String fileExtension=getFileExtension(inputFilePath);
        if(fileExtension.equals("json")){
            AddEligibilityField(inputFilePath,outputFilePath);
        }else{
            ProcessData(inputFilePath,outputFilePath);}

    }
    private static String getFileExtension(String filePath){
        return  filePath.substring(filePath.lastIndexOf(".")+1);
    }
    private static void ProcessData(String inputfile,String outputfile){
        try {
            FileReader fr = new FileReader(inputfile);
            BufferedReader reader = new BufferedReader(fr);
            FileWriter fw=new FileWriter(outputfile);
            BufferedWriter writer=new BufferedWriter(fw);
            String line=reader.readLine();
            writer.write(line);
            writer.newLine();
            String[] words=line.split(",");
            while((line=reader.readLine())!=null){
                words=line.split(",");
                if(words.length>=4){
                    String name=words[0];
                    int age=Integer.parseInt(words[1]);
                    age+=5;
                    writer.write(name+","+age+","+words[2]+","+words[3]);
                    writer.newLine();
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }
    private static void AddEligibilityField(String input,String output){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(input));

            // Iterate over each person in the JSON array
            for (JsonNode personNode : rootNode) {
                int age = personNode.get("age").asInt();
                String eligibility = age > 18 ? "eligible" : "not eligible";

                // Add "eligibleornot" field to the person object
                ((ObjectNode) personNode).put("eligibleornot", eligibility);
            }

            // Write modified JSON to output file
            mapper.writeValue(new File(output), rootNode);

            System.out.println("Modified JSON data with eligibility field added to output.json successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
