package dev.acobano.pcm.model.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    US_DOLLAR("United States Dollar", "USD", "$"),
    EURO("Euro", "EUR", "€"),
    BRITISH_POUND("British Pound Sterling", "GBP", "£"),
    JAPANESE_YEN("Japanese Yen", "JPY", "¥"),
    CHINESE_YUAN("Chinese Yuan Renminbi", "CNY", "¥"),
    CANADIAN_DOLLAR("Canadian Dollar", "CAD", "C$"),
    HK_DOLLAR("Hong Kong Dollar", "HKD", "HK$"),
    AUSTRALIAN_DOLLAR("Australian Dollar", "AUD", "A$"),
    SINGAPORE_DOLLAR("Singapore Dollar", "SGD", "S$"),
    SWISS_FRANC("Swiss Franc", "CHF", "CHF"),
    SWEDISH_KRONA("Swedish Krona", "SEK", "kr"),
    POLISH_ZLOTY("Polish Zloty", "PLN", "zł"),
    NORWEGIAN_KRONE("Norwegian Krone", "NOK", "kr"),
    DANISH_KRONE("Danish Krone", "DKK", "kr"),
    MEXICAN_PESO("Mexican Peso", "MXN", "$"),
    SA_RAND("South African Rand", "ZAR", "R"),
    NZD("New Zealand Dollar", "NZD", "NZ$"),
    THAI_BAHT("Thai Baht", "THB", "฿"),
    KOREAN_WON("South Korean Won", "KRW", "₩"),
    INDIAN_RUPEE("Indian Rupee", "INR", "₹"),
    RUSSIAN_RUBLE("Russian Ruble", "RUB", "₽"),
    BRAZILIAN_REAL("Brazilian Real", "BRL", "R$"),
    TURKISH_LIRA("Turkish Lira", "TRY", "₺"),
    BITCOIN("Bitcoin", "BTC", "₿");

    private final String name;
    private final String isoCode;
    private final String symbol;
}
