package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Product;
import coffeeshop.entity.Store;
import coffeeshop.entity.Suborder;
import coffeeshop.facade.AddressFacade;
import coffeeshop.facade.OrderInfoFacade;
import coffeeshop.facade.StoreFacade;
import coffeeshop.facade.SuborderFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
public class CartManagerBean implements CartManager, Serializable {

    private static final Logger LOG = Logger.getLogger(CartManagerBean.class.getName());

    private static final long serialVersionUID = 1L;

    private OrderInfo orderInfo = null;

    @EJB
    private OrderInfoFacade orderInfoFacade;

    @EJB
    private StoreFacade storeFacade;

    @EJB
    private AddressFacade addressFacade;

    @EJB
    private SuborderFacade suborderFacade;

    @PostConstruct
    public void init() {
        orderInfo = new OrderInfo();
        orderInfo.setSuborderList(new ArrayList<>());
        orderInfo.setIsFinished((short) 0);
        orderInfo.setIsPrepared((short) 0);
    }

    @Override
    public OrderInfo saveAndGetOrderInfo(Store store, Address address) {
        orderInfo.setDateCreate(new Date());
        orderInfo.setConfirmation(newConfirmationId());
        orderInfo.setAmount(getOrderAmount());
        orderInfo.setStoreId(store);
        orderInfo.setAddressId(address);
        store.getOrderInfoList().add(orderInfo);
        address.getOrderInfoList().add(orderInfo);
        orderInfoFacade.create(orderInfo);
        storeFacade.edit(store);
        addressFacade.edit(address);
        for (Suborder suborder : orderInfo.getSuborderList()) {
            suborderFacade.create(suborder);
        }
        return orderInfo;
    }

    @Override
    public void add(Product product, List<Ingredient> ingredients, short quality) throws CartManagerException {
        Set<IngredientCategory> categories = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
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
        LOG.log(Level.INFO, "Added, current: {0}", orderInfo);
    }

    private int newConfirmationId() {
        // TODO: Change confirmation id to meaning full number
        return 0;
    }

    @Override
    public BigDecimal getOrderAmount() {
        List<Suborder> suborders = orderInfo.getSuborderList();
        BigDecimal amount = BigDecimal.ZERO;
        for (Suborder suborder : suborders) {
            amount = amount.add(getSuborderAmount(suborder));
        }
        return amount;
    }

    private BigDecimal getSuborderAmount(Suborder suborder) {
        BigDecimal amount = BigDecimal.ZERO;
        amount = amount.add(suborder.getProductId().getCost());
        for (Ingredient ingredient : suborder.getIngredientList()) {
            amount = amount.add(ingredient.getCost());
        }
        return amount;
    }

    @Override
    public List<Suborder> getSuborders() {
        return orderInfo.getSuborderList();
    }

    @Override
    public void remove(Suborder suborder) throws CartManagerException {
        if (!orderInfo.getSuborderList().remove(suborder)) {
            throw new CartManagerException("Suborder not contained in cart");
        }
    }

    @Override
    public int getItemCount() {
        int quality = 0;
        for (Suborder suborder : orderInfo.getSuborderList()) {
            quality += suborder.getQuantity();
        }
        return quality;
    }
}
