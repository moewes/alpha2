package net.moewes.berufsinfo.referenten;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ReferentMapper {
    @Mapping(source = "rowKey", target = "id")
    OdataReferent toOdata(Referent entity);

    @Mapping(source = "id", target = "rowKey")
    Referent toEntity(OdataReferent odata);
}
