package se.swedsoft.bookkeeping.gui.util.filechooser.util;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import java.util.ResourceBundle;
import java.io.File;

/**
 * Date: 2006-feb-13
 * Time: 14:48:14
 */
public class SSFilterBGMAX extends SSFileFilter {

    public static ResourceBundle bundle = SSBundle.getBundle();

    public SSFilterBGMAX(){
        super();
        addExtention("out");
        addExtention("ut");
    }
    /**
     * @return The description of this filter
     */
    public String getDescription() {
        return SSFilterBGMAX.bundle.getString("filechooser.bgmax.filter");
    }



}
