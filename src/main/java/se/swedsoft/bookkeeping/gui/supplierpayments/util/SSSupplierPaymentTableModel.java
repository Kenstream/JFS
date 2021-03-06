package se.swedsoft.bookkeeping.gui.supplierpayments.util;

import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.SSTable;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.supplier.SSSupplierDialog;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.data.SSSupplier;
import se.swedsoft.bookkeeping.data.SSSupplierInvoice;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.common.SSCurrency;
import se.swedsoft.bookkeeping.importexport.supplierpayments.data.PaymentMethod;
import se.swedsoft.bookkeeping.importexport.supplierpayments.data.SupplierPayment;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

/**
 * User: Andreas Lago
 * Date: 2006-aug-28
 * Time: 15:00:30
 */
public class SSSupplierPaymentTableModel extends SSTableModel<SupplierPayment> {



    /**
     * Constructor.
     *
     * @param iSupplierInvoices The data for the table model.
     */
    public SSSupplierPaymentTableModel(List<SSSupplierInvoice> iSupplierInvoices) {
        super();

        List<SupplierPayment> iPayments = new LinkedList<SupplierPayment>();
        for (SSSupplierInvoice iSupplierInvoice : iSupplierInvoices) {
            iPayments.add( new SupplierPayment(iSupplierInvoice) );
        }
        setObjects(iPayments);
    }

    /**
     * Returns the type of data in this model. <P>
     *
     * @return The current data type.
     */
    public Class getType() {
        return SupplierPayment.class;
    }















    /**
     * Number
     */
    public static SSTableColumn<SupplierPayment> COLUMN_NUMBER = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.1")) {
        public Object getValue(SupplierPayment iPayment) {
            SSSupplierInvoice iInvoice = iPayment.getSupplierInvoice();

            return iInvoice.getNumber();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
        }

        public Class getColumnClass() {
            return String.class;
        }
        public int getDefaultWidth() {
            return 70;
        }
    };

    /**
     * Supplier nr
     */
    public static SSTableColumn<SupplierPayment> COLUMN_SUPPLIER_NUMBER = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.2")) {
        public Object getValue(SupplierPayment iPayment) {
            SSSupplierInvoice iInvoice = iPayment.getSupplierInvoice();

            return iInvoice.getSupplierNr();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
        }

        public Class getColumnClass() {
            return String.class;
        }
        public int getDefaultWidth() {
            return 150;
        }
    };

    /**
     * Supplier name
     */
    public static SSTableColumn<SupplierPayment> COLUMN_SUPPLIER_NAME = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.3")) {
        public Object getValue(SupplierPayment iPayment) {
            SSSupplierInvoice iInvoice = iPayment.getSupplierInvoice();

            return iInvoice.getSupplierName();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
        }

        public Class getColumnClass() {
            return String.class;
        }
        public int getDefaultWidth() {
            return 150;
        }
    };


    /**
     * Supplier name
     */
    public static SSTableColumn<SupplierPayment> COLUMN_DATE = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.4")) {
        public Object getValue(SupplierPayment iPayment) {
            return iPayment.getDate();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
            iPayment.setDate((Date)iValue);
        }

        public Class getColumnClass() {
            return Date.class;
        }
        public int getDefaultWidth() {
            return 100;
        }
    };


    /**
     * Value
     */
    public static SSTableColumn<SupplierPayment> COLUMN_VALUE = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.5")) {
        public Object getValue(SupplierPayment iPayment) {

            return iPayment.getValue();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
            iPayment.setValue((BigDecimal)iValue);
        }

        public Class getColumnClass() {
            return BigDecimal.class;
        }
        public int getDefaultWidth() {
            return 100;
        }
    };

    /**
     *
     */
    public static SSTableColumn<SupplierPayment> COLUMN_CURRENCY = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.6")) {
        public Object getValue(SupplierPayment iPayment) {
            SSSupplierInvoice iInvoice = iPayment.getSupplierInvoice();

            return iInvoice.getCurrency();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
        }

        public Class getColumnClass() {
            return SSCurrency.class;
        }
        public int getDefaultWidth() {
            return 50;
        }
    };



    /**
     * Payment method
     */
    public static SSTableColumn<SupplierPayment> COLUMN_PAYMENT_METHOD = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.7")) {
        public Object getValue(SupplierPayment iPayment) {
            return iPayment.getPaymentMethod();
        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
            iPayment.setPaymentMethod((PaymentMethod)iValue);

            if(iPayment.getPaymentMethod() == PaymentMethod.BANKGIRO){
                iPayment.setAccount( iPayment.getBankGiro() );
            }
            if(iPayment.getPaymentMethod() == PaymentMethod.PLUSGIRO){
                iPayment.setAccount(  iPayment.getPlusGiro() );
            }

            if(iPayment.getPaymentMethod() == PaymentMethod.CASH){
                iPayment.setAccount(  iPayment.getOutpaymentNumber().toString() );
            }

        }

        public Class getColumnClass() {
            return PaymentMethod.class;
        }

        public int getDefaultWidth() {
            return 70;
        }
    };



    /**
     * Account
     */
    public static SSTableColumn<SupplierPayment> COLUMN_ACCOUNT = new SSTableColumn<SupplierPayment>(SSBundle.getBundle().getString("supplierpaymenttable.column.8")) {
        public Object getValue(SupplierPayment iPayment) {
            return iPayment.getAccount();

        }

        public void setValue(SupplierPayment iPayment, Object iValue) {
            PaymentMethod iPaymentMethod = iPayment.getPaymentMethod();

            if(iPaymentMethod != PaymentMethod.CASH) iPayment.setAccount((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }
        public int getDefaultWidth() {
            return 100;
        }
    };






    /**
     * Edit Supplier
     */
    public static SSTableColumn<SupplierPayment> COLUMN_EDIT_SUPPLIER = new SSTableColumn<SupplierPayment>("") {
        public Object getValue(SupplierPayment iPayment) {
            return iPayment.getSupplierInvoice().getSupplier(SSDB.getInstance().getSuppliers());
        }

        public void setValue(SupplierPayment iObject, Object iValue) {

        }

        public Class getColumnClass() {
            return SSSupplier.class;
        }
        public int getDefaultWidth() {
            return 20;
        }

        /**
         * @return
         */
        public TableCellEditor getCellEditor() {
            return new EditSupplierCellEditor();
        }

        /**
         * @return
         */
        public TableCellRenderer getCellRenderer() {
            return new TableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    return new JButton("...");
                }
            };
        }
    };





    /**
     * @param iTable
     */
    public void setupTable(SSTable iTable) {
        super.setupTable(iTable);

        iTable.setDefaultRenderer(PaymentMethod.class,  new PaymentMethodCellRenderer() );
        iTable.setDefaultEditor  (PaymentMethod.class,  new PaymentMethodCellEditor() );


    }


    /**
     *
     */
    private static class PaymentMethodCellEditor extends DefaultCellEditor {

        /**
         * Constructs a <code>DefaultCellEditor</code> that uses a text field.
         *
         */
        public PaymentMethodCellEditor() {
            super(new JComboBox() );

            JComboBox iComboBox = (JComboBox)getComponent();

            DefaultComboBoxModel iModel = new DefaultComboBoxModel();
            iModel.addElement(PaymentMethod.BANKGIRO);
            iModel.addElement(PaymentMethod.PLUSGIRO);
            iModel.addElement(PaymentMethod.CASH);

            iComboBox.setModel( iModel );

            iComboBox.setRenderer(new DefaultListCellRenderer(){
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    if(value instanceof PaymentMethod){
                        PaymentMethod iAction = (PaymentMethod) value;

                        setText( iAction.getDescription() );
                    } else {
                        setText("");
                    }

                    return this;
                }
            });
        }
    }

    /**
     *
     */
    private static class PaymentMethodCellRenderer extends DefaultTableCellRenderer {

        public void setValue(Object value) {
            if(value instanceof PaymentMethod){
                PaymentMethod iAction = (PaymentMethod) value;

                setText(iAction.getDescription());
            }  else {
                setText( "" );
            }
        }
    }




    /**
     *
     */
    private static class EditSupplierCellEditor extends AbstractCellEditor implements TableCellEditor{
        /**
         * Returns the value contained in the editor.
         *
         * @return the value contained in the editor
         */
        public Object getCellEditorValue() {
            return null;
        }

        /**
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            final SSSupplier iSupplier = (SSSupplier)value;
            final JTable     iTable    = table;

            JButton iButton = new JButton("..");
            iButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SSSupplierDialog.editDialog( SSMainFrame.getInstance(), iSupplier, null   );

                    TableModel iModel = iTable.getModel();
                    if(iModel instanceof AbstractTableModel){
                        ((AbstractTableModel)iModel).fireTableDataChanged();
                    }
                }
            });

            return iButton;
        }
    }

}
