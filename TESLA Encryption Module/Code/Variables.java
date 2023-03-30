/* @Project: Project Hermes */
/* @File: Variables.java */
/* @Author: SuperPEKKA336 */
/* @Version: 0.1.1 Pre-alpha */
/* @PatchNotes:  */
/* @Updated: 03/30/2023 */

/* ================================================== */

/* Imports */
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;


class Variables
{
  /* Constant Global Variables */

  // Program Information
  protected static final String PROGRAM_NAME = new String("T.E.S.L.A. Encryption Program");
  protected static final String VERSION_NUMBER = new String("Version 0.1.1 Pre-alpha");

  // Device Information
  protected static final File DEFAULT_DIRECTORY = new File(System.getProperty("user.home") + "/Documents");
  protected static final int screenLength = (int)new Dimension(Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
  protected static final int screenWidth = (int)new Dimension(Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
  protected static final Dimension SCREEN = new Dimension(screenLength, screenWidth);

  // Other Variables
  protected static final FileNameExtensionFilter ACK_FILE_FILTER = new FileNameExtensionFilter("Advanced Cipher Key", "ACK");
  protected static final FileNameExtensionFilter AEF_FILE_FILTER = new FileNameExtensionFilter("Advanced Encrypted File", "AEF");
  protected static final FileNameExtensionFilter TXT_FILE_FILTER = new FileNameExtensionFilter("Plain Text", "txt");
  protected static final String DEFAULT_FILE_NAME = new String("TESLA_CipherKey.ACK");
  protected static final String DIVIDER = new String("==================================================");


  /* Global Variables */

  // Window Objects
  protected static JFrame mainFrame = new JFrame(Variables.PROGRAM_NAME + " - " + Variables.VERSION_NUMBER);
  protected static Canvas mainCanvas = new Canvas();

  // Control Booleans
  protected static boolean exit = false;
  protected static boolean keyLoaded = false;

  // Other Variables
  protected static String[][] cipherKey = new String[1281][16];
  protected static JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
}

/* ================================================== */