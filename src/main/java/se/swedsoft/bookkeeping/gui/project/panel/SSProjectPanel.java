/*
 * @(#)SSNewProjectPanel.java                v 1.0 2005-aug-19
 *
 * Time-stamp: <2005-aug-19 20:14:20 Hasse>
 *
 * Copyright (c) Trade Extensions TradeExt AB, Sweden.
 * www.tradeextensions.com, info@tradeextensions.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Trade Extensions ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Trade Extensions.
 */
package se.swedsoft.bookkeeping.gui.project.panel;

import se.swedsoft.bookkeeping.data.SSNewProject;
import se.swedsoft.bookkeeping.gui.util.datechooser.SSDateChooser;

import javax.swing.*;
import java.util.Date;
import java.awt.event.*;

/**
 * @author
 */
public class SSProjectPanel{

    private SSNewProject iProject;

    protected JPanel iPanel;

    private JButton iOkButton;

    private JButton iCancelButton;


    protected JTextArea iDescription;

    protected JTextField iName;

    protected JFormattedTextField iNumber;


    protected JCheckBox iConcluded;

    protected SSDateChooser iConcludedDate;

    /**
     * Default constructor. <P>
     */
    public SSProjectPanel(boolean iEdit) {
        iNumber.setEnabled(!iEdit);
        iNumber.setValue("");

        iConcluded.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                iConcludedDate.setEnabled( iConcluded.isSelected() );
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(iNumber.isEnabled())
                    iNumber.requestFocusInWindow();
                else
                    iName.requestFocusInWindow();

            }
        });

        iNumber.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iName.requestFocusInWindow();
                        }
                    });
                }
            }
        });

        iName.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iDescription.requestFocusInWindow();
                        }
                    });
                }
            }
        });

        iDescription.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iConcluded.requestFocusInWindow();
                        }
                    });
                }
            }
        });

        iConcluded.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            if(iConcluded.isSelected())
                                iConcludedDate.getEditor().getComponent(0).requestFocusInWindow();
                            else
                                iOkButton.requestFocusInWindow();
                        }
                    });
                }
            }
        });

        iConcludedDate.getEditor().getComponent(0).addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iOkButton.requestFocusInWindow();
                        }
                    });
                }
            }
        });

        iOkButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iCancelButton.requestFocusInWindow();
                        }
                    });
                }
                else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            for(ActionListener al : iOkButton.getActionListeners()){
                                al.actionPerformed(null);
                            }
                        }
                    });
                }
            }
        });

        iCancelButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            iOkButton.requestFocusInWindow();
                        }
                    });
                }
                else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            for(ActionListener al : iCancelButton.getActionListeners()){
                                al.actionPerformed(null);
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     *
     * @return
     */
    public JPanel getPanel() {
        return iPanel;
    }


    /**
     *
     * @param pActionListener
     */

    public void addOkAction(ActionListener pActionListener) {
        iOkButton.addActionListener(pActionListener);
    }

    /**
     *
     * @param pActionListener
     */
    public void addCancelAction(ActionListener pActionListener) {
        iCancelButton.addActionListener(pActionListener);
    }

    /**
     *
     * @param iProject
     */
    public void setProject(SSNewProject iProject) {
        this.iProject = iProject;

        iNumber       .setValue   (iProject.getNumber());
        iName         .setText    (iProject.getName());
        iDescription  .setText    (iProject.getDescription());
        iConcluded    .setSelected(iProject.getConcluded() );
        iConcludedDate.setDate    (iProject.getConcludedDate() != null ? iProject.getConcludedDate() : new Date() );
    }

    /**
     *
     * @return
     */
    public SSNewProject getProject() {
        iProject.setNumber       (iNumber.getText());
        iProject.setName         (iName.getText());
        iProject.setDescription  (iDescription.getText());
        iProject.setConcluded    (iConcluded.isSelected() );
        iProject.setConcludedDate(iConcludedDate.getDate());

        return iProject;
    }
}
