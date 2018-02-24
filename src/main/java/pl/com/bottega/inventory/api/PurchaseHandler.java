package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.InventoryRepository;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class PurchaseHandler implements Handler<PurchaseCommand, PurchaseDto> {

    private InventoryRepository repository;

    public PurchaseHandler(InventoryRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public PurchaseDto handle(PurchaseCommand command) {
        validateParameter(command.getProducts().keySet(), repository.getAll(command.getProducts().keySet()));
        PurchaseDto purchase = new PurchaseDto();

        for (Map.Entry<String, Integer> items : command.getProducts().entrySet()) {
            Inventory product = repository.get(items.getKey()).get();
            if (!product.canPurchase(items.getValue())) {
                purchase.getMissingProducts().put(items.getKey(), items.getValue());
                return purchase;
            }
        }

        for (Map.Entry<String, Integer> items : command.getProducts().entrySet()) {
            Inventory product = repository.get(items.getKey()).get();
            product.substractAmount(items.getValue());
            purchase.getPurchasedProducts().put(items.getKey(), items.getValue());
        }

        purchase.setSuccess(true);
        return purchase;
    }

    private void validateParameter(Set<String> skuCodes, Set<Inventory> products) {
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        Set<String> sku = products.stream().map(Inventory::getSkuCode).collect(Collectors.toSet());
        for (String s : skuCodes) {
            if (!sku.contains(s)) {
                errors.add(s, "no such sku");
            }
        }
        if (!errors.isValid())
            throw new InvalidCommandException(errors);
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return PurchaseCommand.class;
    }
}
