package se.swedsoft.bookkeeping.data;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * User: Johan Gunnarsson
 * Date: 2007-nov-22
 * Time: 16:47:12
 */
public class SSOwnReport implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer iId;

    private String iName;

    private List<SSOwnReportRow> iHeadings;

    private String iProjectNr;

    private String iResultUnitNr;

    public SSOwnReport(){
        iId = -1;
        iName = "";
        iHeadings = new LinkedList<SSOwnReportRow>();
        iProjectNr = null;
        iResultUnitNr = null;
    }

    /**
     *
     * @return iNumber - Nummret f�r rapporten
     */
    public Integer getId(){
        return iId;
    }

    public void setId(Integer iId) {
        this.iId = iId;
    }

    /**
     *
     * @return iName - Namnet p� rapporten
     */
    public String getName() {
        return iName;
    }

    public void setName(String iName) {
        this.iName = iName;
    }

    /**
     *
     * @return iHeadings - Rubriker samt dess typ
     */
    public List<SSOwnReportRow> getHeadings() {
        return iHeadings;
    }

    public void setHeadings(List<SSOwnReportRow> iHeadings) {
        this.iHeadings = iHeadings;
    }

    /**
     *
     * @return iProjectNumber - Projekt f�r rapporten
     */
    public String getProjectNr() {
        return iProjectNr;
    }

    public void setProjectNr(String iProjectNumber) {
        this.iProjectNr = iProjectNumber;
    }

    /**
     *
     * @return iResultUnitNumber - Resultatenhet f�r rapporten
     */
    public String getResultUnitNr() {
        return iResultUnitNr;
    }

    public void setResultUnitNr(String iResultUnitNr) {
        this.iResultUnitNr = iResultUnitNr;
    }

    /**
     *
     * @param obj - The object to compare with
     * @return Whether obj is equal to this object
     */

    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj instanceof SSOwnReport){
            SSOwnReport iOwnReport = (SSOwnReport) obj;
            if(iOwnReport.iId.equals(this.iId)) return true;
        }
        return false;
    }


}
