package se.swedsoft.bookkeeping.gui.purchaseorder.util;

import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.graphics.SSIcon;
import se.swedsoft.bookkeeping.data.SSPurchaseOrder;
import se.swedsoft.bookkeeping.data.SSSupplierInvoice;
import se.swedsoft.bookkeeping.data.common.SSCurrency;
import se.swedsoft.bookkeeping.data.system.SSDB;

import javax.swing.*;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

/**
 * User: Andreas Lago
 * Date: 2006-jun-15
 * Time: 13:07:54
 */
public class SSPurchaseOrderTableModel extends SSTableModel<SSPurchaseOrder> {


    /**
     * Default constructor.
     */
    public SSPurchaseOrderTableModel() {
        super(SSDB.getInstance().getPurchaseOrders() );
    }

    /**
     * Constructor.
     *
     * @param pObjects The data for the table model.
     */
    public SSPurchaseOrderTableModel(List<SSPurchaseOrder> pObjects) {
        super(pObjects);
    }


    /**
     * Returns the type of data in this model. <P>
     *
     * @return The current data type.
     */
    public Class getType() {
        return SSPurchaseOrder.class;
    }

    /**
     *  Utskriven
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_PRINTED = new SSTableColumn<SSPurchaseOrder>("") {
        public Object getValue(SSPurchaseOrder iPurchaseOrder) {
            return iPurchaseOrder.isPrinted() ? SSIcon.getIcon("ICON_PROPERTIES16", SSIcon.IconState.NORMAL ) : null;
        }

        public void setValue(SSPurchaseOrder iPurchaseOrder, Object iValue) {
        }

        public Class getColumnClass() {
            return ImageIcon.class;
        }

        public int getDefaultWidth() {
            return 20;
        }
    };


    /**
     * Number column
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_NUMBER = new SSTableColumn<SSPurchaseOrder>("Ordernr") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getNumber();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
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
    public static SSTableColumn<SSPurchaseOrder> COLUMN_SUPPLIER_NR = new SSTableColumn<SSPurchaseOrder>("Leverantörsnummer") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getSupplierNr();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
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
    public static SSTableColumn<SSPurchaseOrder> COLUMN_SUPPLIER_NAME = new SSTableColumn<SSPurchaseOrder>("Leverantörsnamn") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getSupplierName();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
            iObject.setSupplierName((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }
        public int getDefaultWidth() {
            return 150;
        }
    };

    /**
     * Date column
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_DATE = new SSTableColumn<SSPurchaseOrder>("Inköpsdatum") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getDate();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
        }

        public Class getColumnClass() {
            return Date.class;
        }

        public int getDefaultWidth() {
            return 110;
        }

    };


    /**
     * Sum column
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_SUM = new SSTableColumn<SSPurchaseOrder>("Belopp") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getSum();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
        }

        public Class getColumnClass() {
            return BigDecimal.class;
        }

        public int getDefaultWidth() {
            return 100;
        }

    };

    /**
     * Currency column
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_CURRENCY = new SSTableColumn<SSPurchaseOrder>("Valuta") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getCurrency();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
        }

        public Class getColumnClass() {
            return SSCurrency.class;
        }

        public int getDefaultWidth() {
            return 50;
        }

    };

    /**                                                                                                                                   * EstimatedDelivery column
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_ESTIMATED_DELIVERY = new SSTableColumn<SSPurchaseOrder>("Beräknad leverans") {
        public Object getValue(SSPurchaseOrder iObject) {
            return iObject.getEstimatedDelivery();
        }

        public void setValue(SSPurchaseOrder iObject, Object iValue) {
        }

        public Class getColumnClass() {
            return Date.class;
        }

        public int getDefaultWidth() {
            return 110;
        }

    };


    /**
     * Faktura
     */
    public static SSTableColumn<SSPurchaseOrder> COLUMN_INVOICE = new SSTableColumn<SSPurchaseOrder>(SSBundle.getBundle().getString("purchaseordertable.column.8")) {
        public Object getValue(SSPurchaseOrder iOrder) {
            return iOrder.getInvoiceNr( );
        }

        public void setValue(SSPurchaseOrder iOrder, Object iValue) {
            //iOrder.setInvoice((SSSupplierInvoice)iValue);
        }

        public Class getColumnClass() {
            return Integer.class;//SSSupplierInvoice.class;
        }

        public int getDefaultWidth() {
            return 90;
        }
    };

}
