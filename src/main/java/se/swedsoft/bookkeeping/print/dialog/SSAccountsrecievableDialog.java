package se.swedsoft.bookkeeping.print.dialog;

import se.swedsoft.bookkeeping.gui.util.dialogs.SSDialog;
import se.swedsoft.bookkeeping.gui.util.datechooser.SSDateChooser;
import se.swedsoft.bookkeeping.gui.util.SSButtonPanel;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.data.SSInvoice;
import se.swedsoft.bookkeeping.data.system.SSDB;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Date;

/**
 * User: Andreas Lago
 * Date: 2006-apr-24
 * Time: 16:16:46
 */
public class SSAccountsrecievableDialog extends SSDialog {

    private JPanel iPanel;

    private SSDateChooser iDate;

    private SSButtonPanel iButtonPanel;


    /**
     * @param iFrame
     */
    public SSAccountsrecievableDialog(SSMainFrame iFrame) {
        super(iFrame, SSBundle.getBundle().getString("accountsrecievablereport.dialog.title"));

        setPanel(iPanel);

        iButtonPanel.addCancelActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setModalResult(JOptionPane.CANCEL_OPTION, true);
            }
        });
        iButtonPanel.addOkActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setModalResult(JOptionPane.OK_OPTION, true);
            }
        });
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return iDate.getDate();
    }
}
