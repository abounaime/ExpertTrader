package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {
    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("preferred")
    private boolean preferred;

    @JsonProperty("accountType")
    private String accountType;
}
