package se.swedsoft.bookkeeping.gui.ownreport.util;

import se.swedsoft.bookkeeping.data.SSOwnReportRow;
import se.swedsoft.bookkeeping.data.*;
import se.swedsoft.bookkeeping.data.common.SSHeadingType;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.model.SSDefaultTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSEditableTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.SSTable;
import se.swedsoft.bookkeeping.gui.util.table.editors.SSTableEditor;
import se.swedsoft.bookkeeping.gui.util.table.editors.SSHeadingTypeCellEditor;
import se.swedsoft.bookkeeping.gui.util.table.editors.SSHeadingTypeCellRenderer;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.List;
import java.math.BigDecimal;

/**
 * User: Johan Gunnarsson
 * Date: 2007-nov-26
 * Time: 10:35:14
 */
public class SSOwnReportRowTableModel extends SSEditableTableModel<SSOwnReportRow> {

    /**
     *
     * @return
     */
    public SSOwnReportRow newObject() {
        return  new SSOwnReportRow();
    }

    public static SSTableModel<SSOwnReportRow> getDropDownModel(){
        SSOwnReportRowTableModel iModel = new SSOwnReportRowTableModel();
        iModel.addColumn( SSOwnReportRowTableModel.COLUMN_HEADING, false   );
        return iModel;
    }
    /**
     * Returns the type of data in this model.
     *
     * @return The current data type.
     */
    public Class getType() {
        return SSOwnReportRow.class;
    }

    public static SSTableColumn<SSOwnReportRow> COLUMN_TYPE = new SSTableColumn<SSOwnReportRow>(SSBundle.getBundle().getString("ownreport.headingtable.column.1")) {
        public Object getValue(SSOwnReportRow iObject) {
            return iObject.getType();
        }

        public void setValue(SSOwnReportRow iObject, Object iValue) {
            if(iValue instanceof SSHeadingType){
                iObject.setType((SSHeadingType)iValue);
            }
        }

        public Class getColumnClass() {
            return SSHeadingType.class;
        }

        public int getDefaultWidth() {
            return 75;
        }

        public TableCellEditor getCellEditor() {
            return new SSHeadingTypeCellEditor();
        }

        /**
         * @return
         */
        public TableCellRenderer getCellRenderer() {
            return new SSHeadingTypeCellRenderer();
        }
    };

    public static SSTableColumn<SSOwnReportRow> COLUMN_HEADING = new SSTableColumn<SSOwnReportRow>(SSBundle.getBundle().getString("ownreport.headingtable.column.2")) {
        public Object getValue(SSOwnReportRow iObject) {
            return iObject.getHeading();
        }

        public void setValue(SSOwnReportRow iObject, Object iValue) {
            iObject.setHeading((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 300;
        }
    };
}

