package se.swedsoft.bookkeeping.gui.util.filechooser.util;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Date: 2006-feb-10
 * Time: 15:07:37
 */
public class SSImageFilter extends SSFileFilter {

    public static ResourceBundle bundle = SSBundle.getBundle();

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String png = "png";

    public SSImageFilter(){
        super();
        addExtention(gif );
        addExtention(jpeg);
        addExtention(jpg );
        addExtention(png );
    }
    /**
     * @return The description of this filter
     */
    public String getDescription() {
        return bundle.getString("filechooser.image.filter");
    }



}


