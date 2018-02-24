package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.api.InventorDto;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.InventoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class JPAInventoryRepository implements InventoryRepository {

    private EntityManager entityManager;

    public JPAInventoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }

    @Override
    public boolean isExist(String skuCode) {
        Inventory product = entityManager.find(Inventory.class, skuCode);
        if (product != null) {
            return true;
        }
        return false;
    }

    @Override
    public Set<Inventory> getAll(Set<String> skuCodes) {
        Set<Inventory> result = new HashSet<>();
        for (String s : skuCodes) {
            if (get(s).isPresent())
                result.add(get(s).get());
        }
        return result;
    }

    @Override
    public Optional<Inventory> get(String skuCode) {
        try {
            Inventory inventory = (Inventory) entityManager.createQuery("FROM Inventory i WHERE i.skuCode =:skuCode")
                    .setParameter("skuCode", skuCode)
                    .getSingleResult();
            return Optional.of(inventory);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
