package df.application.dto.form.support;

public enum InterfaceType {
    CAN("CAN", "Controller Area Network", "Standard for robust vehicle bus communication"),
    CAN_FD("CAN-FD", "CAN with Flexible Data-rate", "Extended version of CAN with higher data rate"),
    I2C("I2C", "Inter-Integrated Circuit", "Synchronous, multi-master, multi-slave serial interface"),
    I2C_X2("I2C x2", "Dual I2C", "Two independent I2C interfaces for multiple devices"),
    SPI("SPI", "Serial Peripheral Interface", "Full-duplex, synchronous serial communication interface"),
    SPI_X2("SPI x2", "Dual SPI", "Two independent SPI interfaces for higher throughput"),
    USART("USART", "Universal Synchronous/Asynchronous Receiver/Transmitter", "Serial communication protocol supporting synchronous and asynchronous data transmission"),
    UART("UART", "Universal Asynchronous Receiver/Transmitter", "Asynchronous serial communication interface"),
    USB("USB", "Universal Serial Bus", "Standard interface for connecting peripherals"),
    USB_OTG("USB-OTG", "USB On-The-Go", "Allows USB devices to act as a host or peripheral"),
    USB_FS("USB-FS", "USB Full-Speed", "USB interface operating at 12 Mbps"),
    USB_HS("USB-HS", "USB High-Speed", "USB interface operating at 480 Mbps"),
    USB_C("USB-C", "USB Type-C", "Reversible USB connector standard"),
    LIN("LIN", "Local Interconnect Network", "Low-cost serial network protocol for automotive communication"),
    IRDA("IrDA", "Infrared Data Association", "Infrared communication standard for short-range wireless transmission"),
    JTAG("JTAG", "Joint Test Action Group", "Standard for testing and debugging integrated circuits"),
    SWD("SWD", "Serial Wire Debug", "Debug interface used in ARM processors"),
    SMBUS("SMBus", "System Management Bus", "Two-wire communication protocol derived from I2C for system management"),
    RS232("RS232", "RS-232", "Standard for serial communication transmission of data"),
    RS485("RS485", "RS-485", "Standard for balanced transmission with longer distances and multi-point systems"),
    RS422("RS422", "RS-422", "Serial communication standard for differential data transmission"),
    ETHERNET("Ethernet", "Ethernet", "Wired networking protocol for local area networks"),
    ETHERNET_MAC("Ethernet MAC", "Ethernet Media Access Control", "MAC interface for Ethernet communication"),
    ETHERNET_PHY("Ethernet PHY", "Ethernet Physical Layer", "Physical layer of Ethernet network"),
    HDLC("HDLC", "High-Level Data Link Control", "Synchronous data link layer protocol"),
    I2S("I2S", "Integrated Interchip Sound", "Synchronous serial bus for connecting digital audio devices"),
    TWI("TWI", "Two-Wire Interface", "Another name for I2C, commonly used by Atmel and Microchip"),
    MODBUS("MODBUS", "MODBUS", "Serial communication protocol for industrial automation"),
    PCI("PCI", "Peripheral Component Interconnect", "Standard for connecting peripherals to a processor"),
    PCIe("PCIe", "PCI Express", "High-speed interface for connecting peripheral devices to the processor"),
    SATA("SATA", "Serial Advanced Technology Attachment", "Interface for connecting storage devices like SSDs and HDDs"),
    SDIO("SDIO", "Secure Digital Input Output", "Interface for connecting SD cards with additional functionalities"),
    UART_X2("UART x2", "Dual UART", "Two independent UART interfaces"),
    USART_X2("USART x2", "Dual USART", "Two independent USART interfaces"),
    HDMI("HDMI", "High-Definition Multimedia Interface", "Interface for transmitting uncompressed video and audio data"),
    DSI("DSI", "Display Serial Interface", "Interface for connecting display panels"),
    CSI("CSI", "Camera Serial Interface", "Interface for connecting camera modules"),
    CAN_X2("CAN x2", "Dual CAN", "Two independent CAN interfaces for extended connectivity"),
    FLEX_RAY("FlexRay", "FlexRay", "Automotive communication system for real-time data transmission"),
    MOST("MOST", "Media Oriented Systems Transport", "High-speed multimedia network for automotive applications"),
    AHB("AHB", "Advanced High-performance Bus", "High-performance system bus used in ARM-based systems"),
    AXI("AXI", "Advanced eXtensible Interface", "High-performance, high-speed bus interface used in ARM processors"),
    SPI_QUAD("SPI Quad", "Quad SPI", "SPI interface with four data lines for higher data rates"),
    HART("HART", "Highway Addressable Remote Transducer", "Communication protocol for industrial automation and process control"),
    PROFIBUS("PROFIBUS", "Process Field Bus", "Standard for fieldbus communication in automation technology"),
    PROFINET("Profinet", "Profinet", "Real-time Ethernet standard for industrial automation"),
    FDDI("FDDI", "Fiber Distributed Data Interface", "Standard for data transmission in LANs using optical fiber"),
    THUNDERBOLT("Thunderbolt", "Thunderbolt", "High-speed data and video interface"),
    I3C("I3C", "Improved Inter-Integrated Circuit", "Next-generation I2C standard for better performance and power efficiency");

    private final String code;
    private final String name;
    private final String description;

    InterfaceType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %s", code, name, description);
    }
}
