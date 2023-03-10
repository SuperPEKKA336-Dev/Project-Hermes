/* @File: TESLA_Encryption.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Alpha */
/* @PatchNotes: Encryption method is finished; working on decryption next */
/* @Updated: 03/10/2023 */

// Imports
import java.io.*;
import java.util.*;
import java.io.LineNumberReader;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.BufferedReader;

class TESLA_Encyrption
{
  // Constant Global Variables
  static final String VERSION_NUMBER = new String("Version 0.1.1");
  static final String DEFAULT_FILE_NAME = new String("TESLA_CipherKey.txt");
  static final String DIVIDER = new String("==================================================");
  
  // Global Variables
  static File encryptionKey_File = new File("");
  static String[][] encryptionKey = new String[256][16];
  static LineNumberReader encryptionKey_FileReader;
  static boolean exit;
  static boolean keyLoaded;
  
  public static void main(String args[]) // Main method
  {
    int menuChoice = menu();
    
    while(!exit)
    {
      if(menuChoice == 1)
      {
        loadCipherKey();
        
        if(keyLoaded)
        {
          encryption();
        }
      }
      else if(menuChoice == 2)
      {
        loadCipherKey();
        
        if(keyLoaded)
        {
          decryption();
        }
      }
      else if(menuChoice == 3)
      {
        
      }
    }
  }
  
  public static int menu() // Menu
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
    {
      menuChoice = Integer.parseInt(menuChoice_String);
    }
    
    else if(menuChoice_String.equals("`"))
    {
      exit = true;
    }
    
    System.out.println(DIVIDER);
    System.out.println();
    
    return menuChoice;
  }
  
  public static void loadCipherKey() // Method to retreive the encryption key from a text file
  {
    // Prompts user to slect file
    System.out.println("[System] Loading cipher key . . . .");

    JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home") + "/Documents"));
    FileNameExtensionFilter fileType = new FileNameExtensionFilter("Text Files", "txt");
    
    fileChooser.setDialogTitle("Cipher Key");
    fileChooser.addChoosableFileFilter(fileType);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    
    if(fileChooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION)
    {
      encryptionKey_File = fileChooser.getSelectedFile();
      
      try
      {
        encryptionKey_FileReader = new LineNumberReader(new FileReader(encryptionKey_File));

        for(int i = 0; i < 4; i++)
        {
          encryptionKey_FileReader.readLine();
        }
        
        for(int i = 32; i < 256; i++)
        {
          if((i < 127 || i > 160) && i < 256)
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
        System.out.println("[System] Cipher Key: " + encryptionKey_File.getAbsolutePath());
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println();
      }
    }
  }
  
  public static void encryption()
  {
    File textFile = new File("");
    boolean retry = true;

    while(retry && !exit)
    {
      System.out.println("[System] Loading file . . . .");

      // Prompts user to slect file
      JFileChooser textFileChooser = new JFileChooser(new File(System.getProperty("user.home") + "/Documents"));
      FileNameExtensionFilter textFileType = new FileNameExtensionFilter("Text Files", "txt");
      
      textFileChooser.setDialogTitle("Text File");
      textFileChooser.addChoosableFileFilter(textFileType);
      textFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      textFileChooser.setAcceptAllFileFilterUsed(false);

      if(textFileChooser.showDialog(null, "Upload") == JFileChooser.APPROVE_OPTION)
      {
        textFile = textFileChooser.getSelectedFile();
        
        System.out.println("[System] File successfully loaded.");
        System.out.println("[System] File: " + textFile.getAbsolutePath());
        System.out.println();

        Scanner encryptChoiceScanner = new Scanner(System.in);
        System.out.print("[System] Encrypt [y/n]: ");
        String encryptChoiceString = encryptChoiceScanner.nextLine();
                
        try
        {
          if(!encryptChoiceString.equals("y") || !encryptChoiceString.equals("n") || !encryptChoiceString.equals("`"))
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
            File encryptedFile = new File(System.getProperty("user.home") + "/Documents" + "/encrypted." + textFile.getName());
            FileWriter encryptedFileWriter = new FileWriter(encryptedFile);
            BufferedReader fileReader = new BufferedReader(new FileReader(textFile));

            try
            {
              int character = fileReader.read();
              
              while(character != -1)
              {
                int randNum = (int)Math.floor(Math.random() * (15 - 0 + 1) + 0);
                
                encryptedFileWriter.write(encryptionKey[character][randNum]);
                
                character = fileReader.read();
              }

              retry = false;

              System.out.println("[System] File successfully encrypted.");
              System.out.println("[System] File: " + encryptedFile);
              System.out.println();
              System.out.println(DIVIDER);
            }
            catch(Exception e)
            {
              System.out.println(e);
            }
            finally
            {
              encryptedFileWriter.close();
              fileReader.close();
            }
          }
        }
        
        catch(Exception e)
        {
          System.out.println(e);
          
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
  
  public static void decryption()
  {
    
  }
}