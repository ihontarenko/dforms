package df.base.dto.form.support;

public enum Package {
    DIP("DIP", "Dual In-line Package", "Traditional package with two rows of pins"),
    SIP("SIP", "Single In-line Package", "Package with a single row of pins"),
    TO92("TO-92", "Transistor Outline 92", "Compact package for small transistors"),
    TO220("TO-220", "Transistor Outline 220", "Package for high-power transistors and voltage regulators"),
    TO3("TO-3", "Transistor Outline 3", "Metal can package for high-power transistors"),
    TO126("TO-126", "Transistor Outline 126", "Compact package for medium-power transistors"),
    TO247("TO-247", "Transistor Outline 247", "Package for high-power semiconductors"),
    TO264("TO-264", "Transistor Outline 264", "Larger package for high-power devices"),
    SOIC("SOIC", "Small Outline Integrated Circuit", "Small package for surface mounting"),
    SSOP("SSOP", "Shrink Small Outline Package", "Compact surface mount package with smaller pin spacing"),
    TSSOP("TSSOP", "Thin Shrink Small Outline Package", "Thin surface mount package"),
    SOT23("SOT-23", "Small Outline Transistor", "Small surface mount package for transistors"),
    SOT223("SOT-223", "Small Outline Transistor", "Larger surface mount package for higher power components"),
    SOT89("SOT-89", "Small Outline Transistor 89", "Surface mount package for medium-power components"),
    QFP("QFP", "Quad Flat Package", "Flat package with pins on all four sides"),
    LQFP("LQFP", "Low-profile Quad Flat Package", "Low-profile version of QFP"),
    TQFP("TQFP", "Thin Quad Flat Package", "Thin version of QFP"),
    PQFP("PQFP", "Plastic Quad Flat Package", "Plastic version of QFP"),
    BGA("BGA", "Ball Grid Array", "Package with solder balls on the bottom"),
    LGA("LGA", "Land Grid Array", "Package with flat contacts on the bottom"),
    PGA("PGA", "Pin Grid Array", "Package with a grid of pins on the bottom"),
    DFN("DFN", "Dual Flat No-leads", "Small surface mount package without leads"),
    QFN("QFN", "Quad Flat No-leads", "Flat package without leads on all four sides"),
    CSP("CSP", "Chip Scale Package", "Small package with minimal footprint"),
    WLCSP("WLCSP", "Wafer-Level Chip Scale Package", "Package processed at wafer level for high-density packaging"),
    COB("COB", "Chip on Board", "Bare chip mounted directly on a PCB"),
    DSBGA("DSBGA", "Die Size Ball Grid Array", "Smallest form of BGA package"),
    TSOP("TSOP", "Thin Small Outline Package", "Thin version of SOP with higher pin density"),
    PLCC("PLCC", "Plastic Leaded Chip Carrier", "Plastic package with leads on all four sides"),
    CLCC("CLCC", "Ceramic Leadless Chip Carrier", "Ceramic version of PLCC without leads"),
    DIP8("DIP-8", "Dual In-line Package with 8 pins", "Standard DIP package with 8 pins"),
    DIP16("DIP-16", "Dual In-line Package with 16 pins", "Standard DIP package with 16 pins"),
    DIP40("DIP-40", "Dual In-line Package with 40 pins", "Large DIP package with 40 pins"),
    SOJ("SOJ", "Small Outline J-lead", "Package with J-shaped leads for surface mounting"),
    ZIP("ZIP", "Zigzag In-line Package", "Package with zigzagged pins for compact design"),
    QIL("QIL", "Quad In-line", "Package with four rows of pins"),
    MCM("MCM", "Multi-Chip Module", "Module housing multiple semiconductor dies"),
    PGA370("PGA-370", "Pin Grid Array 370", "Package used for Intel processors"),
    CPGA("CPGA", "Ceramic Pin Grid Array", "Ceramic package with pin grid array"),
    FCBGA("FCBGA", "Flip Chip Ball Grid Array", "BGA package with flip-chip technology"),
    FLIPCHIP("FLIPCHIP", "Flip Chip", "Direct chip attachment to the PCB"),
    DIP14("DIP-14", "Dual In-line Package with 14 pins", "Standard DIP package with 14 pins"),
    DIP24("DIP-24", "Dual In-line Package with 24 pins", "DIP package with 24 pins"),
    PDIP("PDIP", "Plastic Dual In-line Package", "Plastic version of DIP"),
    CDIP("CDIP", "Ceramic Dual In-line Package", "Ceramic version of DIP"),
    SDIP("SDIP", "Shrink Dual In-line Package", "Smaller version of DIP"),
    LDCC("LDCC", "Leadless Ceramic Chip Carrier", "Ceramic package without leads"),
    HLCC("HLCC", "Hermetic Leadless Chip Carrier", "Hermetically sealed chip carrier without leads"),
    LCC("LCC", "Leadless Chip Carrier", "Chip carrier without external leads"),
    CLGA("CLGA", "Ceramic Land Grid Array", "Ceramic version of LGA"),
    UDFN("UDFN", "Ultra-thin Dual Flat No-leads", "Ultra-thin version of DFN"),
    WLP("WLP", "Wafer-Level Package", "Small package for wafer-level processing"),
    PDIP8("PDIP-8", "Plastic Dual In-line Package with 8 pins", "Plastic version of DIP-8"),
    TLLGA("TLLGA", "Thin Low-Profile Leadless Grid Array", "Thin version of LGA"),
    VFBGA("VFBGA", "Very Fine Pitch Ball Grid Array", "BGA package with very fine pitch for high-density designs"),
    VQFN("VQFN", "Very Thin Quad Flat No-leads", "Ultra-thin version of QFN"),
    FFPGA("FFPGA", "Fine Pitch Pin Grid Array", "PGA package with fine pitch"),
    SOP("SOP", "Small Outline Package", "Standard surface mount package with leads on two sides"),
    HSOP("HSOP", "Heat Sink Small Outline Package", "SOP with a heat sink"),
    TSOP1("TSOP-1", "Thin Small Outline Package 1", "TSOP with a specific pin arrangement"),
    TSOP2("TSOP-2", "Thin Small Outline Package 2", "TSOP with a different pin arrangement"),
    QFJ("QFJ", "Quad Flat J-lead", "Flat package with J-shaped leads on four sides"),
    VSOP("VSOP", "Very Small Outline Package", "Smaller version of SOP"),
    MSOP("MSOP", "Mini Small Outline Package", "Miniature SOP"),
    SOP16("SOP-16", "Small Outline Package with 16 pins", "SOP with 16 pins"),
    SSOP20("SSOP-20", "Shrink Small Outline Package with 20 pins", "SSOP with 20 pins"),
    SSOP28("SSOP-28", "Shrink Small Outline Package with 28 pins", "SSOP with 28 pins"),
    LCC20("LCC-20", "Leadless Chip Carrier with 20 pins", "LCC package with 20 pins"),
    WQFN("WQFN", "Wettable Flank Quad Flat No-leads", "QFN package with wettable flanks for soldering"),
    DFN8("DFN-8", "Dual Flat No-leads with 8 pins", "DFN package with 8 pins"),
    DFN10("DFN-10", "Dual Flat No-leads with 10 pins", "DFN package with 10 pins"),
    DFN12("DFN-12", "Dual Flat No-leads with 12 pins", "DFN package with 12 pins"),
    DFN16("DFN-16", "Dual Flat No-leads with 16 pins", "DFN package with 16 pins"),
    MLF("MLF", "Micro Leadframe Package", "Small leadframe-based package"),
    MLP("MLP", "Micro Leadless Package", "Leadless package with small form factor"),
    SOIC16("SOIC-16", "Small Outline Integrated Circuit with 16 pins", "SOIC package with 16 pins"),
    TO18("TO-18", "Transistor Outline 18", "Small metal can package for transistors"),
    TO39("TO-39", "Transistor Outline 39", "Medium-sized metal can package for transistors"),
    DPAK("DPAK", "Discrete Package", "Package for discrete power devices"),
    IPAK("IPAK", "Insulated Package", "Package for insulated power devices"),
    SMD("SMD", "Surface Mount Device", "General package for surface mounted devices"),
    TO263("TO-263", "Transistor Outline 263", "Surface-mount version of TO-220 for power components"),
    TO252("TO-252", "Transistor Outline 252", "Compact surface-mount package for power components"),
    TO251("TO-251", "Transistor Outline 251", "Smaller version of TO-252"),
    SMDP("SMD-P", "Surface Mount Device Plastic", "Plastic surface mount package"),
    SMDL("SMD-L", "Surface Mount Device Leaded", "Surface mount device with leads"),
    MQFP("MQFP", "Metric Quad Flat Package", "QFP package with metric lead spacing"),
    HQFP("HQFP", "High-density Quad Flat Package", "QFP package for high pin count and density"),
    XQFP("XQFP", "eXtra Thin Quad Flat Package", "Extra thin version of QFP"),
    MBGA("MBGA", "Micro Ball Grid Array", "Miniaturized version of BGA"),
    TBGA("TBGA", "Thin Ball Grid Array", "Thin version of BGA for space-saving designs"),
    UBGA("UBGA", "Ultra Ball Grid Array", "Ultra-thin BGA for dense packaging"),
    CBGA("CBGA", "Ceramic Ball Grid Array", "Ceramic version of BGA"),
    TEBGA("TEBGA", "Tape Embedded Ball Grid Array", "BGA with tape for better thermal performance"),
    PBGA("PBGA", "Plastic Ball Grid Array", "Plastic version of BGA"),
    POP("PoP", "Package on Package", "Stacked packaging for multiple components"),
    COF("COF", "Chip on Film", "Chip bonded directly onto a flexible film"),
    COG("COG", "Chip on Glass", "Chip mounted directly onto glass"),
    DIL("DIL", "Dual In-line", "Package with two rows of pins"),
    LCC32("LCC-32", "Leadless Chip Carrier with 32 pins", "Leadless chip carrier with 32 pins"),
    LCC44("LCC-44", "Leadless Chip Carrier with 44 pins", "Leadless chip carrier with 44 pins"),
    LCC68("LCC-68", "Leadless Chip Carrier with 68 pins", "Leadless chip carrier with 68 pins"),
    QFN24("QFN-24", "Quad Flat No-leads with 24 pins", "QFN package with 24 pins"),
    QFN32("QFN-32", "Quad Flat No-leads with 32 pins", "QFN package with 32 pins"),
    QFN48("QFN-48", "Quad Flat No-leads with 48 pins", "QFN package with 48 pins"),
    MLF32("MLF-32", "Micro Leadframe Package with 32 pins", "MLF package with 32 pins"),
    MLF64("MLF-64", "Micro Leadframe Package with 64 pins", "MLF package with 64 pins"),
    BQFP("BQFP", "Bumpered Quad Flat Package", "QFP with protective corners"),
    QFJ64("QFJ-64", "Quad Flat J-lead with 64 pins", "QFJ package with 64 pins"),
    DIP64("DIP-64", "Dual In-line Package with 64 pins", "Large DIP package with 64 pins"),
    DIP32("DIP-32", "Dual In-line Package with 32 pins", "DIP package with 32 pins"),
    PGA132("PGA-132", "Pin Grid Array with 132 pins", "PGA package with 132 pins"),
    PGA256("PGA-256", "Pin Grid Array with 256 pins", "Large PGA package with 256 pins"),
    BGA144("BGA-144", "Ball Grid Array with 144 balls", "BGA package with 144 balls"),
    BGA256("BGA-256", "Ball Grid Array with 256 balls", "Large BGA package with 256 balls"),
    FQFP("FQFP", "Fine Pitch Quad Flat Package", "QFP with fine pitch for high pin counts"),
    VFBGA96("VFBGA-96", "Very Fine Pitch Ball Grid Array with 96 balls", "VFBGA package with 96 balls"),
    TFBGA("TFBGA", "Thin Fine Ball Grid Array", "Thin fine-pitch BGA for space-constrained applications"),
    QFJ44("QFJ-44", "Quad Flat J-lead with 44 pins", "QFJ package with 44 pins"),
    LGA1155("LGA-1155", "Land Grid Array with 1155 contacts", "LGA used in Intel processors"),
    LGA1366("LGA-1366", "Land Grid Array with 1366 contacts", "LGA for high-performance processors"),
    LGA2011("LGA-2011", "Land Grid Array with 2011 contacts", "LGA for high-end processors"),
    CLCC68("CLCC-68", "Ceramic Leadless Chip Carrier with 68 pins", "CLCC package with 68 pins"),
    BCC("BCC", "Bump Chip Carrier", "Chip carrier with bump contacts for high-speed applications"),
    DFN24("DFN-24", "Dual Flat No-leads with 24 pins", "DFN package with 24 pins"),
    BGA100("BGA-100", "Ball Grid Array with 100 balls", "BGA package with 100 balls"),
    CSP36("CSP-36", "Chip Scale Package with 36 balls", "CSP package with 36 balls"),
    TDFN("TDFN", "Thin Dual Flat No-leads", "Thin version of DFN"),
    VDFN("VDFN", "Very Thin Dual Flat No-leads", "Very thin version of DFN"),
    FCLGA("FCLGA", "Flip Chip Land Grid Array", "LGA with flip chip technology");

    private final String code;
    private final String name;
    private final String description;

    Package(String code, String name, String description) {
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

    public String getCodeWithName() {
        return "%s (%s)".formatted(getCode(), getName());
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %s", code, name, description);
    }

}