package dto;

import lombok.Data;

@Data
public class Allowance {
    private int remainingAllowance;
    private int totalAllowance;
    private long allowanceExpiry;
}
