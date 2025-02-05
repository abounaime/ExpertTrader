package dto;

import lombok.Data;

@Data
public class PageData {
    private int pageSize;
    private int pageNumber;
    private int totalPages;
}
