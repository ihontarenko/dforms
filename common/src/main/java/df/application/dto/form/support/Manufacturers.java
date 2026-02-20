package df.application.dto.form.support;

public enum Manufacturers {

    TI("TI", "Texas Instruments"),
    VISHAY("Vishay", "Vishay Intertechnology"),
    UNI_OHMS("Uni-Ohms", "Uni-Ohms Electronics"),
    ST_MICROELECTRONICS("ST", "STMicroelectronics"),
    NXP("NXP", "NXP Semiconductors"),
    INFINEON("Infineon", "Infineon Technologies"),
    ON_SEMICONDUCTOR("ON", "ON Semiconductor"),
    ANALOG_DEVICES("ADI", "Analog Devices"),
    MICROCHIP("Microchip", "Microchip Technology"),
    RENESAS("Renesas", "Renesas Electronics"),
    MAXIM("Maxim", "Maxim Integrated"),
    SAMSUNG("Samsung", "Samsung Electronics"),
    PANASONIC("Panasonic", "Panasonic Corporation"),
    TDK("TDK", "TDK Corporation"),
    ROHM("ROHM", "ROHM Semiconductor"),
    KYOCERA("Kyocera", "Kyocera Corporation"),
    MURATA("Murata", "Murata Manufacturing"),
    AVX("AVX", "AVX Corporation"),
    EPCOS("EPCOS", "EPCOS AG"),
    KEMET("Kemet", "KEMET Corporation");

    private final String shortName;
    private final String fullName;

    Manufacturers(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}

