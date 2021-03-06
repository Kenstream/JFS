package se.swedsoft.bookkeeping.data.system;


import se.swedsoft.bookkeeping.data.SSNewCompany;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.rmi.server.UID;

/**
 * Date: 2006-feb-24
 * Time: 16:02:26
 *
 * Contains the information for each company
 *
 */
public class SSSystemCompany implements Serializable {

    // Constant for serialization versioning.
    static final long serialVersionUID = 1L;

    // The id of the company
    private UID iID;

    // The name of the company
    private String iName;

    // We are the current (active) company
    private boolean iCurrent;

    // The years in the company
    private List<SSSystemYear> iYears;


    // Our placehoder for the company, if loaded
    private transient SSNewCompany iCompany;


    /**
     * Creates a new system company
     */
    public SSSystemCompany(){
        iID      = new UID();
        iName    = "";
        iCompany = null;
        iYears   = new LinkedList<SSSystemYear>();
        iCurrent = false;
    }

    /**
     *
     * @param pCompany
     */
    public SSSystemCompany(SSNewCompany pCompany){
        iCompany = pCompany;
        iCurrent = false;
        iID      = new UID();
        iName    = pCompany.getName();
        iYears   = new LinkedList<SSSystemYear>();
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * returns the unique id for this company
     * @return
     */
    public UID getId() {
        return iID;
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public String getName() {
        return iName;
    }

    /**
     *
     * @param iName
     */
    public void setName(String iName) {
        this.iName = iName;
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return if the company is the current year
     */
    public boolean isCurrent() {
        return iCurrent;
    }

    /**
     * Set if the company shall be the current one, loads data if true, unloads if false
     *
     * @param pCurrent if the company shall be current
     */
    public void setCurrent(boolean pCurrent) {
        iCurrent = pCurrent;

    }
    //////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public SSNewCompany getData() {
        return iCompany;
    }

    /**
     *
     * @param iCompany
     */
    public void setData(SSNewCompany iCompany) {
        if(iCompany != null)
            this.iName = iCompany.getName();
        
        this.iCompany = iCompany;
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * The years for the company
     *
     * @return list of years
     */
    public List<SSSystemYear> getYears() {
        return iYears;
    }

    public void setYears(List<SSSystemYear> pYears) {
        iYears = pYears;
    }

    /**
     * The current year for the company
     *
     * @return list of years
     */
    public SSSystemYear getCurrentYear() {

        for (int i = 0; i < iYears.size(); i++) {
            SSSystemYear iYear = iYears.get(i);
            if (iYear.isCurrent()) {
                return iYears.get(i);
            }
        }
        return null;
    }

    public void setCurrentyear(SSSystemYear iNew) {
        if(iNew == null)
            return;
        
        for(SSSystemYear iYear: iYears) {
            if (iYear.isCurrent()) {
                iYear.setCurrent(false);
            }
            if (iYear.getId().equals(iNew.getId())) {
                iYear.setData(iNew.getData());
                iYear.setCurrent(true);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param other the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj
     *         argument; <code>false</code> otherwise.
     */
    public boolean equals(Object other) {
        if(other instanceof SSSystemCompany){
            SSSystemCompany iSystemCompany = (SSSystemCompany) other;

            return iID.equals( iSystemCompany.iID );
        }
        if(other instanceof SSNewCompany){
            SSNewCompany iCompany = (SSNewCompany) other;

            return iID.equals( iCompany.getId() );
        }
        return false;
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     *
     * @return a string representation of the object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SSSystemCompany (");
        sb.append(iID);
        sb.append("): ");
        sb.append(iName);

        return sb.toString();
    }



}
