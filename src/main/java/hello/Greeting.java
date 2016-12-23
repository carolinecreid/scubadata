/*
    Greeting object. It has all the variables that are being passed through the RESTful API calls
 */

package hello;

import javax.json.Json;
import javax.json.JsonArray;

public class Greeting {

    private String content;

    private String startDate;
    private String endDate;
    private String domain;
    private String keyword;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String dateFrom) {
        this.startDate = dateFrom;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String dateTo) {
        this.endDate = dateTo;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
