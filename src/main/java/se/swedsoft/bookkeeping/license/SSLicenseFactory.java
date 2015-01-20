package se.swedsoft.bookkeeping.license;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created with IntelliJ IDEA.
 * User: shikhar
 * Date: 2015-01-10
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public class SSLicenseFactory {
    private static final String PermanentID = "9";
    private static final String EvaluationID = "D";
    private static final String PersonalID = "A";
    private static final String GroupID = "E";
    private static final String ExpiresDateID = "3";
    private static final String ExpiresIntervalID = "8";
    private static String iMessage;

    private static long crc32(String iValue)
    {
        Checksum iChecksum = new CRC32();
        for (byte b : iValue.getBytes()) {
            iChecksum.update(b);
        }
        return iChecksum.getValue();
    }

    private static Date decodeDate(String iHexDate)
    {
        Calendar iCalendar = Calendar.getInstance();

        long iDate = Long.parseLong(iHexDate, 16);

        iCalendar.set(1, (int)(iDate / 372L) + 1970);
        iCalendar.set(2, (int)(iDate % 372L / 31L) - 1);
        iCalendar.set(5, (int)(iDate % 372L % 31L));
        iCalendar.set(11, iCalendar.getActualMaximum(11));
        iCalendar.set(12, iCalendar.getActualMaximum(12));
        iCalendar.set(13, iCalendar.getActualMaximum(13));

        return iCalendar.getTime();
    }

    private static String encodeDate(Date iDate, int iSize)
    {
        Calendar iCalendar = Calendar.getInstance();

        iCalendar.setTime(iDate);

        long iYear = iCalendar.get(1) - 1970;
        long iMonth = iCalendar.get(2) + 1;
        long iDay = iCalendar.get(5);

        long iTime = iYear * 372L + iMonth * 32L + iDay;

        return encodeLong(iTime, iSize);
    }

    private static String encodeLong(long iValue, int iSize)
    {
        String iHexValue = Long.toHexString(iValue).toUpperCase();
        while (iHexValue.length() < iSize) {
            iHexValue = "0" + iHexValue;
        }
        return iHexValue;
    }

    private static long decodeLong(String iValue)
    {
        return Long.parseLong(iValue, 16);
    }

    private static String encodeString(String iValue, int iSize)
    {
        long iValuecrc = crc32(iValue);

        return encodeLong(iValuecrc, iSize);
    }

    private static SSLicenseType decodeType(String iPrefix)
    {
        if (iPrefix.equals("D")) {
            return SSLicenseType.Evaluation;
        }
        if (iPrefix.equals("9")) {
            return SSLicenseType.Permanent;
        }
        return null;
    }

    private static Boolean decodePersonal(String iPrefix)
    {
        if (iPrefix.equals("A")) {
            return Boolean.valueOf(true);
        }
        if (iPrefix.equals("E")) {
            return Boolean.valueOf(false);
        }
        return null;
    }

    private static Boolean decodeExpires(String iPrefix)
    {
        if (iPrefix.equals("3")) {
            return Boolean.valueOf(true);
        }
        if (iPrefix.equals("8")) {
            return Boolean.valueOf(false);
        }
        return null;
    }

    public static String generateKey(String iName, SSLicenseType iType, boolean iPersonal, Date iExpiresOn, Integer iExpiresAfter)
    {
        StringBuilder iKey = new StringBuilder();
        if (iType == SSLicenseType.Evaluation) {
            iKey.append("D");
        }
        if (iType == SSLicenseType.Permanent) {
            iKey.append("9");
        }
        if (iPersonal) {
            iKey.append("A");
        } else {
            iKey.append("E");
        }
        long iSeed1 = (long) (Math.random() * 1048575.0D);
        long iSeed2 = (long) (Math.random() * 1048575.0D);
        long iSeed3 = (long) (Math.random() * 4294967295.0D);
        if (iPersonal) {
            iKey.append(encodeString(iName, 8));
        } else {
            iKey.append(encodeLong(iSeed3, 8));
        }
        iKey.append(encodeLong(iSeed1, 5));
        if (iType == SSLicenseType.Evaluation)
        {
            if (iExpiresAfter == null)
            {
                iKey.append("3");
                iKey.append(encodeDate(iExpiresOn, 4));
            }
            else if (iExpiresOn == null)
            {
                iKey.append("8");
                iKey.append(encodeLong(iExpiresAfter.intValue(), 4));
            }
            else
            {
                throw new RuntimeException("One of iExpiresOn and iExpiresAfter must be null ");
            }
        }
        else if (iType == SSLicenseType.Permanent) {
            iKey.append(encodeLong(iSeed2, 5));
        }
        long iCheckSum = crc32(iKey.toString());


        iKey.append(encodeLong(iCheckSum, 8));


        return formatKey(iKey.toString());
    }

    public static String generateKey(String iName, SSLicenseType iType, boolean iPersonal)
    {
        return generateKey(iName, iType, iPersonal, new Date(), null);
    }

    public static boolean isValid(String iName, String iKey)
    {
        Date iNow = new Date();
        return isValid(iName, iKey, iNow);
    }

    public static boolean isValid(String iName, String iKey, Date iNow)
    {
        iKey = trimKey(iKey);
        if (iKey.length() != 28)
        {
            setMessage("The licence key is the wrong length. " + iKey.length());
            return false;
        }
        StringIterator iIterator = new StringIterator(iKey);


        SSLicenseType iType = decodeType(iIterator.next(1));
        if (iType == null)
        {
            setMessage("The licence key dont start with a valid type prefix.");
            return false;
        }
        Boolean iPersonal = decodePersonal(iIterator.next(1));
        if (iPersonal == null)
        {
            setMessage("The licence key dont start with a valid group prefix.");
            return false;
        }
        if (iPersonal.booleanValue())
        {
            String iNameHash = iIterator.next(8);
            if (crc32(iName) != decodeLong(iNameHash))
            {
                setMessage("The name do not match.");
                return false;
            }
        }
        else
        {
            iIterator.next(8);
        }
        iIterator.next(5);
        if (iType == SSLicenseType.Evaluation)
        {
            Boolean iExpiresAtADate = decodeExpires(iIterator.next(1));
            if (iExpiresAtADate == null)
            {
                setMessage("The evaluation type is not valid.");
                return false;
            }
            Date iExpires;
            if (iExpiresAtADate.booleanValue())
            {
                iExpires = decodeDate(iIterator.next(4));
            }
            else
            {
                Calendar iCalendar = Calendar.getInstance();

                long iNumdays = decodeLong(iIterator.next(4));

                iCalendar.setTimeInMillis(System.currentTimeMillis());
                iCalendar.add(5, (int)iNumdays);
                iExpires = iCalendar.getTime();
            }
            if (iNow.getTime() > iExpires.getTime())
            {
                setMessage("The licence has expired.");
                return false;
            }
        }
        else
        {
            iIterator.next(5);
        }
        String iData = iKey.substring(0, 20);

        String iDataHash = iIterator.next(8);
        if (crc32(iData) != Long.parseLong(iDataHash, 16))
        {
            setMessage("The checksum do not match.");
            return false;
        }
        return true;
    }

    public static SSLicenseType getType(String iKey)
    {
        iKey = trimKey(iKey);
        if (iKey.startsWith("D")) {
            return SSLicenseType.Evaluation;
        }
        if (iKey.startsWith("9")) {
            return SSLicenseType.Permanent;
        }
        return null;
    }

    public static Date getExpires(String iKey)
    {
        iKey = trimKey(iKey);
        if ((iKey.length() == 28) && (iKey.startsWith("D")))
        {
            Boolean iExpiresAtADate = decodeExpires(iKey.substring(15, 16));
            if (iExpiresAtADate == null)
            {
                setMessage("The evaluation type is not valid.");
                return null;
            }
            if (iExpiresAtADate.booleanValue()) {
                return decodeDate(iKey.substring(16, 20));
            }
            Calendar iCalendar = Calendar.getInstance();

            long iNumdays = decodeLong(iKey.substring(16, 20));

            iCalendar.setTime(new Date());
            iCalendar.add(5, (int)iNumdays);

            return iCalendar.getTime();
        }
        return null;
    }

    public static Boolean getPersonal(String iKey)
    {
        iKey = trimKey(iKey);
        if (iKey.length() == 28) {
            return decodePersonal(iKey.substring(1, 2));
        }
        return null;
    }

    public static String trimKey(String iKey)
    {
        return iKey.trim().replace("-", "");
    }

    public static String formatKey(String iKey)
    {
        iKey = trimKey(iKey);

        StringBuilder iFormatedKey = new StringBuilder();

        StringIterator iIterator = new StringIterator(iKey);
        while (iIterator.hasNext(5))
        {
            iFormatedKey.append(iIterator.next(5));
            iFormatedKey.append("-");
        }
        iFormatedKey.append(iIterator.next(255));


        return iFormatedKey.toString();
    }

    public static void main(String[] args)
    {
        Calendar iCalendar = Calendar.getInstance();




        String iName = "Andreas Lago";
        String iKey = generateKey(iName, SSLicenseType.Permanent, true);

        System.out.println("Key  : " + iKey);
        System.out.println("Valid: " + isValid(iName, iKey));
        System.out.println(" ");

        iCalendar.setTime(new Date());
        iCalendar.add(5, 1);

        iName = "Andreas Lago,asd";
        iKey = generateKey(iName, SSLicenseType.Evaluation, false, iCalendar.getTime(), null);

        System.out.println("Key  : " + iKey);
        System.out.println("Valid: " + isValid(iName, iKey));
        System.out.println(" ");


        System.out.println("-------------------------");

        iName = "Andreas Lago";
        iKey = "9A423-3B149-1523A-FEAAF-3C0C8-A89";

        System.out.println(isValid(iName, iKey));

        iName = "Andreas Lago";
        iKey = "DE423-3B149-35077-B6365-B11E1-D4A";

        System.out.println(isValid(iName, iKey));
    }

    public static String getMessage()
    {
        return iMessage;
    }

    private static void setMessage(String pLastMessage)
    {
        iMessage = pLastMessage;
    }

    private static class StringIterator
            implements Iterator<String>
    {
        private String iValue;
        private int iIndex;

        public StringIterator(String pValue)
        {
            this.iValue = pValue;
            this.iIndex = 0;
        }

        public boolean hasNext()
        {
            return this.iIndex < this.iValue.length();
        }

        public boolean hasNext(int iLength)
        {
            return this.iIndex + iLength < this.iValue.length();
        }

        public String next()
        {
            if (this.iIndex >= this.iValue.length()) {
                throw new NoSuchElementException();
            }
            String iNext = this.iValue.substring(this.iIndex, this.iIndex);

            this.iIndex += 1;

            return iNext;
        }

        public String next(int iLength)
        {
            if (this.iIndex >= this.iValue.length()) {
                throw new NoSuchElementException();
            }
            String iNext;
            if (this.iIndex + iLength < this.iValue.length()) {
                iNext = this.iValue.substring(this.iIndex, this.iIndex + iLength);
            } else {
                iNext = this.iValue.substring(this.iIndex);
            }
            this.iIndex += iLength;

            return iNext;
        }

        public boolean matches(int iLength, String... iMatch)
        {
            String iValue = next(iLength);
            for (String iCurrent : iMatch) {
                if (iCurrent.equals(iValue)) {
                    return true;
                }
            }
            return false;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
