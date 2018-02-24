package pl.com.bottega.inventory.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.PurchaseDto;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;

import java.util.Map;

@RestController
public class InventoryController {

    private CommandGateway gateway;

    public InventoryController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping("/inventory")
    public void addProductsToMagazine(@RequestBody AddInventoryCommand cmd) {
       gateway.execute(cmd);
    }

    @PostMapping("/purchase")
    public PurchaseDto purchaseProducts(@RequestBody Map<String, Integer> map) {
        PurchaseCommand command = new PurchaseCommand();
        command.setProducts(map);
        return gateway.execute(command);
    }

}
