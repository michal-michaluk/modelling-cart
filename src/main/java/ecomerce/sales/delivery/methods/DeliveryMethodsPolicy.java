package ecomerce.sales.delivery.methods;

class DeliveryMethodsPolicy {

    private final Dimensions maxDimensionsForInpostDelivery = Dimensions.ofCentimeters(40, 36, 62);
    private final Weight maxWeightOfCourierDelivery = Weight.ofKiloGrams(25);

    DeliveryMethods deliveryMethodsFor(Products products) {
        boolean fitsDimensions = products.allMatches(product -> product.dimensionFitsInto(maxDimensionsForInpostDelivery));
        boolean lessThanWeight = products.sum(product -> product.weight().weightInGrams()) < maxWeightOfCourierDelivery.weightInGrams();
        boolean requiresSpecial = products.anyMatches(Products.Product::requiresSpecialDelivery);

        return new DeliveryMethods(
                fitsDimensions && lessThanWeight,
                lessThanWeight,
                requiresSpecial || !lessThanWeight
        );
    }
}
