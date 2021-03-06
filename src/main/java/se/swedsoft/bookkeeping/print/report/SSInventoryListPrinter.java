package se.swedsoft.bookkeeping.print.report;

import se.swedsoft.bookkeeping.print.SSPrinter;
import se.swedsoft.bookkeeping.print.util.SSDefaultJasperDataSource;
import se.swedsoft.bookkeeping.data.*;
import se.swedsoft.bookkeeping.data.base.SSSaleRow;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.model.SSDefaultTableModel;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.text.DateFormat;

/**
 * Date: 2006-mar-03
 * Time: 15:32:42
 */
public class SSInventoryListPrinter extends SSPrinter {

    private SSInventoryRowPrinter iPrinter;

    private SSDefaultJasperDataSource iDataSource;

    private List<SSInventory> iInventories;

    /**
     *
     */
    public SSInventoryListPrinter() {
        this(SSDB.getInstance().getInventories() );
    }

    /**
     *
     */
    public SSInventoryListPrinter( List<SSInventory> iInventories){
        super();
        // Get all orders
        this.iInventories = iInventories;

        setPageHeader  ("header_period.jrxml");
        setColumnHeader("inventorylist.jrxml");
        setDetail      ("inventorylist.jrxml");
    }


    /**
     * Gets the title file for this repport
     *
     * @return
     */
    public String getTitle() {
        return SSBundle.getBundle().getString("inventorylistreport.title");
    }

    /**
     * @return SSDefaultTableModel
     */
    protected SSDefaultTableModel getModel() {

        iPrinter = new SSInventoryListPrinter.SSInventoryRowPrinter();
        iPrinter.generateReport();

        addParameter("Report"      , iPrinter.getReport());
        addParameter("Parameters"  , iPrinter.getParameters() );

        iDataSource = new SSDefaultJasperDataSource(iPrinter.getModel());

        SSDefaultTableModel<SSInventory> iModel = new SSDefaultTableModel<SSInventory>() {

            DateFormat iFormat = DateFormat.getDateInstance(DateFormat.SHORT);

            public Class getType() {
                return SSInventory.class;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;

                SSInventory iInventory = getObject(rowIndex);

                switch (columnIndex) {
                    case 0  :
                        value = iInventory.getNumber();
                        break;
                    case 1:
                        value = iFormat.format(iInventory.getDate());
                        break;
                    case 2:
                        value = iInventory.getText();
                        break;
                    case 3:
                        iPrinter.setInventory(iInventory);

                        iDataSource.reset();

                        value = iDataSource;
                        break;
                }

                return value;
            }
        };

        iModel.addColumn("inventory.number");
        iModel.addColumn("inventory.date");
        iModel.addColumn("inventory.text");
        iModel.addColumn("inventory.rows");



        Collections.sort(iInventories, new Comparator<SSInventory>() {
            public int compare(SSInventory o1, SSInventory o2) {
                return o1.getNumber() - o2.getNumber();
            }
        });

        iModel.setObjects(iInventories);


        return iModel;
    }


    private class SSInventoryRowPrinter extends SSPrinter {

        private SSDefaultTableModel<SSInventoryRow> iModel;

        /**
         *
         */
        public SSInventoryRowPrinter( ){
            setMargins(0,0,0,0);

            setDetail ("inventorylist.row.jrxml");
            setSummary("inventorylist.row.jrxml");



            iModel = new SSDefaultTableModel<SSInventoryRow>(  ) {

                public Class getType() {
                    return SSSaleRow.class;
                }

                public Object getValueAt(int rowIndex, int columnIndex) {
                    Object value = null;

                    SSInventoryRow iRow = getObject(rowIndex);

                    SSProduct iProduct = iRow.getProduct();

                    switch (columnIndex) {
                        case 0  :
                            value = iRow.getProductNr();
                            break;
                        case 1:
                            value = iProduct == null ? null : iProduct.getDescription();
                            break;
                        case 2:
                            value = iRow.getStockQuantity();
                            break;
                        case 3:
                            value = iRow.getInventoryQuantity();
                            break;
                        case 4:
                            value = iRow.getChange();
                            break;

                    }

                    return value;
                }
            };

            iModel.addColumn("inventoryrow.product");
            iModel.addColumn("inventoryrow.descripion");
            iModel.addColumn("inventoryrow.stockquantity");
            iModel.addColumn("inventoryrow.inventoryquantity");
            iModel.addColumn("inventoryrow.change");
        }


        /**
         * Gets the data model for this report
         *
         * @return SSDefaultTableModel
         */
        protected SSDefaultTableModel getModel() {
            return iModel;
        }

        /**
         * Gets the title for this report
         *
         * @return The title
         */
        public String getTitle() {
            return null;
        }

        /**
         *
         * @param iInventory
         */
        public void setInventory(SSInventory iInventory) {
            iModel.setObjects( iInventory.getRows() );
        }
    }




}
