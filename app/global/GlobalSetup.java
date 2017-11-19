package global;

import controllers.Application;
import play.GlobalSettings;
import play.Logger;

public class GlobalSetup extends GlobalSettings
{

    private static final String COMPANY_FILE = "company.json";

    public void onStart(Application app)
    {
        Logger.info("Application has started");
        try
        {
//            initCompanyList();
        } catch (Exception e)
        {
            Logger.error("Could not load the " + COMPANY_FILE
                    + " file. Error: " + e.getLocalizedMessage());
        }
    }
}