/* @File: EncryptionGenerator.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Alpha */
/* @PatchNotes: Changed it to 16 different codes for each character */
/* @Updated: 02/24/2023 */

// Imports
import java.io.FileWriter;

class EncryptionGenerator
{
  private static String[][] encryptionKey = new String[256][16];
  
  public EncryptionGenerator()
  {
    
  }
  
  // Creats the encryption key
  public static void createKey()
  {
    boolean duplication = false;
    
    for(int i = 32; i < 256; i++)
    {
      if(i < 127 || i > 160)
      {
        for(int i2 = 0; i2 < 16; i2++)
        {
          int characterNumber = 0;
          
          encryptionKey[i][i2] = "";
          
          while(characterNumber < 16)
          {
            int randNum = 0;
            
            while((randNum > 126 && randNum < 160) || randNum == 0)
            {
              randNum = ((int)Math.floor(Math.random() * (255 - 32 + 1) + 32));
            }
            
            encryptionKey[i][i2] = encryptionKey[i][i2] + (char)randNum;
            
            characterNumber++;
          }
          
          for(int i3 = 32; i3 < i + 1; i3++)
          {
            for(int i4 = 0; i4 < 16; i4++)
            {
              if(encryptionKey[i][i2].equals(encryptionKey[i3][i4]) && i != i3 && i2 != i4 && !encryptionKey[i][i2].equals(""))
              {
                duplication = true;
                
                i2--;
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
          
          encryptionKey[i][i2] = (char)8203 + encryptionKey[i][i2] + (char)8203;
          
          try
          {            
            FileWriter cipherKeyFileWriter = new FileWriter("TESLA_CipherKey.txt");
            
            cipherKeyFileWriter.write("T.E.S.L.A. Encryption Key - Version 1.0");
            cipherKeyFileWriter.write("\n");
            cipherKeyFileWriter.write("\n");
            cipherKeyFileWriter.write("Character" + "\t" + "ASCII Code" + "\t" + "Encryption Key");
            
            for(int i5 = 32; i5 < 256; i5++)
            {
              char character = (char)i5;
              
              if(i5 < 100)
              {
                cipherKeyFileWriter.write("\n" + "\n" + character + "        " + "\t" + "0" + i5 + "       " + "\t");
                
                for(int i6 = 0; i6 < 16; i6++)
                {
                  cipherKeyFileWriter.write(encryptionKey[i5][i6] + "\t");
                }
              }
              
              else if(i5 > 126 && i5 < 161)
              {
                
              }
              
              else
              {
                cipherKeyFileWriter.write("\n" + "\n" + character + "        " + "\t" + i5 + "       " + "\t");
                
                for(int i6 = 0; i6 < 16; i6++)
                {
                  cipherKeyFileWriter.write(encryptionKey[i5][i6] + "\t");
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
    }
  }
}