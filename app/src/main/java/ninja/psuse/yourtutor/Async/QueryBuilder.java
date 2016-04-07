package ninja.psuse.yourtutor.Async;

import ninja.psuse.yourtutor.other.RegisterInfo;

/**
 * Created by peerapon01 on 4/7/16 AD.
 */
public class QueryBuilder {
    public String getAPIKey(){
        return "HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
    }
    public String getBaseURL(){
        return "https://api.mlab.com/api/1/databases/yourtutor/collections/";
    }
    public String getUserColl(){
        return "users";
    }
    public String createRegisterInfo(RegisterInfo contact)
    {
        return String
                .format("{\"first_name\": \"%s\", "
                                + "\"last_name\": \"%s\", \"email\": \"%s\", "
                                + "\"phone\": \"%s\",\"facebookid\":\"%s\","
                                +"\"lineid\": \"%s\",\"facebookname\":\"%s\"}",
                        contact.firstname, contact.lastname, contact.email, contact.mobilenum,contact.facebookID,
                        contact.lineid,contact.facebookName);
    }

}
