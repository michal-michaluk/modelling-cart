package ecomerce.sales.delivery.methods;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DeliveryMethodsService {
    private final DeliveryMethodsPolicy policy;
    private final ProductStorageInfoRepository products;

    public DeliveryMethods deliveryMethodsFor(List<String> productIds) {
        return policy.deliveryMethodsFor(products.getStorageInfo(productIds));
    }
}
