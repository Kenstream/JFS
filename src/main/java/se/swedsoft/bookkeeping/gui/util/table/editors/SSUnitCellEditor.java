package se.swedsoft.bookkeeping.gui.util.table.editors;

import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.util.model.SSUnitTableModel;
import se.swedsoft.bookkeeping.data.common.SSUnit;



/**
 * User: Andreas Lago
 * Date: 2006-mar-27
 * Time: 11:37:41
 */
public class SSUnitCellEditor extends SSTableComboBox.CellEditor<SSUnit> {
    /**
     *
     */
    public SSUnitCellEditor() {
        super();

        setModel( SSUnitTableModel.getDropDownModel() );

        setSearchColumns(0);
    }




}
