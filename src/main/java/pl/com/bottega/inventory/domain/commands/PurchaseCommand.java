package pl.com.bottega.inventory.domain.commands;

import java.util.HashMap;
import java.util.Map;

public class PurchaseCommand implements Validatable {


    private Map<String, Integer> products = new HashMap<>();

    public PurchaseCommand(Map<String, Integer> products) {
        this.products = products;
    }

    public PurchaseCommand() {

    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if (products.isEmpty())
            errors.add("skus", "are required");
        validateAmountValue(errors);
    }


    private void validateAmountValue(Validatable.ValidationErrors errors) {
        for (String key : products.keySet()) {
            if (products.get(key) == null) {
                errors.add(key, "can't be blank");
            }
            if (products.get(key) <= 0 || products.get(key) > 999)
                errors.add(key, "must be between 1 and 999");
        }

    }
}