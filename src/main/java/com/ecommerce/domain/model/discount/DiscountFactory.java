package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.model.Category;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
public class DiscountFactory {

    static public Discount createCampaign(Category category, BigDecimal discount, int itemCount, DiscountType discountType) {
        if (discountType == DiscountType.RATE) {
            return RateCampaign.builder()
                    .category(category)
                    .minItemCount(itemCount)
                    .discountRate(discount)
                    .build();
        } else {
            return AmountCampaign.builder()
                    .category(category)
                    .minItemCount(itemCount)
                    .discountAmount(discount)
                    .build();
        }
    }

    static public Discount createCoupon(BigDecimal minAmount, BigDecimal discount, DiscountType discountType) {
        if (discountType == DiscountType.RATE) {
            return RateCoupon.builder()
                    .discountRate(discount)
                    .minAmount(minAmount)
                    .build();
        } else {
            return AmountCoupon.builder()
                    .discountAmount(discount)
                    .minAmount(minAmount)
                    .build();
        }
    }
}
