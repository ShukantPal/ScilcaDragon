/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.graphics;

import com.scilca.circuit.bridge.ButtonView;
import com.scilca.circuit.bridge.GraphicsDrawer;
import com.scilca.circuit.io.FileCharacterReader;
import com.scilca.circuit.parser.HTMLParser;
import com.scilca.dom.html.HTMLDocument;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Sukant Pal
 */
public final class Browser extends JFrame{
    
    JTabbedPane pagesTransfer;
    
    JButton backButton;
    JButton forwardButton;
    JTextField urlTextField;
    
    HTMLDocument currentRender;
    ArrayList<HTMLDocument> renderedPages;
    
    ArrayList<Container> renderedTabs;
    ArrayList<HTMLDocument> DOMDocs;
    
    void initComponents(Container toLoad) throws FileNotFoundException{
        
        GridBagLayout componentLayout = new GridBagLayout();
        GridBagConstraints componentConstraints = new GridBagConstraints();
        
        toLoad.setLayout(componentLayout);
        
        backButton = new JButton("<");
        forwardButton = new JButton(">");
        urlTextField = new JTextField();
        
        componentConstraints.gridx = GridBagConstraints.RELATIVE;
        componentConstraints.gridy = 0;
        
        componentConstraints.weightx = .01;
        componentConstraints.weighty = .01;
        componentConstraints.anchor = GridBagConstraints.NORTHWEST;
        
        backButton.setMargin(new Insets(4,15,4,15));
        forwardButton.setMargin(new Insets(4,15,4,15));

        urlTextField.setMargin(new Insets(5,15,5,15));
        urlTextField.setText("DEFAULT DEGUG CONFIGURATION: CONTACT VENDOR");
        
        componentLayout.setConstraints(backButton, componentConstraints);
        toLoad.add(backButton);
        
        componentLayout.setConstraints(forwardButton, componentConstraints);
        toLoad.add(forwardButton);
        
        componentConstraints.weightx = 1;
        componentConstraints.fill = GridBagConstraints.HORIZONTAL;
        componentLayout.setConstraints(urlTextField, componentConstraints);
        toLoad.add(urlTextField);
        
        componentConstraints.fill = GridBagConstraints.BOTH;
        componentConstraints.gridy = 1;
        componentConstraints.weighty = 1;
        componentConstraints.gridwidth = 3;
        
        JPanel HTMLRender = new JPanel();
        HTMLRender.setBackground(Color.white);
        
        // <scilcadev testing="true">
        
        HTMLParser defaultParser = new HTMLParser(
                new FileCharacterReader(new File("D:/html.txt"))
        );
        
        HTMLDocument builtDOM = defaultParser.parseDocument();
        DOMDocs.add(builtDOM);
        defaultParser.closeParser();
        
        GraphicsDrawer renderer = new GraphicsDrawer(
                HTMLRender, builtDOM
        );
        
        //</scilcadev>
        
        componentLayout.setConstraints(HTMLRender, componentConstraints);
        toLoad.add(HTMLRender);
    }
    
    void setTitle(int index, HTMLDocument fromDOC){
        if(fromDOC.getHead() == null)
            // TOMORROW
            ;
    }
    
    public Browser() throws FileNotFoundException{
        DOMDocs = new ArrayList<>();
        
        super.setTitle("Circuit Browser");
        super.setSize(
                super.getGraphicsConfiguration().getBounds().width * 2 / 3,
                super.getGraphicsConfiguration().getBounds().height * 2 / 3
        );
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        super.getContentPane().setBackground(new Color(255, 255, 255));
        
        GridLayout containerTab = new GridLayout();
        super.getContentPane().setLayout(containerTab);
        
        pagesTransfer = new JTabbedPane();
        super.getContentPane().add(pagesTransfer);
        
        renderedTabs = new ArrayList<>();
        
        renderedTabs.add(new Container());
        initComponents(renderedTabs.get(0));
        pagesTransfer.insertTab("New Tab - Scilca Circuit", null, renderedTabs.get(0), "New Tab - Scilca Circuit", 0);

        pagesTransfer.setTitleAt(0, 
                (DOMDocs.get(0).getTitle() != null) ? DOMDocs.get(0).getHead().toString() : "NO TITLE");
        
        super.setVisible(true);
    }
    
    public static Browser mainWindow;
    
    public static void main(String[] args) {
        String OS = System.getProperty("os.name");
        
        if(OS == null);
        else if(OS.toLowerCase().contains("windows")){
            try{
               UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getCanonicalName());
            } catch(Exception e){
                // TODO
            }
        }
            
        
        SwingUtilities.invokeLater(
                () -> {
            try {
                mainWindow = new Browser();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        );
    }
    
}
