package se.swedsoft.bookkeeping.calc.math;

import se.swedsoft.bookkeeping.data.*;
import se.swedsoft.bookkeeping.data.base.SSSaleRow;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

/**
 * User: Andreas Lago
 * Date: 2006-sep-22
 * Time: 16:34:16
 */
public class SSInventoryMath {

    /**
     * 
     * @param iInventory
     * @param pTo
     * @return
     */
    public static boolean inPeriod(SSInventory iInventory, Date pTo) {
        Date iDate = iInventory.getDate();
        Date iTo   = SSDateMath.ceil (pTo);

        return  (iDate.getTime() <= iTo.getTime());

    }

    /**
     *
     * @param iInventory
     * @param pFrom
     * @param pTo
     * @return
     */
    public static boolean inPeriod( SSInventory iInventory, Date pFrom, Date pTo){
        Date iDate = iInventory.getDate();
        Date iFrom = SSDateMath.floor(pFrom);
        Date iTo   = SSDateMath.ceil (pTo);

        return (iFrom.getTime() <= iDate.getTime()) && (iDate.getTime() <= iTo.getTime());
    }

    /**
     *
     * @param iInventory
     * @param iProduct
     * @return
     */
    public static boolean hasProduct(SSInventory iInventory, SSProduct iProduct) {

        for (SSInventoryRow iRow : iInventory.getRows()) {
            if(iRow.hasProduct(iProduct)) return true;
        }
        return false;
    }

    public static Map<String, Integer> getStockInfluencing(List<SSInventory> iInventories) {
        Map<String, Integer> iInventoryCount = new HashMap<String, Integer>();
        for (SSInventory iInventory : iInventories) {
            for (SSInventoryRow iRow : iInventory.getRows()) {
                if(iRow.getChange() == null) continue;
                Integer iReserved = iInventoryCount.get(iRow.getProductNr()) == null ? iRow.getChange() : iInventoryCount.get(iRow.getProductNr()) + iRow.getChange();
                iInventoryCount.put(iRow.getProductNr(), iReserved);
            }
        }
        return iInventoryCount;
    }

}
