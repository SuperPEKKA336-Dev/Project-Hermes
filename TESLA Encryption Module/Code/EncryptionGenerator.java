/* @File: EncryptionGenerator.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Alpha */
/* @PatchNotes: Added characters 0 - 1278 Dec */
/* @Updated: 03/17/2023 */

// Imports
import java.io.FileWriter;

class EncryptionGenerator
{
  // Constant Global Variables
  public static final String PROGRAM_NAME = TESLA_Encyrption.PROGRAM_NAME;
  public static final String VERSION_NUMBER = TESLA_Encyrption.VERSION_NUMBER;

  private static String[][] encryptionKey = new String[1279][16];

  public EncryptionGenerator()
  {
    
  }
  
  // Creats the encryption key
  public void createKey()
  {
    boolean duplication = false; // Boolean in case there's a duplication of keys
    
    for(int character = 0; character < 1279; character++) // Creates a for loop to create a key for each character
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

    try
    {            
      FileWriter cipherKeyFileWriter = new FileWriter("TESLA_CipherKey.txt");
      
      cipherKeyFileWriter.write(PROGRAM_NAME + " - " + VERSION_NUMBER);
      cipherKeyFileWriter.write("\n");
      cipherKeyFileWriter.write("*Characters without a render will appear as [" + (char)65533 + "]");
      cipherKeyFileWriter.write("\n");
      cipherKeyFileWriter.write("Character" + "\t" + "ASCII Code" + "\t" + "Encryption Key");
      
      for(int character = 0; character < 1279; character++)
      {
        char writeChar = 65533;

        if(Character.isDefined((char)character) && Character.isAlphabetic((char)character) && !Character.isWhitespace((char)character) && !Character.isIdeographic((char)character) && !Character.isIdentifierIgnorable((char)character) && character > 32 && character <= 512)
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
    }    
    catch(Exception e)
    {
      
    }
  }
}