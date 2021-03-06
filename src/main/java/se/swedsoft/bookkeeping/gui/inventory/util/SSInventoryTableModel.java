package se.swedsoft.bookkeeping.gui.inventory.util;

import se.swedsoft.bookkeeping.data.SSInvoice;
import se.swedsoft.bookkeeping.data.SSInventory;
import se.swedsoft.bookkeeping.data.common.SSCurrency;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;
import se.swedsoft.bookkeeping.gui.util.graphics.SSIcon;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.calc.math.SSInvoiceMath;

import javax.swing.*;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

/**
 * User: Andreas Lago
 * Date: 2006-mar-21
 * Time: 10:34:35
 */
public class SSInventoryTableModel extends SSTableModel<SSInventory> {


    /**
     * Default constructor.
     */
    public SSInventoryTableModel() {
        super(SSDB.getInstance().getInventories());
    }

    /**
     * Default constructor.
     */
    public SSInventoryTableModel(List<SSInventory> iInvoices) {
        super(iInvoices);
    }

    /**
     * Returns the type of data in this model.
     *
     * @return The current data type.
     */
    public Class getType() {
        return SSInventory.class;
    }



    /**
     *  Inventerings nummer
     */
    public static SSTableColumn<SSInventory> COLUMN_NUMBER = new SSTableColumn<SSInventory>(SSBundle.getBundle().getString("inventorytable.column.1")) {
        public Object getValue(SSInventory iInventory) {
            return iInventory.getNumber();
        }

        public void setValue(SSInventory iInvoice, Object iValue) {
            iInvoice.setNumber((Integer)iValue);

        }

        public Class getColumnClass() {
            return Integer.class;
        }

        public int getDefaultWidth() {
            return 70;
        }
    };

    /**
     * Datum
     */
    public static SSTableColumn<SSInventory> COLUMN_DATE = new SSTableColumn<SSInventory>(SSBundle.getBundle().getString("inventorytable.column.2")) {
        public Object getValue(SSInventory iInventory) {
            return iInventory.getDate();
        }

        public void setValue(SSInventory iInventory, Object iValue) {
            iInventory.setDate((Date)iValue);
        }

        public Class getColumnClass() {
            return Date.class;
        }

        public int getDefaultWidth() {
            return 90;
        }
    };

    /**
     * Text
     */
    public static SSTableColumn<SSInventory> COLUMN_TEXT = new SSTableColumn<SSInventory>(SSBundle.getBundle().getString("inventorytable.column.3")) {
        public Object getValue(SSInventory iInventory) {
            return iInventory.getText();
        }

        public void setValue(SSInventory iInventory, Object iValue) {
            iInventory.setText((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 400;
        }
    };











}
