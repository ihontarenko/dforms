package df.application.dto.form.support;

public enum ARM {
    ARMv1("ARMv1", "First ARM architecture", "The first ARM architecture used in the ARM1 processor."),
    ARMv2("ARMv2", "Second ARM architecture", "Used in ARM2 and ARM3 processors, introducing multiply instruction."),
    ARMv3("ARMv3", "Third ARM architecture", "Used in ARM6 and ARM7 processors, introducing 32-bit addressing."),
    ARMv4("ARMv4", "Fourth ARM architecture", "Introduced the Thumb instruction set and was used in ARM7TDMI."),
    ARMv5("ARMv5", "Fifth ARM architecture", "Added Jazelle for Java bytecode execution and improved instruction pipelining."),
    ARMv6("ARMv6", "Sixth ARM architecture", "Introduced SIMD instructions and TrustZone technology."),
    ARMv7_A("ARMv7-A", "ARMv7-A Application", "Used in high-performance application processors, introducing NEON and VFPv3."),
    ARMv7_R("ARMv7-R", "ARMv7-R Real-time", "Optimized for real-time systems, used in Cortex-R processors."),
    ARMv7_M("ARMv7-M", "ARMv7-M Microcontroller", "Designed for microcontrollers, used in Cortex-M processors."),
    ARMv7E_M("ARMv7E-M", "ARMv7E-M Enhanced Microcontroller", "An enhanced version of ARMv7-M with DSP instructions."),
    ARMv8_A("ARMv8-A", "ARMv8-A Application", "First 64-bit architecture, introduced AArch64 mode."),
    ARMv8_R("ARMv8-R", "ARMv8-R Real-time", "Real-time version of ARMv8 with safety features, used in Cortex-R processors."),
    ARMv8_M("ARMv8-M", "ARMv8-M Microcontroller", "Added security features to microcontroller profile, including TrustZone."),
    ARMv8_1_A("ARMv8.1-A", "ARMv8.1-A Application", "Improved memory management and virtualization features."),
    ARMv8_2_A("ARMv8.2-A", "ARMv8.2-A Application", "Introduced half-precision floating-point support and improved atomic operations."),
    ARMv8_3_A("ARMv8.3-A", "ARMv8.3-A Application", "Added pointer authentication for security."),
    ARMv8_4_A("ARMv8.4-A", "ARMv8.4-A Application", "Introduced memory tagging for improved memory safety."),
    ARMv8_5_A("ARMv8.5-A", "ARMv8.5-A Application", "Added Branch Target Identification and memory protection improvements."),
    ARMv8_6_A("ARMv8.6-A", "ARMv8.6-A Application", "Introduced enhanced floating-point and matrix multiplication instructions."),
    ARMv8_7_A("ARMv8.7-A", "ARMv8.7-A Application", "Further improvements in memory operations and security features."),
    ARMv9_A("ARMv9-A", "ARMv9-A Application", "Next-generation architecture with enhanced security and machine learning features."),
    ARMv9_1_A("ARMv9.1-A", "ARMv9.1-A Application", "Further improvements for 5G, AI, and data processing."),
    ARMv9_2_A("ARMv9.2-A", "ARMv9.2-A Application", "Enhanced security features and efficiency for AI-driven workloads."),
    ARMv9_R("ARMv9-R", "ARMv9-R Real-time", "Next-generation real-time architecture for Cortex-R processors."),
    ARMv9_M("ARMv9-M", "ARMv9-M Microcontroller", "Enhanced microcontroller architecture with security and real-time improvements."),

    CORTEX_A5("Cortex-A5", "Cortex-A5", "Low-power application processor with ARMv7-A architecture."),
    CORTEX_A7("Cortex-A7", "Cortex-A7", "Power-efficient application processor with ARMv7-A architecture."),
    CORTEX_A8("Cortex-A8", "Cortex-A8", "High-performance ARMv7-A processor for mobile and embedded devices."),
    CORTEX_A9("Cortex-A9", "Cortex-A9", "Multi-core capable ARMv7-A processor for high-performance applications."),
    CORTEX_A15("Cortex-A15", "Cortex-A15", "High-performance application processor with ARMv7-A architecture."),
    CORTEX_A17("Cortex-A17", "Cortex-A17", "ARMv7-A processor focused on mid-range performance and efficiency."),
    CORTEX_A35("Cortex-A35", "Cortex-A35", "Ultra-efficient 64-bit ARMv8-A processor for low-power devices."),
    CORTEX_A53("Cortex-A53", "Cortex-A53", "64-bit ARMv8-A processor focused on power efficiency."),
    CORTEX_A57("Cortex-A57", "Cortex-A57", "High-performance 64-bit processor with ARMv8-A architecture."),
    CORTEX_A72("Cortex-A72", "Cortex-A72", "High-performance ARMv8-A processor for premium mobile and server devices."),
    CORTEX_A73("Cortex-A73", "Cortex-A73", "Efficient 64-bit processor optimized for mobile devices."),
    CORTEX_A75("Cortex-A75", "Cortex-A75", "Next-generation ARMv8-A processor for high-performance workloads."),
    CORTEX_A76("Cortex-A76", "Cortex-A76", "High-performance ARMv8.2-A processor for premium devices."),
    CORTEX_A77("Cortex-A77", "Cortex-A77", "Improved performance ARMv8.2-A processor with AI and ML optimizations."),
    CORTEX_A78("Cortex-A78", "Cortex-A78", "Latest ARMv8.2-A processor with optimized power efficiency."),
    CORTEX_X1("Cortex-X1", "Cortex-X1", "High-performance custom ARMv8.2-A processor for premium devices."),

    CORTEX_R4("Cortex-R4", "Cortex-R4", "Real-time processor with ARMv7-R architecture for automotive and industrial systems."),
    CORTEX_R5("Cortex-R5", "Cortex-R5", "Enhanced real-time processor for safety-critical applications."),
    CORTEX_R7("Cortex-R7", "Cortex-R7", "High-performance real-time processor for safety and high-reliability systems."),
    CORTEX_R8("Cortex-R8", "Cortex-R8", "Latest real-time processor for 5G and automotive applications."),

    CORTEX_M0("Cortex-M0", "Cortex-M0", "Ultra-low-power ARMv6-M processor for microcontrollers."),
    CORTEX_M0_PLUS("Cortex-M0+", "Cortex-M0+", "Enhanced version of Cortex-M0 with improved energy efficiency."),
    CORTEX_M3("Cortex-M3", "Cortex-M3", "High-performance ARMv7-M processor for microcontrollers."),
    CORTEX_M4("Cortex-M4", "Cortex-M4", "ARMv7E-M processor with DSP extensions for microcontrollers."),
    CORTEX_M7("Cortex-M7", "Cortex-M7", "High-performance ARMv7E-M microcontroller with advanced DSP instructions."),
    CORTEX_M23("Cortex-M23", "Cortex-M23", "Ultra-low-power ARMv8-M processor with TrustZone security."),
    CORTEX_M33("Cortex-M33", "Cortex-M33", "Secure ARMv8-M processor with TrustZone for IoT applications."),
    CORTEX_M35P("Cortex-M35P", "Cortex-M35P", "ARMv8-M processor with enhanced physical security features."),

    NEOVERSE_N1("Neoverse-N1", "Neoverse N1", "Infrastructure-class ARMv8.2-A processor for high-performance servers and cloud computing."),
    NEOVERSE_V1("Neoverse-V1", "Neoverse V1", "High-performance ARMv8.4-A processor optimized for data centers and AI workloads."),
    NEOVERSE_N2("Neoverse-N2", "Neoverse N2", "ARMv9-A infrastructure processor for scalable cloud and 5G applications."),
    NEOVERSE_E1("Neoverse-E1", "Neoverse E1", "Optimized ARMv8.2-A processor for edge computing and 5G infrastructure.");

    private final String code;
    private final String name;
    private final String description;

    ARM(String code, String name, String description) {
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

}