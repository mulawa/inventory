package pl.com.bottega.inventory.domain.commands;

public class AddInventoryCommand implements Validatable {

    private String skuCode;
    private Integer amount;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public void validate(ValidationErrors errors) {
        validatePresenceOf(skuCode, "skuCode", errors);
        validatePresenceOf(amount, "amount", errors);
        validateAmount("amount", errors);
    }

    public void validateAmount(String name, ValidationErrors errors) {
        if (amount != null)
            if (amount < 0 || amount > 999) {
                errors.add("amount", "must be between 1 and 999");
            }
    }


}
