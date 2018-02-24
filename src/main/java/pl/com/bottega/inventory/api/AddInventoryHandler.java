package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.InventoryRepository;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;

@Component
public class AddInventoryHandler implements Handler<AddInventoryCommand, Void> {

    private InventoryRepository repository;

    public AddInventoryHandler(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Void handle(AddInventoryCommand command) {
        if (repository.isExist(command.getSkuCode())) {
            repository.get(command.getSkuCode()).ifPresent(inventory -> {
                inventory.addAmount(command.getAmount());
                repository.save(inventory);
            });
        } else
            repository.save(new Inventory(command.getSkuCode(), command.getAmount()));
        return null;
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return AddInventoryCommand.class;
    }
}
