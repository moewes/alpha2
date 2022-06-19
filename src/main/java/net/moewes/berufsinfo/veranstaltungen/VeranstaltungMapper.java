package net.moewes.berufsinfo.veranstaltungen;

import net.moewes.berufsinfo.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", uses = {DateTimeMapper.class}, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface VeranstaltungMapper {
    @Mapping(source = "rowKey", target = "id")
    OdataVeranstaltung toOdata(Veranstaltung entity);

    @Mapping(source = "id", target = "rowKey")
    Veranstaltung toEntity(OdataVeranstaltung odata);
}
