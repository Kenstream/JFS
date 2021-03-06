package se.swedsoft.bookkeeping.importexport.bgmax.data;

/**
 * User: Andreas Lago
 * Date: 2006-aug-23
 * Time: 09:34:35
 */
public class BgMaxLine {


    private char [] iChars;

    /**
     *
     * @param iLine
     */
    public BgMaxLine(String iLine) throws RuntimeException{
        if(iLine.length()!= 80)
            throw new RuntimeException("BgMaxLine lengt mismatch: " + iLine.length() );

        iChars = new char[81];

        iLine.getChars(0, 80, iChars, 1);
    }



    /**
     *
     * @return
     */
    public String getTransaktionsKod(){
        return getField(1,2);
    }

    /**
     *
     * @param iStart
     * @return
     */
    public String getField(int iStart){
        return "" + iChars[iStart];

    }

    /**
     *
     * @param iStart
     * @param iEnd
     * @return
     */
    public String getField(int iStart, int iEnd){
        String iField = "";

        for(int i = iStart; i <= iEnd; i++){
            iField = iField + iChars[i];
        }
        return iField.trim();

    }

}
