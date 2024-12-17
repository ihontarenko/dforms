package df.base.mapping.reflection;

import df.base.common.mapping.Mapper;
import df.base.dto.reflection.PackageDTO;

public class PackageMapper implements Mapper<Package, PackageDTO> {

    @Override
    public PackageDTO map(Package source) {
        PackageDTO packageDTO = new PackageDTO();

        packageDTO.setName(source.getName());
        packageDTO.setNativePackage(source);

        return packageDTO;
    }

    @Override
    public Package reverse(PackageDTO source) {
        throw new UnsupportedOperationException();
    }

}
