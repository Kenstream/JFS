package se.swedsoft.bookkeeping.importexport.sie.fields;

import se.swedsoft.bookkeeping.importexport.sie.SSSIEImporter;
import se.swedsoft.bookkeeping.importexport.sie.SSSIEExporter;
import se.swedsoft.bookkeeping.importexport.sie.util.SIEReader;
import se.swedsoft.bookkeeping.importexport.sie.util.SIEWriter;
import se.swedsoft.bookkeeping.importexport.sie.util.SIELabel;
import se.swedsoft.bookkeeping.importexport.util.SSImportException;
import se.swedsoft.bookkeeping.importexport.util.SSExportException;
import se.swedsoft.bookkeeping.data.SSNewCompany;
import se.swedsoft.bookkeeping.data.SSNewAccountingYear;
import se.swedsoft.bookkeeping.data.system.SSDB;

/**
 * Date: 2006-feb-23
 * Time: 10:02:10
 */
public class SIEEntryBranschkod extends SIEEntry {
    /**
     * Imports the entry
     *
     * @param iImporter
     * @param iReader
     * @return If anything was imported
     * @throws se.swedsoft.bookkeeping.importexport.util.SSImportException
     *
     */
    public boolean importEntry(SSSIEImporter iImporter, SIEReader iReader, SSNewAccountingYear iYearData) throws SSImportException {
        return false;
    }

    /**
     * Exports the entry
     *
     * @param iExporter
     * @param iWriter
     * @return If anything was exported
     * @throws se.swedsoft.bookkeeping.importexport.util.SSExportException
     *
     */
    public boolean exportEntry(SSSIEExporter iExporter, SIEWriter iWriter, SSNewAccountingYear iCurrentYearData) throws SSExportException {
          //SSNewCompany iCompany = SSDB.getInstance().getCurrentCompany();

        // TODO SNI-kod ???
        iWriter.append(SIELabel.SIE_BKOD);
        iWriter.append( 0);
        iWriter.newLine();
        return true;
    }
}
