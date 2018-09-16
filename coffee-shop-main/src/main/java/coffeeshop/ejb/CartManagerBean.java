package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Product;
import coffeeshop.entity.Store;
import coffeeshop.entity.Suborder;
import coffeeshop.facade.OrderInfoFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class CartManagerBean implements CartManager, Serializable {
    
    private static final long serialVersionUID = 1L;

    private OrderInfo orderInfo = null;

    @Inject
    private OrderInfoFacade orderInfoFacade;

    @PostConstruct
    public void init() {
        orderInfo = new OrderInfo();
        orderInfo.setSuborderList(new ArrayList<>());
        orderInfo.setIsFinished((short) 0);
        orderInfo.setIsPrepared((short) 0);
    }

    @Override
    public OrderInfo check() {
        orderInfo.setDateCreate(new Date());
        orderInfo.setConfirmation(newConfirmationId());
        orderInfo.setAmount(getOrderAmmount());
        return orderInfo;
    }

    @Override
    public void add(Product product, List<Ingredient> ingredients, short quality) throws CartManagerException {
        Set<IngredientCategory> categories = new HashSet<>();
        for (Ingredient ingredient: ingredients) {
            IngredientCategory category = ingredient.getIngredientCategoryId();
            if (categories.contains(category)) {
                throw new CartManagerException("Repeated ingredient category " + category.getName());
            } else {
                categories.add(category);
            }
        }
        Suborder suborder = new Suborder();
        suborder.setIngredientList(ingredients);
        suborder.setOrderId(orderInfo);
        suborder.setProductId(product);
        suborder.setQuantity(quality);
        orderInfo.getSuborderList().add(suborder);
    }

    private int newConfirmationId() {
        return 0;
    }

    private BigDecimal getOrderAmmount() {
        List<Suborder> suborders = orderInfo.getSuborderList();
        BigDecimal amount = BigDecimal.ZERO.setScale(2);
        for (Suborder suborder : suborders) {
            amount = amount.add(getSuborderAmmount(suborder));
        }
        return amount;
    }
    
    private BigDecimal getSuborderAmmount(Suborder suborder) {
        BigDecimal amount = BigDecimal.ZERO.setScale(2);
        amount = amount.add(suborder.getProductId().getCost());
        for (Ingredient ingredient: suborder.getIngredientList()) {
            amount = amount.add(ingredient.getCost());
        }
        return amount;
    }
}
