package se.sundsvall.smssender.provider.linkmobility.domain;

import java.util.Arrays;
import java.util.EnumSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkMobilitySmsResponse {

    @JsonProperty("resultCode")
    private ResponseStatus status;

    public static enum ResponseStatus {

        UNKNOWN_ERROR(0),
        TEMPORARY_ROUTING_ERROR(1),
        PERMANENT_ROUTING_ERROR(2),
        MAXIMUM_THROTTLING_EXCEEDED(3),
        TIMEOUT(4),
        OPERATOR_UNKNOWN_ERROR(5),
        OPERATOR_ERROR(6),
        CONFIGURATION_ERROR(104),
        INTERNAL_ERROR(105),
        SENT(1000),
        BILLED_AND_DELIVERED(1001),
        EXPIRED(1002),
        DELETED(1003),
        MOBILE_FULL(1004),
        QUEUED(1005),
        NOT_DELIVERED(1006),
        DELIVERED_BILLED_DELAYED(1007),
        BILLED_OK(1008),
        BILLED_OK_AND_NOT_DELIVERED(1009),
        EXPIRED_ABSCENCE_OF_OPERATOR_DELIVERY_REPORT(1010),
        BILLED_OK_AND_SENT_TO_OPERATOR(1010),
        DELAYED(1012),
        SENT_TO_OPERATOR_BILL_DELAYED(1013),
        INVALID_SOURCE(2000),
        SOURCE_SHORTNUMBER_NOT_SUPPORTED(2001),
        SOURCE_ALPHA_NOT_SUPPORTED(2002),
        SOURCE_MSISDN_NOT_SUPPORTED(2003),
        DESTINATION_SHORTNUMBER_NOT_SUPPORTED(2100),
        DESTINATION_ALPHA_NOT_SUPPORTED(2101),
        DESTINATION_MSISDN_NOT_SUPPORTED(2102),
        OPERATION_BLOCKED(2103),
        UNKNOWN_SUBSCRIBER(2104),
        DESTINATION_BLOCKED(2105),
        NUMBER_ERROR(2106),
        DESTINATION_TEMPORARILY_BLOCKED(2107),
        CHARGING_ERROR(2200),
        SUBSCRIBER_HAS_LOW_BALANCE(2201),
        SUBSCRIBER_BARRED_FOR_OVERCHARGED(2202),
        SUBSCRIBER_TOO_YOUNG(2203),
        PREPAID_SUBSCRIBER_NOT_ALLOWED(2204),
        SERVICE_REJECTED_BY_SUBSCRIBER(2205),
        SUBSCRIBER_NOT_REGISTERED_IN_PAYMENT_SYSTEM(2206),
        SUBSCRIBER_HAS_REACHED_MAX_BALANCE(2207),
        GSM_ENCODING_NOT_SUPPORTED(3000),
        UCS2_ENCODING_NOT_SUPPORTED(3001),
        BINARY_ENCODING_NOT_SUPPORTED(3002),
        DELIVERY_REPORT_IS_NOT_SUPPORTED(4000),
        INVALID_MESSAGE_CONTENT(4001),
        INVALID_TARIFF(4002),
        INVALID_USER_DATA(4003),
        INVALID_USER_DATA_HEADER(4004),
        INVALID_DATA_CODING(4005),
        INVALID_VAT(4006),
        UNSUPPORTED_CONTENT_FOR_DESTINATION(4007),

        UNKNOWN_ERROR_2(106000),
        INVALID_AUTHENTICATION(106100),
        ACCESS_DENIED(106101),
        INVALID_OR_MISSING_PLATFORM_ID(106200),
        INVALID_OR_MISSING_PLATFORM_PARTNER_ID(106201),
        INVALID_OR_MISSING_CURRENCY_FOR_PREMIUM_MESSAGE(106202),
        NO_GATES_AVAILABLE(106300),
        SPECIFIED_GATE_UNAVAILABLE(106301);

        private static final EnumSet<ResponseStatus> SUCCESS_STATUSES = EnumSet.of(
            SENT,
            BILLED_AND_DELIVERED,
            QUEUED,
            BILLED_OK,
            BILLED_OK_AND_SENT_TO_OPERATOR,
            DELAYED,
                SENT_TO_OPERATOR_BILL_DELAYED
        );

        private final int value;

        ResponseStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean isSent() {
            return SUCCESS_STATUSES.contains(this);
        }

        @JsonCreator
        public static ResponseStatus forValue(final int value) {
            return Arrays.stream(ResponseStatus.values())
                .filter(status -> value == status.value)
                .findFirst()
                .orElse(null);
        }
    }
}
