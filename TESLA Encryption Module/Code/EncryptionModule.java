/* @Project: Project Hermes */
/* @File: Window.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Pre-alpha */
/* @PatchNotes:  */
/* @Updated: 03/30/2023 */

/* ================================================== */

/* Imports */
import java.awt.*;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Frame;
import java.awt.Window;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.LineNumberReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.util.*;

class EncryptionModule
{
  public static void generateKey()
  {
    String[][] encryptionKey = new String[1281][16];
    boolean duplication = false; // Boolean in case there's a duplication of keys
    
    for(int character = 0; character < 1281; character++) // Creates a for loop to create a key for each character
    {
      for(int keyNumber = 0; keyNumber < 16; keyNumber++) // Creates a for loop to create 16 keys for each character
      {
        int characterNumber = 0;

        encryptionKey[character][keyNumber] = "";
        
        while(characterNumber < 16) // Creates a while loop to create each of the 16 character long keys
        {
          int randNum = 0; // Random Number Variable
          boolean validChar = false;
          
          while(!validChar)
          {
            randNum = ((int)Math.floor(Math.random() * (1279 - 0 + 1) + 0));

            if(Character.isDefined((char)randNum) && Character.isAlphabetic((char)randNum) && !Character.isWhitespace((char)randNum) && !Character.isIdeographic((char)randNum) && !Character.isIdentifierIgnorable((char)randNum) && randNum > 32 && randNum <= 512)
            {
              validChar = true;
            }
          }
                    
          encryptionKey[character][keyNumber] = encryptionKey[character][keyNumber] + (char)randNum;
          
          characterNumber++;
        }
        
        for(int i3 = 32; i3 < character + 1; i3++)
        {
          for(int i4 = 0; i4 < 16; i4++)
          {
            if(encryptionKey[character][keyNumber].equals(encryptionKey[i3][i4]) && character != i3 && keyNumber != i4 && !encryptionKey[character][keyNumber].equals(""))
            {
              duplication = true;
              
              keyNumber--;
            }
            
            if(duplication)
            {
              break;
            }
          }
          
          if(duplication)
          {
            break;
          }
        }

        if(!duplication)
        {
          encryptionKey[character][keyNumber] = (char)8203 + encryptionKey[character][keyNumber] + (char)8203;
        }
      }
    }

    String cipherKeyFileString = new String();

    for(int i = 0; i < 16; i++)
    {
      int randNum = 0; // Random Number Variable
      boolean validChar = false;
      
      while(!validChar)
      {
        randNum = ((int)Math.floor(Math.random() * (128 - 0 + 1) + 0));

        if((randNum >= 48 && randNum <= 57) || (randNum >= 65 && randNum <= 90) || (randNum >= 97 && randNum <= 122))
        {
          validChar = true;
        }
      }

      cipherKeyFileString = cipherKeyFileString + (char)randNum;
    }

    cipherKeyFileString = cipherKeyFileString + ".ACK";

    File cipherKeyFile = new File(cipherKeyFileString);
    cipherKeyFile.setWritable(true);
    
    try
    {
      FileWriter cipherKeyFileWriter = new FileWriter(cipherKeyFile);

      cipherKeyFileWriter.write(Variables.PROGRAM_NAME + " - " + Variables.VERSION_NUMBER);
      cipherKeyFileWriter.write("\n" + "\n");
      cipherKeyFileWriter.write("File: " + cipherKeyFile.getName());
      cipherKeyFileWriter.write("\n" + "\n");
      cipherKeyFileWriter.write("*Characters without a render will appear as [" + (char)65533 + "]");
      cipherKeyFileWriter.write("\n" + "\n" + "\n");
      cipherKeyFileWriter.write("Character" + "\t" + "ASCII Code" + "\t" + "Encryption Key");
      
      for(int character = 0; character < 1281; character++)
      {
        char writeChar = 65533;

        if(Character.isDefined((char)character) && Character.isAlphabetic((char)character) && !Character.isWhitespace((char)character) && !Character.isIdeographic((char)character) && !Character.isIdentifierIgnorable((char)character) && character > 32 && character <= 512 && character != 1280)
        {
          writeChar = (char)character;
        }

        if(character < 10)
        {
          cipherKeyFileWriter.write("\n" + "\n" + writeChar + "        " + "\t" + "000" + character + "       " + "\t");
          
          for(int keyNumber = 0; keyNumber < 16; keyNumber++)
          {
            cipherKeyFileWriter.write(encryptionKey[character][keyNumber] + "\t");
          }
        }
        else if(character < 100)
        {
          cipherKeyFileWriter.write("\n" + "\n" + writeChar + "        " + "\t" + "00" + character + "       " + "\t");
          
          for(int keyNumber = 0; keyNumber < 16; keyNumber++)
          {
            cipherKeyFileWriter.write(encryptionKey[character][keyNumber] + "\t");
          }
        }
        else if(character < 1000)
        {
          cipherKeyFileWriter.write("\n" + "\n" + writeChar + "        " + "\t" + "0" + character + "       " + "\t");
          
          for(int keyNumber = 0; keyNumber < 16; keyNumber++)
          {
            cipherKeyFileWriter.write(encryptionKey[character][keyNumber] + "\t");
          }
        }
        else if(character == 1280)
        {
          cipherKeyFileWriter.write("\n" + "\n" + writeChar + "        " + "\t" + "Fill" + "       " + "\t");
          
          for(int keyNumber = 0; keyNumber < 16; keyNumber++)
          {
            cipherKeyFileWriter.write(encryptionKey[character][keyNumber] + "\t");
          }
        }
        else
        {
          cipherKeyFileWriter.write("\n" + "\n" + writeChar + "        " + "\t" + character + "       " + "\t");
          
          for(int keyNumber = 0; keyNumber < 16; keyNumber++)
          {
            cipherKeyFileWriter.write(encryptionKey[character][keyNumber] + "\t");
          }
        }
      }

      cipherKeyFileWriter.close();
      cipherKeyFile.setReadOnly();
    }    
    catch(Exception e)
    {
      if(cipherKeyFile.exists())
        cipherKeyFile.delete();

      System.out.println("[System] An error occured.");
    }
  }

  protected static void loadCipherKey()
  {
    File cipherKeyFile = new File("");
    LineNumberReader cipherKeyFileReader;

    // Prompts user to slect file
    System.out.println("[System] Loading cipher key . . . .");
    System.out.println();
    
    while(!Variables.exit && !Variables.keyLoaded)
    {
      Variables.fileChooser.setDialogTitle("Cipher Key");
      Variables.fileChooser.addChoosableFileFilter(Variables.ACK_FILE_FILTER);
      Variables.fileChooser.setFileHidingEnabled(true);
      Variables.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      Variables.fileChooser.setAcceptAllFileFilterUsed(false);
    
      if(Variables.fileChooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION)
      {
        cipherKeyFile = Variables.fileChooser.getSelectedFile();
        
        try
        {
          cipherKeyFileReader = new LineNumberReader(new FileReader(cipherKeyFile));

          for(int i = 0; i < 9; i++)
            cipherKeyFileReader.readLine();
          
          for(int i = 0; i < 1281; i++)
          {
            String line = new String(cipherKeyFileReader.readLine());
            
            String key = new String(line.substring(line.indexOf((char)8203) + 1, line.lastIndexOf((char)8203)));
            
            Variables.cipherKey[i][0] = key.substring(0, key.indexOf((char)8203));
            
            for(int i2 = 1; i2 < 16; i2++)
            {            
              key = key.substring(key.indexOf((char)8203) + 1, key.length());
              
              key = key.substring(key.indexOf((char)8203) + 1, key.length());
              
              if(i2 == 15)
              {
                Variables.cipherKey[i][i2] = key;
              }
              else
              {
                Variables.cipherKey[i][i2] = key.substring(0, key.indexOf((char)8203));
              }
            }
            
            cipherKeyFileReader.readLine();
          }
          
          Variables.keyLoaded = true;
          
          if(Variables.keyLoaded)
          {
            cipherKeyFileReader.close();
          }
        }
        
        catch(Exception e)
        {
          System.out.println(e);
          
          String choiceString = new String("");
          
          while(choiceString.equals("n") || choiceString.equals("`") || choiceString.equals(""))
          {
            System.out.println("[System] Error: Cipher key is unreadable or corrupted.");
            System.out.println();
            Scanner choiceScanner = new Scanner(System.in);
            System.out.print("[System] Retry [y/n]: ");
            choiceString = choiceScanner.nextLine();
            System.out.println();
            
            if(choiceString.equals("n") || choiceString.equals("`"))
            {
              Variables.exit = true;
            }
          }
          
          Variables.keyLoaded = false;
        }
        
        if(Variables.keyLoaded)
        {
          System.out.println("[System] Cipher key successfully loaded.");
          System.out.println("[System] Cipher Key: " + cipherKeyFile.getName());
          System.out.println();
          System.out.println(Variables.DIVIDER);
          System.out.println();
        }
      }
      else
      {
        System.out.println("[System] Error: File retreival unsuccesful.");
        System.out.println();
        Scanner choiceScanner = new Scanner(System.in);
        System.out.print("[System] Retry [y/n]: ");
        String choiceString = choiceScanner.nextLine();
        System.out.println();
        
        if(choiceString.equals("n") || choiceString.equals("`"))
        {
          Variables.exit = true;
        }
      }
    }
  }

  public static void encryption()
  {
    File textFile = new File("");
    boolean retry = true;

    while(retry && !Variables.exit)
    {
      System.out.println("[System] Loading file . . . .");
      System.out.println();

      // Prompts user to slect file      
      // fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
      Variables.fileChooser.setDialogTitle("Text File");
      Variables.fileChooser.resetChoosableFileFilters();
      Variables.fileChooser.addChoosableFileFilter(Variables.TXT_FILE_FILTER);
      Variables.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      Variables.fileChooser.setAcceptAllFileFilterUsed(false);

      if(Variables.fileChooser.showDialog(null, "Upload") == JFileChooser.APPROVE_OPTION)
      {
        textFile = Variables.fileChooser.getSelectedFile();
        
        System.out.println("[System] File successfully loaded.");
        System.out.println("[System] File: " + textFile.getName());
        System.out.println();

        Scanner encryptChoiceScanner = new Scanner(System.in);
        System.out.print("[System] Encrypt [y/n]: ");
        String encryptChoiceString = encryptChoiceScanner.nextLine();

        if(!encryptChoiceString.equals("y") && !encryptChoiceString.equals("n") && !encryptChoiceString.equals("`"))
        {
          while(!Variables.exit)
          {
            System.out.println("[System] Error: Invalid Choice.");
            encryptChoiceScanner = new Scanner(System.in);
            System.out.print("[System] Encrypt [y/n]: ");
            System.out.println();
            
            if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
            {
              Variables.exit = true;
              
              break;
            }
            else if(encryptChoiceString.equals("y"))
            {
              break;
            }
          }
        }
        else if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
        {
          Variables.exit = true;
        }
        
        if(encryptChoiceString.equals("y"))
        {
          String encryptedFileName = new String();

          for(int i = 0; i < 16; i++)
          {
            int randNum = 0; // Random Number Variable
            boolean validChar = false;
            
            while(!validChar)
            {
              randNum = ((int)Math.floor(Math.random() * (128 - 0 + 1) + 0));

              if((randNum >= 48 && randNum <= 57) || (randNum >= 65 && randNum <= 90) || (randNum >= 97 && randNum <= 122))
              {
                validChar = true;
              }
            }

            encryptedFileName = encryptedFileName + (char)randNum;
          }

          try
          {
            File encryptedFile = new File(System.getProperty("user.home") + "/Documents/" + encryptedFileName + ".AEF");
            FileWriter encryptedFileWriter = new FileWriter(encryptedFile);
            BufferedReader fileReader = new BufferedReader(new FileReader(textFile));

            try
            {
              System.out.println("[System] Encrypting file . . . .");
              System.out.println();

              int character = fileReader.read();

              while(character != -1)
              {
                int randNum = (int)Math.floor(Math.random() * (15 - 0 + 1) + 0);
                
                encryptedFileWriter.write(Variables.cipherKey[character][randNum]);

                if((int)Math.floor(Math.random() * (10 - 0 + 1) + 0) == 1)
                  encryptedFileWriter.write(Variables.cipherKey[1280][(int)Math.floor(Math.random() * (16 - 0 + 1) + 0)]);
                
                character = fileReader.read();
              }

              retry = false;

              System.out.println("[System] File successfully encrypted.");
              System.out.println("[System] File: " + encryptedFile.getName());
              System.out.println();
              System.out.println(Variables.DIVIDER);

              encryptedFileWriter.close();
              fileReader.close();
            }
            catch(Exception e)
            {
              Scanner choiceScanner = new Scanner(System.in);
              System.out.print("[System] Retry [y/n]: ");
              String choiceString = choiceScanner.nextLine();
              System.out.println();
              
              if(!choiceString.equals("n") || !choiceString.equals("`") || !choiceString.equals("`"))
              {
                while(!Variables.exit)
                {
                  System.out.println("[System] Error: Invalid Choice.");
                  choiceScanner = new Scanner(System.in);
                  System.out.print("[System] Retry [y/n]: ");
                  choiceString = choiceScanner.nextLine();
                  System.out.println();
                  
                  if(choiceString.equals("n") || choiceString.equals("`"))
                  {
                    Variables.exit = true;
                    
                    break;
                  }
                  else if(choiceString.equals("y"))
                  {
                    retry = true;
                    
                    break;
                  }
                }
              }
            }
          }
          catch(Exception e)
          {
            Scanner choiceScanner = new Scanner(System.in);
            System.out.print("[System] Retry [y/n]: ");
            String choiceString = choiceScanner.nextLine();
            System.out.println();
            
            if(!choiceString.equals("n") || !choiceString.equals("`") || !choiceString.equals("`"))
            {
              while(!Variables.exit)
              {
                System.out.println("[System] Error: Invalid Choice.");
                choiceScanner = new Scanner(System.in);
                System.out.print("[System] Retry [y/n]: ");
                choiceString = choiceScanner.nextLine();
                System.out.println();
                
                if(choiceString.equals("n") || choiceString.equals("`"))
                {
                  Variables.exit = true;
                  
                  break;
                }
                else if(choiceString.equals("y"))
                {
                  retry = true;
                  
                  break;
                }
              }
            }
          }
        }        
      }
    }
  }

  public static void decryption()
  {
    File encryptedFile = new File("");
    boolean retry = true;

    while(retry && !Variables.exit)
    {
      System.out.println("[System] Loading file . . . .");
      System.out.println();

      // Prompts user to slect file      
      // fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
      Variables.fileChooser.setDialogTitle("Advanced Encrypted File");
      Variables.fileChooser.resetChoosableFileFilters();
      Variables.fileChooser.addChoosableFileFilter(Variables.AEF_FILE_FILTER);
      Variables.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      Variables.fileChooser.setAcceptAllFileFilterUsed(false);

      if(Variables.fileChooser.showDialog(null, "Upload") == JFileChooser.APPROVE_OPTION)
      {
        encryptedFile = Variables.fileChooser.getSelectedFile();
        
        System.out.println("[System] File successfully loaded.");
        System.out.println("[System] File: " + encryptedFile.getName());
        System.out.println();

        Scanner encryptChoiceScanner = new Scanner(System.in);
        System.out.print("[System] Decrypt [y/n]: ");
        String encryptChoiceString = encryptChoiceScanner.nextLine();

        if(!encryptChoiceString.equals("y") && !encryptChoiceString.equals("n") && !encryptChoiceString.equals("`"))
        {
          while(!Variables.exit)
          {
            System.out.println("[System] Error: Invalid Choice.");
            encryptChoiceScanner = new Scanner(System.in);
            System.out.print("[System] Encrypt [y/n]: ");
            System.out.println();
            
            if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
            {
              Variables.exit = true;
              
              break;
            }
            else if(encryptChoiceString.equals("y"))
            {
              break;
            }
          }
        }
        else if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
        {
          Variables.exit = true;
        }
        
        if(encryptChoiceString.equals("y"))
        {
          String decryptedFileName = new String();

          for(int i = 0; i < 16; i++)
          {
            int randNum = 0; // Random Number Variable
            boolean validChar = false;
            
            while(!validChar)
            {
              randNum = ((int)Math.floor(Math.random() * (128 - 0 + 1) + 0));

              if((randNum >= 48 && randNum <= 57) || (randNum >= 65 && randNum <= 90) || (randNum >= 97 && randNum <= 122))
              {
                validChar = true;
              }
            }

            decryptedFileName = decryptedFileName + (char)randNum;
          }

          try
          {
            File decryptedFile = new File(System.getProperty("user.home") + "/Documents/" + decryptedFileName + ".txt");
            FileWriter decryptedFileWritrer = new FileWriter(decryptedFile);
            FileReader encryptedFileReader = new FileReader(encryptedFile);

            try
            {
              System.out.println("[System] Decrypting file . . . .");
              System.out.println();

              int character = 0;
              boolean characterFound = false;

              while(character != -1)
              {
                String encryptedCharacter = new String();

                for(int i = 0; i < 16; i++)
                {
                  character = encryptedFileReader.read();

                  encryptedCharacter = encryptedCharacter + (char)character;

                  if(character == -1)
                    break;
                }

                for(int i = 0; i < 1281; i++)
                {
                  characterFound = false;

                  if(character == -1)
                    break;

                  for(int i2 = 0; i2 < 16; i2++)
                  {
                    if(encryptedCharacter.equals(Variables.cipherKey[i][i2]) && !encryptedCharacter.equals(Variables.cipherKey[1280][i2]))
                    {
                      decryptedFileWritrer.write((char)i);

                      characterFound = true;

                      break;
                    }
                  }

                  if(characterFound)
                    break;
                }
              }

              retry = false;

              System.out.println("[System] File successfully encrypted.");
              System.out.println("[System] File: " + decryptedFile.getName());
              System.out.println();
              System.out.println(Variables.DIVIDER);

              decryptedFileWritrer.close();
              encryptedFileReader.close();
            }
            catch(Exception e)
            {
              Scanner choiceScanner = new Scanner(System.in);
              System.out.print("[System] Retry [y/n]: ");
              String choiceString = choiceScanner.nextLine();
              System.out.println();
              
              if(!choiceString.equals("n") || !choiceString.equals("`") || !choiceString.equals("`"))
              {
                while(!Variables.exit)
                {
                  System.out.println("[System] Error: Invalid Choice.");
                  choiceScanner = new Scanner(System.in);
                  System.out.print("[System] Retry [y/n]: ");
                  choiceString = choiceScanner.nextLine();
                  System.out.println();
                  
                  if(choiceString.equals("n") || choiceString.equals("`"))
                  {
                    Variables.exit = true;
                    
                    break;
                  }
                  else if(choiceString.equals("y"))
                  {
                    retry = true;
                    
                    break;
                  }
                }
              }
            }
          }
          catch(Exception e)
          {
            Scanner choiceScanner = new Scanner(System.in);
            System.out.print("[System] Retry [y/n]: ");
            String choiceString = choiceScanner.nextLine();
            System.out.println();
            
            if(!choiceString.equals("n") || !choiceString.equals("`") || !choiceString.equals("`"))
            {
              while(!Variables.exit)
              {
                System.out.println("[System] Error: Invalid Choice.");
                choiceScanner = new Scanner(System.in);
                System.out.print("[System] Retry [y/n]: ");
                choiceString = choiceScanner.nextLine();
                System.out.println();
                
                if(choiceString.equals("n") || choiceString.equals("`"))
                {
                  Variables.exit = true;
                  
                  break;
                }
                else if(choiceString.equals("y"))
                {
                  retry = true;
                  
                  break;
                }
              }
            }
          }
        }        
      }
    }
  }
}

/* ================================================== */