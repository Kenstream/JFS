package se.swedsoft.bookkeeping.gui.invoice.panel;

import se.swedsoft.bookkeeping.gui.util.components.SSButton;
import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.util.table.SSTable;
import se.swedsoft.bookkeeping.gui.util.model.SSAccountTableModel;
import se.swedsoft.bookkeeping.gui.util.SSSelectionListener;
import se.swedsoft.bookkeeping.gui.invoice.util.SSInterestInvoiceTableModel;
import se.swedsoft.bookkeeping.data.SSInvoice;
import se.swedsoft.bookkeeping.data.SSAccount;
import se.swedsoft.bookkeeping.data.common.SSInvoiceType;
import se.swedsoft.bookkeeping.data.common.SSDefaultAccount;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.calc.math.SSInvoiceMath;
import se.swedsoft.bookkeeping.calc.math.SSInpaymentMath;
import se.swedsoft.bookkeeping.calc.math.SSDateMath;
import se.swedsoft.bookkeeping.SSBookkeeping;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

/**
 * User: Andreas Lago
 * Date: 2006-apr-12
 * Time: 10:30:06
 */
public class SSInterestInvoicePanel {
    private JPanel iPanel;
    private SSButton iCancelButton;
    private SSButton iOkButton;
    private SSTable iTable;

    private JTextField iDescription;

    private SSTableComboBox<SSAccount> iAccount;


    private SSInterestInvoiceTableModel iModel;
    private JTextField iAccountText;


    public SSInterestInvoicePanel(List<SSInvoice> iRows) {
        iModel = new SSInterestInvoiceTableModel( iRows );

        iTable.setModel(iModel);

        SSAccount iSelected = SSDB.getInstance().getCurrentCompany().getDefaultAccount(SSDB.getInstance().getCurrentAccountPlan(), SSDefaultAccount.InterestProfit);

        iAccount.setModel            ( SSAccountTableModel.getDropDownModel() );
        iAccount.setSearchColumns    ( 0);
        iAccount.setAllowCustomValues( true);
        iAccount.addSelectionListener(new SSSelectionListener<SSAccount>() {
            public void selected(SSAccount selected) {
                if(selected != null){
                    iAccountText.setText( selected.getDescription() );
                } else {
                    iAccount.setText("");
                }
            }
        });
        iAccount.setSelected ( iSelected, true );

        SSInterestInvoiceTableModel.setupTable(iTable);
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
     * @return
     */
    public List<SSInvoice> getInterestInvoices() {
        String    iDescription = this.iDescription.getText();
        SSAccount iAccount     = this.iAccount    .getSelected();

        return iModel.getInterestInvoices(iDescription, iAccount);
    }

    /**
     *
     * @return
     */
    public static List<SSInvoice> getRows(){
        // Get all invoices from the DB
        List<SSInvoice> iInvoices = SSDB.getInstance().getInvoices();

        // Get all the rows
        List<SSInvoice> iRows = new LinkedList<SSInvoice>();


        for (SSInvoice iInvoice : iInvoices) {
            // Skip invoices that is flagged as interest invoiced or is a cash sales
            if(iInvoice.isInterestInvoiced() || iInvoice.getType() == SSInvoiceType.CASH ) continue;

            BigDecimal iSaldo = SSInvoiceMath.getSaldo(iInvoice.getNumber());

            Date iLastInpayment = SSInpaymentMath.getLastInpaymentForInvoice(iInvoice);

            if(iLastInpayment == null ) iLastInpayment = new Date();

            // Floor the inpayment date so we don't get any credit invoices for invoices payed on the last day
            iLastInpayment = SSDateMath.floor(iLastInpayment);

            if( iSaldo.signum() == 0 && iLastInpayment.after( iInvoice.getDueDate() ) ){
                iRows.add(iInvoice);
            }
        }


        return iRows;
    }
}
