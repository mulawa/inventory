package pl.com.bottega.inventory.api;

public class InventorDto {

    private String skuCode;
    private Integer amount;

    public InventorDto(String skuCode, Integer amount) {
        this.skuCode = skuCode;
        this.amount = amount;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setCount(Integer amount) {
        this.amount = amount;
    }
}
