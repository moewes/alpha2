package net.moewes.berufsinfo.berufsfelder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BerufsfeldMapper {
    @Mapping(source = "rowKey", target = "id")
    OdataBerufsfeld toOdata(Berufsfeld entity);

    @Mapping(source = "id", target = "rowKey")
    Berufsfeld toEntity(OdataBerufsfeld odata);
}
