package pl.com.bottega.inventory.domain;

import javax.persistence.*;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "amount")
    private Integer amount;

    public Inventory() {
    }

    public Inventory(String skuCode, Integer amount) {
        this.skuCode = skuCode;
        this.amount = amount;
    }

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

    public Integer addAmount(Integer value) {
        return this.amount += value;
    }

    public Integer substractAmount(Integer value) {
        return amount -= value;
    }

    public boolean canPurchase(Integer value) {
        return amount >= value;
    }

}
