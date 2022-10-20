package ecomerce.sales.delivery.methods;

import java.util.List;

public interface ProductStorageInfoRepository {
    Products getStorageInfo(List<String> productIds);
}
