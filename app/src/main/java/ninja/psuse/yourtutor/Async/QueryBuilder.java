package ninja.psuse.yourtutor.Async;

import ninja.psuse.yourtutor.other.CourseInfo;
import ninja.psuse.yourtutor.other.RegisterInfo;

/**
 * Created by peerapon01 on 4/7/16 AD.
 */
public class QueryBuilder {
    public String getAPIKey() {
        return "HXLkpE-1gKRhr8kYsje_fLtdLva5DSkR";
    }

    public String getBaseURL() {
        return "https://api.mlab.com/api/1/databases/yourtutor/collections/";
    }

    public String getUserColl() {
        return "users";
    }

    public String createRegisterInfo(RegisterInfo contact) {
        return String
                .format("{\"first_name\": \"%s\", "
                                + "\"last_name\": \"%s\", \"email\": \"%s\", "
                                + "\"phone\": \"%s\",\"facebookid\":\"%s\","
                                + "\"lineid\": \"%s\",\"facebookname\":\"%s\"}",
                        contact.firstname, contact.lastname, contact.email, contact.mobilenum, contact.facebookID,
                        contact.lineid, contact.facebookName);
    }

    public String createCourseInfo(CourseInfo course) {
        return String
                .format("{\"category\": \"%s\",\"author\":\"%s\","
                                + "\"reservedBy\": \"%s\","
                                + "\"subject\": \"%s\", \"level\": \"%s\", "
                                + "\"location\": \"%s\",\"school\":\"%s\","
                                + "\"priceperHr\": \"%s\",\"description\":\"%s\","
                                + "\"status\": \"%s\",\"facebookID\":\"%s\"}",
                        course.category, course.author, course.reservedBy, course.subject, course.level, course.location, course.school, course.priceperHr,
                        course.description, course.status, course.authorfacebookID);
    }
    public String changeStatusInfo(String facebookID){
        return String.format("{\"$set\":{\"reservedBy\":\""+facebookID+"\",\"status\":\"reserved\"}}");
    }


}
