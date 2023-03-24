/* @File: EncryptionGenerator.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Beta */
/* @PatchNotes: Added a filler */
/* @Updated: 03/20/2023 */

// Imports
import java.io.File;
import java.io.FileWriter;

class EncryptionGenerator
{
  // Constant Global Variables
  public static final String PROGRAM_NAME = TESLA_Encryption.PROGRAM_NAME;
  public static final String VERSION_NUMBER = TESLA_Encryption.VERSION_NUMBER;

  private static String[][] encryptionKey = new String[1281][16];

  public EncryptionGenerator()
  {
    
  }
  
  // Creats the encryption key
  public void createKey()
  {
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

      cipherKeyFileWriter.write(PROGRAM_NAME + " - " + VERSION_NUMBER);
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
}