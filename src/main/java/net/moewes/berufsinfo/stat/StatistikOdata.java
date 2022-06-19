package net.moewes.berufsinfo.stat;

import lombok.Data;
import net.moewes.quarkus.odata.annotations.EntityKey;
import net.moewes.quarkus.odata.annotations.ODataEntity;

@ODataEntity("Anmeldung")
@Data
public class StatistikOdata {

    @EntityKey
    private String id;

    private String datum;
    private String zeit;
    private String referent;
    private String berufsfeld;
    private int anmeldungen;
}
