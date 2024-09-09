package df.base.dto;

public interface SlaveDTO extends DTO {

    String getPrimaryId();

    String getKey();

    String getValue();

    record Simple(String id, String key, String value, String primaryId) implements SlaveDTO {

        @Override
        public String getPrimaryId() {
            return primaryId;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

    }

}
