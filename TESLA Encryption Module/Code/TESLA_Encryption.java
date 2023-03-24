/* @File: TESLA_Encryption.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Beta */
/* @PatchNotes: Working on some finishing touches */
/* @Updated: 03/23/2023 */

// Imports
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

class TESLA_Encryption
{
  // Constant Global Variables
  public static final String PROGRAM_NAME = new String("T.E.S.L.A. Encryption Program");
  public static final String VERSION_NUMBER = new String("Version 0.1.1a");
  public static final File DEFAULT_DIRECTORY = new File(System.getProperty("user.home") + "/Documents");
  public static final FileNameExtensionFilter ACK_FILE_FILTER = new FileNameExtensionFilter("Advanced Cipher Key", "ACK");
  public static final FileNameExtensionFilter AEF_FILE_FILTER = new FileNameExtensionFilter("Advanced Encrypted File", "AEF");
  public static final FileNameExtensionFilter TXT_FILE_FILTER = new FileNameExtensionFilter("Plain Text", "txt");

  static final String DEFAULT_FILE_NAME = new String("TESLA_CipherKey.ACK");
  static final String DIVIDER = new String("==================================================");

  // Global Variables
  public static JFrame mainFrame = new JFrame(PROGRAM_NAME + " - " + VERSION_NUMBER);
  public static Canvas mainCanvas = new Canvas();
  public static int screenLength;
  public static int screenWidth;

  static String[][] encryptionKey = new String[1281][16];
  static File encryptionKey_File = new File("");
  static LineNumberReader encryptionKey_FileReader;
  static JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);

  static boolean exit;
  static boolean keyLoaded = false;

  private static Graphics Graphics;
  
  public static void main(String args[]) // Main method
  {
    //frameSetup();

    int menuChoice = menu();
    
    if(menuChoice == 1)
    {
      loadCipherKey();
      
      if(keyLoaded)
        encryption();
    }
    else if(menuChoice == 2)
    {
      loadCipherKey();
      
      if(keyLoaded)
        decryption();
    }
    else if(menuChoice == 3)
    {
      EncryptionGenerator cipherKey = new EncryptionGenerator();
      cipherKey.createKey();

      exit = true;
    }
  }

  private static void frameSetup()
  {
    Dimension screen = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    screenLength = (int)screen.getWidth();
    screenWidth = (int)screen.getHeight();

    mainFrame.setSize(screenLength / 3, screenWidth / 3);
    mainFrame.setLocation(screenLength / 3, screenWidth / 3);
    mainFrame.setMinimumSize(new Dimension(screenLength / 3, screenWidth /3));
    mainFrame.setMaximumSize(screen);

    mainCanvas.setSize(mainFrame.getWidth(), mainFrame.getHeight());
    
    mainFrame.add(mainCanvas);
    mainFrame.pack();

    mainFrame.setVisible(true);
  }
  
  private static int menu() // Menu
  {
    System.out.println("T.E.S.L.A. Encryption Program - " + VERSION_NUMBER);
    System.out.println();
    System.out.println("Enter [`] at any time to exit.");
    System.out.println();
    System.out.println("1 - Encryption");
    System.out.println("2 - Decryption");
    System.out.println("3 - Generate Cipher Key");
    System.out.println();
    
    Scanner menuChoiceScanner = new Scanner(System.in);
    System.out.print("Your Choice: ");
    String menuChoice_String = menuChoiceScanner.nextLine();
    System.out.println();
    
    while(!menuChoice_String.equals("1") && !menuChoice_String.equals("2") && !menuChoice_String.equals("3") && !menuChoice_String.equals("`"))
    {
      System.out.println("[System] Invalid choice.");
      System.out.println();
      menuChoiceScanner = new Scanner(System.in);
      System.out.print("Your Choice: ");
      menuChoice_String = menuChoiceScanner.nextLine();
      System.out.println();
    }
    
    int menuChoice = 0;
    
    if(!menuChoice_String.equals("`"))
      menuChoice = Integer.parseInt(menuChoice_String);    
    else if(menuChoice_String.equals("`"))
      exit = true;
    
    System.out.println(DIVIDER);
    System.out.println();
    
    return menuChoice;
  }
  
  private static void loadCipherKey() // Method to retreive the encryption key from a text file
  {
    // Prompts user to slect file
    System.out.println("[System] Loading cipher key . . . .");
    System.out.println();
    
    while(!exit && !keyLoaded)
    {
      fileChooser.setDialogTitle("Cipher Key");
      fileChooser.addChoosableFileFilter(ACK_FILE_FILTER);
      fileChooser.setFileHidingEnabled(true);
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
    
      if(fileChooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION)
      {
        encryptionKey_File = fileChooser.getSelectedFile();
        
        try
        {
          encryptionKey_FileReader = new LineNumberReader(new FileReader(encryptionKey_File));

          for(int i = 0; i < 9; i++)
            encryptionKey_FileReader.readLine();
          
          for(int i = 0; i < 1281; i++)
          {
            String line = new String(encryptionKey_FileReader.readLine());
            
            String key = new String(line.substring(line.indexOf((char)8203) + 1, line.lastIndexOf((char)8203)));
            
            encryptionKey[i][0] = key.substring(0, key.indexOf((char)8203));
            
            for(int i2 = 1; i2 < 16; i2++)
            {            
              key = key.substring(key.indexOf((char)8203) + 1, key.length());
              
              key = key.substring(key.indexOf((char)8203) + 1, key.length());
              
              if(i2 == 15)
              {
                encryptionKey[i][i2] = key;
              }
              else
              {
                encryptionKey[i][i2] = key.substring(0, key.indexOf((char)8203));
              }
            }
            
            encryptionKey_FileReader.readLine();
          }
          
          keyLoaded = true;
          
          if(keyLoaded)
          {
            encryptionKey_FileReader.close();
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
              exit = true;
            }
          }
          
          keyLoaded = false;
        }
        
        if(keyLoaded)
        {
          System.out.println("[System] Cipher key successfully loaded.");
          System.out.println("[System] Cipher Key: " + encryptionKey_File.getName());
          System.out.println();
          System.out.println(DIVIDER);
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
          exit = true;
        }
      }
    }
  }
  
  private static void encryption()
  {
    File textFile = new File("");
    boolean retry = true;

    while(retry && !exit)
    {
      System.out.println("[System] Loading file . . . .");
      System.out.println();

      // Prompts user to slect file      
      // fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
      fileChooser.setDialogTitle("Text File");
      fileChooser.resetChoosableFileFilters();
      fileChooser.addChoosableFileFilter(TXT_FILE_FILTER);
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);

      if(fileChooser.showDialog(null, "Upload") == JFileChooser.APPROVE_OPTION)
      {
        textFile = fileChooser.getSelectedFile();
        
        System.out.println("[System] File successfully loaded.");
        System.out.println("[System] File: " + textFile.getName());
        System.out.println();

        Scanner encryptChoiceScanner = new Scanner(System.in);
        System.out.print("[System] Encrypt [y/n]: ");
        String encryptChoiceString = encryptChoiceScanner.nextLine();

        if(!encryptChoiceString.equals("y") && !encryptChoiceString.equals("n") && !encryptChoiceString.equals("`"))
        {
          while(!exit)
          {
            System.out.println("[System] Error: Invalid Choice.");
            encryptChoiceScanner = new Scanner(System.in);
            System.out.print("[System] Encrypt [y/n]: ");
            System.out.println();
            
            if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
            {
              exit = true;
              
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
          exit = true;
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
                
                encryptedFileWriter.write(encryptionKey[character][randNum]);

                if((int)Math.floor(Math.random() * (10 - 0 + 1) + 0) == 1)
                  encryptedFileWriter.write(encryptionKey[1280][(int)Math.floor(Math.random() * (16 - 0 + 1) + 0)]);
                
                character = fileReader.read();
              }

              retry = false;

              System.out.println("[System] File successfully encrypted.");
              System.out.println("[System] File: " + encryptedFile.getName());
              System.out.println();
              System.out.println(DIVIDER);

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
                while(!exit)
                {
                  System.out.println("[System] Error: Invalid Choice.");
                  choiceScanner = new Scanner(System.in);
                  System.out.print("[System] Retry [y/n]: ");
                  choiceString = choiceScanner.nextLine();
                  System.out.println();
                  
                  if(choiceString.equals("n") || choiceString.equals("`"))
                  {
                    exit = true;
                    
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
              while(!exit)
              {
                System.out.println("[System] Error: Invalid Choice.");
                choiceScanner = new Scanner(System.in);
                System.out.print("[System] Retry [y/n]: ");
                choiceString = choiceScanner.nextLine();
                System.out.println();
                
                if(choiceString.equals("n") || choiceString.equals("`"))
                {
                  exit = true;
                  
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
  
  private static void decryption()
  {
    File encryptedFile = new File("");
    boolean retry = true;

    while(retry && !exit)
    {
      System.out.println("[System] Loading file . . . .");
      System.out.println();

      // Prompts user to slect file      
      // fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
      fileChooser.setDialogTitle("Advanced Encrypted File");
      fileChooser.resetChoosableFileFilters();
      fileChooser.addChoosableFileFilter(AEF_FILE_FILTER);
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);

      if(fileChooser.showDialog(null, "Upload") == JFileChooser.APPROVE_OPTION)
      {
        encryptedFile = fileChooser.getSelectedFile();
        
        System.out.println("[System] File successfully loaded.");
        System.out.println("[System] File: " + encryptedFile.getName());
        System.out.println();

        Scanner encryptChoiceScanner = new Scanner(System.in);
        System.out.print("[System] Decrypt [y/n]: ");
        String encryptChoiceString = encryptChoiceScanner.nextLine();

        if(!encryptChoiceString.equals("y") && !encryptChoiceString.equals("n") && !encryptChoiceString.equals("`"))
        {
          while(!exit)
          {
            System.out.println("[System] Error: Invalid Choice.");
            encryptChoiceScanner = new Scanner(System.in);
            System.out.print("[System] Encrypt [y/n]: ");
            System.out.println();
            
            if(encryptChoiceString.equals("n") || encryptChoiceString.equals("`"))
            {
              exit = true;
              
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
          exit = true;
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
                    if(encryptedCharacter.equals(encryptionKey[i][i2]) && !encryptedCharacter.equals(encryptionKey[1280][i2]))
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
              System.out.println(DIVIDER);

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
                while(!exit)
                {
                  System.out.println("[System] Error: Invalid Choice.");
                  choiceScanner = new Scanner(System.in);
                  System.out.print("[System] Retry [y/n]: ");
                  choiceString = choiceScanner.nextLine();
                  System.out.println();
                  
                  if(choiceString.equals("n") || choiceString.equals("`"))
                  {
                    exit = true;
                    
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
              while(!exit)
              {
                System.out.println("[System] Error: Invalid Choice.");
                choiceScanner = new Scanner(System.in);
                System.out.print("[System] Retry [y/n]: ");
                choiceString = choiceScanner.nextLine();
                System.out.println();
                
                if(choiceString.equals("n") || choiceString.equals("`"))
                {
                  exit = true;
                  
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