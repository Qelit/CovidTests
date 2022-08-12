package users;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "surName","firstName","patName","snils","birthday","oid"})
public class User {
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("patName")
    private String patName;
    @JsonProperty("snils")
    private String snils;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("number")
    private String number;
    @JsonProperty("series")
    private String series;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("surName")
    public String getSurName(){
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }

    @JsonProperty("firstName")
    public String getFirstName(){
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("patName")
    public String getPatName(){
        return patName;
    }

    @JsonProperty("patName")
    public void setPatName(String patName) {
        this.patName = patName;
    }

    @JsonProperty("snils")
    public String getSnils(){return snils; }

    @JsonProperty("snils")
    public void setSnils(String snils) {
        this.snils = snils;
    }

    @JsonProperty("birthday")
    public String getBirthday(){return birthday; }

    @JsonProperty("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @JsonProperty("oid")
    public String getOid(){return oid; }

    @JsonProperty("oid")
    public void setOid(String oid) {
        this.oid = oid;
    }

    @JsonProperty("number")
    public String getNumber(){return number; }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("series")
    public String getSeries(){return series; }

    @JsonProperty("series")
    public void setSeries(String series) {
        this.series = series;
    }

    @JsonGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonSetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
