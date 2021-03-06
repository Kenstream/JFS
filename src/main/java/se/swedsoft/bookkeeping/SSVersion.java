package se.swedsoft.bookkeeping;

public interface SSVersion {

    public static final String app_title   = "JFS Administration";

    public static final String app_package = "Administration";

    public static final String app_version = "2.0.5";

    public static final String app_build   = "2008-01-16";


    // Toggles demo version
    public static final boolean DemoVersion = false;

    // Togles if a isValid license is required
    public static final boolean LicenseRequired = false;

    // Toggles if the user can remove vouchers
    public static final boolean iAllowVoucherDeletion = false;
}