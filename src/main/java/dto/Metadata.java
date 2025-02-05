package dto;

import lombok.Data;

@Data
public class Metadata {
    private Allowance allowance;
    private int size;
    private PageData pageData;
}
