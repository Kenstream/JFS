package se.swedsoft.bookkeeping.print.dialog;

import se.swedsoft.bookkeeping.gui.util.dialogs.SSDialog;
import se.swedsoft.bookkeeping.gui.util.SSButtonPanel;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.datechooser.SSDateChooser;
import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.gui.supplier.util.SSSupplierTableModel;
import se.swedsoft.bookkeeping.data.SSSupplier;
import se.swedsoft.bookkeeping.data.SSSupplierInvoice;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.calc.util.SSFilterFactory;
import se.swedsoft.bookkeeping.calc.util.SSFilter;
import se.swedsoft.bookkeeping.calc.math.SSSupplierInvoiceMath;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Date;

/**
 * User: Andreas Lago
 * Date: 2006-apr-19
 * Time: 14:28:34
 */
public class SSSupplierInvoiceListDialog extends SSDialog {

    private JPanel iPanel;

    private SSButtonPanel iButtonPanel;

    private JRadioButton iRadioAll;
    private JRadioButton iRadioNotpayed;
    private JRadioButton iRadioExpired;

    private JCheckBox iCheckDate;
    private JCheckBox iCheckSupplier;

    private SSTableComboBox<SSSupplier> iSupplier;
    private SSDateChooser iToDate;
    private SSDateChooser iFromDate;

    /**
     *
     */
    public SSSupplierInvoiceListDialog(SSMainFrame iMainFrame) {
        super(iMainFrame, SSBundle.getBundle().getString("supplierinvoicelistreport.dialog.title") );

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


        iSupplier.setModel( SSSupplierTableModel.getDropDownModel()) ;
        iSupplier.setSearchColumns(0);

        ChangeListener iChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                iSupplier.setEnabled( iCheckSupplier.isSelected() );

                iFromDate.setEnabled( iCheckDate.isSelected() );
                iToDate  .setEnabled( iCheckDate.isSelected() );
            }
        };

        iCheckDate     .addChangeListener(iChangeListener);
        iCheckSupplier .addChangeListener(iChangeListener);

        ButtonGroup iGroup = new ButtonGroup();

        iGroup.add(iRadioAll);
        iGroup.add(iRadioNotpayed);
        iGroup.add(iRadioExpired);

        iChangeListener.stateChanged(null);
    }

    /**
     *
     * @return
     */
    public JPanel getPanel() {
        return iPanel;
    }


    /**
     * Returns the invoices to print depending on the user selections
     *
     * @return
     */
    public List<SSSupplierInvoice> getElementsToPrint(){
        List<SSSupplierInvoice> iInvoices = SSDB.getInstance().getSupplierInvoices();

        SSFilterFactory<SSSupplierInvoice> iFactory = new SSFilterFactory<SSSupplierInvoice>(iInvoices);

        // Filter by non payed invoices
        if( iRadioNotpayed.isSelected() ){

            iFactory.applyFilter(new SSFilter<SSSupplierInvoice>() {
                public boolean applyFilter(SSSupplierInvoice iInvoice) {
                    return SSSupplierInvoiceMath.getSaldo(iInvoice.getNumber()).signum() != 0;
                }
            });
        }

        // Filter by expired
        if( iRadioExpired.isSelected() ){

            iFactory.applyFilter(new SSFilter<SSSupplierInvoice>() {
                public boolean applyFilter(SSSupplierInvoice iInvoice) {
                    return SSSupplierInvoiceMath.getSaldo(iInvoice.getNumber()).signum() != 0 && SSSupplierInvoiceMath.expired(iInvoice);
                }
            });
        }

        // Filter by a customer
        if(iCheckSupplier.isSelected() && iSupplier.getSelected() != null ){
            final SSSupplier iSupplier = this.iSupplier.getSelected();

            iFactory.applyFilter(new SSFilter<SSSupplierInvoice>() {
                public boolean applyFilter(SSSupplierInvoice iInvoice) {
                    return iInvoice.hasSupplier(iSupplier );
                }
            });

        }
        // Filter by date
        if(iCheckDate.isSelected() ){
            final Date iDateFrom = this.iFromDate.getDate();
            final Date iDateTo   = this.iToDate  .getDate();

            iFactory.applyFilter(new SSFilter<SSSupplierInvoice>() {
                public boolean applyFilter(SSSupplierInvoice iInvoice) {
                    return SSSupplierInvoiceMath.inPeriod(iInvoice, iDateFrom, iDateTo);
                }
            });
        }

        return iFactory.getObjects();
    }
}
