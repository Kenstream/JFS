package se.swedsoft.bookkeeping.gui.autodist.util;

import se.swedsoft.bookkeeping.data.SSAutoDist;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;

import java.util.List;


/**
 * User: Andreas Lago
 * Date: 2006-mar-21
 * Time: 10:34:35
 */
public class SSAutoDistTableModel  extends SSTableModel<SSAutoDist> {


    /**
     * Default constructor.
     */
    public SSAutoDistTableModel() {
        super(SSDB.getInstance().getAutoDists() );
    }

    /**
     * Default constructor.
     */
    public SSAutoDistTableModel(List<SSAutoDist> iAutoDists) {
        super(iAutoDists);
    }

    /**
     * Returns the type of data in this model.
     *
     * @return The current data type.
     */
    public Class getType() {
        return SSAutoDist.class;
    }



    /**
     *  Kontonummer
     */
    public static SSTableColumn<SSAutoDist> COLUMN_NUMBER = new SSTableColumn<SSAutoDist>(SSBundle.getBundle().getString("autodisttable.column.1")) {
        public Object getValue(SSAutoDist iAutoDist) {
            return iAutoDist.getNumber();
        }

        public void setValue(SSAutoDist iAutoDist, Object iValue) {
            iAutoDist.setAccountNumber((Integer)iValue);

        }

        public Class getColumnClass() {
            return Integer.class;
        }

        public int getDefaultWidth() {
            return 70;
        }
    };



    /**
     *  Beskrivning
     */
    public static SSTableColumn<SSAutoDist> COLUMN_DESCRIPTION = new SSTableColumn<SSAutoDist>(SSBundle.getBundle().getString("autodisttable.column.2")) {
        public Object getValue(SSAutoDist iAutoDist) {
            return iAutoDist.getDescription();
        }

        public void setValue(SSAutoDist iInvoice, Object iValue) {
            iInvoice.setDescrition((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 400;
        }
    };
}
