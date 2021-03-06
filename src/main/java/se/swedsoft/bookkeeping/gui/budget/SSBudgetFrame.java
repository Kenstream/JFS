/*
 * @(#)SSBudgetFrame.java                v 1.0 2005-nov-10
 *
 * Time-stamp: <2005-nov-10 19:42:46 Hasse>
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
package se.swedsoft.bookkeeping.gui.budget;

import se.swedsoft.bookkeeping.gui.util.*;
import se.swedsoft.bookkeeping.gui.util.components.SSButton;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.gui.budget.panel.SSBudgetMainPanel;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSProgressDialog;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSQueryDialog;
import se.swedsoft.bookkeeping.data.SSBudget;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.system.SSPostLock;
import se.swedsoft.bookkeeping.data.SSNewAccountingYear;
import se.swedsoft.bookkeeping.print.report.SSBudgetPrinter;
import se.swedsoft.bookkeeping.SSBookkeeping;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;


/**
 */
public class SSBudgetFrame extends se.swedsoft.bookkeeping.gui.util.frame.SSDefaultTableFrame {

    private static SSBudgetFrame cInstance;

    /**
     *
     * @param pMainFrame
     * @param pWidth
     * @param pHeight
     */
    public static void showFrame(SSMainFrame pMainFrame, int pWidth, int pHeight){
        if( cInstance == null || cInstance.isClosed() ){
            cInstance = new SSBudgetFrame(pMainFrame, pWidth, pHeight);
        }
        cInstance.setVisible(true);
        cInstance.deIconize();
    }

    /**
     *
     * @return The SSNewAccountingYearFrame
     */
    public static SSBudgetFrame getInstance(){
        return cInstance;
    }


    private SSNewAccountingYear  iAccountingYear;


    private SSBudgetMainPanel iBudgetMainPanel;

    /**
     * Default constructor.
     */
    private SSBudgetFrame(SSMainFrame pMainFrame, int pWidth, int pHeight) {
        super(pMainFrame, SSBundle.getBundle().getString("budgetframe.title"), pWidth, pHeight);
        iAccountingYear   = SSDB.getInstance().getCurrentYear();
        //iBudgetMainPanel.setBudget(iAccountingYear.getBudget());
        addCloseListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(cInstance != null ){
                    SSQueryDialog iDialog = new SSQueryDialog( getMainFrame(), SSBundle.getBundle(),  "budgetframe.saveonclose" );
                    int iResponce = iDialog.getResponce();
                    if(iResponce != JOptionPane.YES_OPTION){
                        SSPostLock.removeLock("budget"+SSDB.getInstance().getCurrentCompany().getId()+SSDB.getInstance().getCurrentYear().getId());
                        return;
                    }
                    iAccountingYear.setBudget( iBudgetMainPanel.getBudget() );
                    SSDB.getInstance().updateAccountingYear(iAccountingYear);
                    SSPostLock.removeLock("budget"+SSDB.getInstance().getCurrentCompany().getId()+SSDB.getInstance().getCurrentYear().getId());
                }
            }
        });
    }

    /**
     * This method should return a toolbar if the sub-class wants one.
     * Otherwise, it may return null.
     *
     * @return A JToolBar or null.
     */
    public JToolBar getToolBar() {
        JToolBar toolBar = new JToolBar();

        SSButton iButton;

        // Save
        // ***************************
        iButton = new SSButton("ICON_SAVEITEM", "budgetframe.savebutton", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                iAccountingYear.setBudget(iBudgetMainPanel.getBudget());
                SSDB.getInstance().updateAccountingYear(iAccountingYear);
                SSPostLock.removeLock("budget"+SSDB.getInstance().getCurrentCompany().getId()+SSDB.getInstance().getCurrentYear().getId());

                cInstance = null;
                setVisible(false);
            }
        });
        toolBar.add(iButton);

        // Cancel
        // ***************************
        iButton = new SSButton("ICON_CANCELITEM", "budgetframe.cancelbutton", new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                SSPostLock.removeLock("budget"+SSDB.getInstance().getCurrentCompany().getId()+SSDB.getInstance().getCurrentYear().getId());
                cInstance = null;
                setVisible(false);
            }
        });
        toolBar.add(iButton);
        toolBar.addSeparator();


        // Print
        // ******************
        iButton = new SSButton("ICON_PRINT", "budgetframe.printbutton", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final SSMainFrame iMainFrame = SSBudgetFrame.this.getMainFrame();

                final Date iFrom = SSDB.getInstance().getCurrentYear().getFrom();
                final Date iTo = SSDB.getInstance().getCurrentYear().getTo();

                SSProgressDialog.runProgress(iMainFrame, new Runnable(){
                    public void run() {
                        SSBudgetPrinter iPrinter = new SSBudgetPrinter(iFrom, iTo);
                        iPrinter.preview(iMainFrame);
                    }
                });
            }
        });
        toolBar.add(iButton);



        return toolBar;

    }

    /**
     * This method should return the main content for the frame. <P>
     * Such as an object table.
     *
     * @return The main content for this frame.
     */
    public JComponent getMainContent() {
        SSBudget iBudget = new SSBudget( SSDB.getInstance().getCurrentYear().getBudget() );
        iBudgetMainPanel =  new SSBudgetMainPanel(iBudget);

        return iBudgetMainPanel.getPanel() ;
    }



    /**
     * This method should return the status bar content, if any. <P>
     *
     * @return The content for the status bar or null if none is wanted.
     */
    public JComponent getStatusBar() {
        return null;
    }


    /**
     *
     * @return boolean
     */
    public boolean isCompanyFrame() {
        return true;
    }

    /**
     *
     * @return boolean
     */
    public boolean isYearDataFrame() {
        return true;
    }

    public void actionPerformed(ActionEvent e)
    {
        iAccountingYear=null;
        iBudgetMainPanel=null;
        SSBudgetFrame.cInstance=null;
    }

}
