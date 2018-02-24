package pl.com.bottega.inventory.domain;

import java.util.Optional;
import java.util.Set;

public interface InventoryRepository  {

    void save(Inventory inventory);

    Optional<Inventory> get(String skuCode);

    boolean isExist(String skuCode);

    Set <Inventory> getAll(Set<String> skus);
}
