package ecomerce.sales.delivery.methods;

import org.junit.jupiter.api.Test;

import static ecomerce.sales.delivery.methods.Dimensions.ofCentimeters;
import static ecomerce.sales.delivery.methods.ProductsFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodsPolicyTest {

    DeliveryMethodsPolicy policy = new DeliveryMethodsPolicy();

    @Test
    void optionsNotLimited() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(oneProductWith(
                ofCentimeters(30, 26, 52),
                Weight.ofKiloGrams(24)
        ));

        assertThat(deliveryMethods.inpost()).isTrue();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void inpostNotAllowed() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(oneProductWith(
                ofCentimeters(60, 66, 62),
                Weight.ofKiloGrams(24)
        ));

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void optionsNotLimitedOtherTestDesign() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(smallAndLight())
        );

        assertThat(deliveryMethods.inpost()).isTrue();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void inpostNotAllowedOtherTestDesign() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(smallAndLight().butWith()
                        .dimensions(ofCentimeters(60, 66, 62)))
        );

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void tooHeavy() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(oneProductWith(
                ofCentimeters(30, 30, 30),
                Weight.ofKiloGrams(54)
        ));

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isFalse();
        assertThat(deliveryMethods.special()).isTrue();
    }

    @Test
    void specialRequired() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                oneProductWithSpecialDelivery()
        );

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isFalse();
        assertThat(deliveryMethods.special()).isTrue();
    }
}
