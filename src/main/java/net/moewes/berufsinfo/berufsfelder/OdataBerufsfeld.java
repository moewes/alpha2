package net.moewes.berufsinfo.berufsfelder;

import lombok.Data;
import net.moewes.quarkus.odata.annotations.EntityKey;
import net.moewes.quarkus.odata.annotations.ODataEntity;

@Data
@ODataEntity("Berufsfeld")
public class OdataBerufsfeld {

    //public static final String ENTITY_NAME = "Berufsfeld";

    @EntityKey
    private String id;

    private String name;
}
