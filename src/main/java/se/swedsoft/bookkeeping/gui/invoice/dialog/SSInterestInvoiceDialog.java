package se.swedsoft.bookkeeping.gui.invoice.dialog;

import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.model.SSDefaultTableModel;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSDialog;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSInformationDialog;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSErrorDialog;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.gui.invoice.panel.SSInterestInvoicePanel;
import se.swedsoft.bookkeeping.gui.invoice.SSInvoiceFrame;
import se.swedsoft.bookkeeping.data.SSInvoice;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.system.SSPostLock;
import se.swedsoft.bookkeeping.SSBookkeeping;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: Andreas Lago
 * Date: 2006-apr-06
 * Time: 14:09:53
 */
public class SSInterestInvoiceDialog {


    private static ResourceBundle bundle = SSBundle.getBundle();

    /**
     *
     * @param iMainFrame
     */
    public static void showDialog(final SSMainFrame iMainFrame, final AbstractTableModel pModel ) {
        final String lockString = "interestinvoice"+SSDB.getInstance().getCurrentCompany().getId();
        if(!SSPostLock.applyLock(lockString)){
            new SSErrorDialog( iMainFrame, "interestinvoice.open");
            return;
        }

        List<SSInvoice> iRows = SSInterestInvoicePanel.getRows();

        if(iRows.size() == 0){
            SSPostLock.removeLock(lockString);
            new SSInformationDialog(iMainFrame, "interestinvoice.noinvoices");
            return;
        }

        final SSDialog               iDialog = new SSDialog(iMainFrame, SSInterestInvoiceDialog.bundle.getString("invoiceframe.interestinvoice.title"));
        final SSInterestInvoicePanel iPanel  = new SSInterestInvoicePanel(iRows);

        iDialog.add(iPanel.getPanel(), BorderLayout.CENTER);

        iPanel.addOkAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<SSInvoice> iInterestInvoices = iPanel.getInterestInvoices();
                for (SSInvoice iInvoice : iInterestInvoices) {
                    SSDB.getInstance().addInvoice(iInvoice);
                }

                if (pModel != null) pModel.fireTableDataChanged();

                SSPostLock.removeLock(lockString);
                //iDialog.setVisible(false);
                iDialog.closeDialog();
            }
        });

        iPanel.addCancelAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SSPostLock.removeLock(lockString);
                iDialog.setVisible(false);
            }
        });

        iDialog.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                SSPostLock.removeLock(lockString);
            }
        });

        iDialog.setSize(900, 600);
        iDialog.setLocationRelativeTo(iMainFrame);
        iDialog.setVisible();

        // return iPanel.getInvoiceType();

    }

}
