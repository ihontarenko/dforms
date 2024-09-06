package df.base.dto;

public interface KeyValueDTO extends DTO {

    String getKey();

    void setKey(String key);

    String getValue();

    void setValue(String value);

    String getOwnerId();

    void setOwnerId(String id);

}
