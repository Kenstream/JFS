package se.swedsoft.bookkeeping.gui.util.dialogs;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import javax.swing.*;
import java.awt.*;

import com.sun.java.help.impl.SwingWorker;

/**
 * User: Fredrik Stigsson
 * Date: 2006-feb-01
 * Time: 16:32:05
 */
public class SSInitDialog extends SSDialog {

    private JPanel iPanel;

    /**
     *
     * @param iFrame
     */
    public SSInitDialog(JFrame iFrame, String iTitle){
        super(iFrame, iTitle, false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setPanel(iPanel);

        pack();
        setLocationRelativeTo(iFrame);
    }

    /**
     *
     * @param iDialog
     */
    public SSInitDialog(JDialog iDialog, String iTitle){
        super(iDialog, iTitle, false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setPanel(iPanel);

        pack();
        setLocationRelativeTo(iDialog);
    }

    /**
     *
     * @param iFrame
     * @param iAction
     */
    public static void runProgress(JFrame iFrame, final Runnable iAction){
        runProgress(iFrame, "", iAction);
    }

    /**
     * 
     * @param iFrame
     * @param iTitle
     * @param iAction
     */
    public static void runProgress(JFrame iFrame, String iTitle, final Runnable iAction){
        final SSInitDialog dialog  = new SSInitDialog(iFrame, iTitle);
        dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        dialog.setVisible();
        
        new SwingWorker() {
            public Object construct() {
                try{
                    iAction.run();
                } finally{
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    dialog.setVisible(false);
                    dialog.dispose();
                }
                return null;
            }
        }.start();
    }


    /**
     *
     * @param iDialog
     * @param iAction
     */
    public static void runProgress(JDialog iDialog, final Runnable iAction){
        runProgress(iDialog, "", iAction);
    }

    /**
     *
     * @param iDialog
     * @param iTitle
     * @param iAction
     */
    public static void runProgress(JDialog iDialog, String iTitle, final Runnable iAction){
        final SSInitDialog dialog  = new SSInitDialog(iDialog, iTitle);
        dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        dialog.setVisible();

        new SwingWorker() {
            public Object construct() {
                try{
                    iAction.run();
                } finally{
                    dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    dialog.setVisible(false);
                    dialog.dispose();
                }
                return null;
            }
        }.start();
    }



}
