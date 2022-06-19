package net.moewes.berufsinfo.veranstaltungen;

import lombok.Data;
import net.moewes.quarkus.odata.annotations.EntityKey;
import net.moewes.quarkus.odata.annotations.ODataEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ODataEntity(OdataVeranstaltung.ENTITY_NAME)
public class OdataVeranstaltung {

    public static final String ENTITY_NAME = "Veranstaltung";

    @EntityKey
    private String id;

    private String beschreibung;
    private LocalDate datum = LocalDate.now();
    private LocalTime zeit = LocalTime.of(12, 45);

    private String berufsfeld;
    private String referent;
}
