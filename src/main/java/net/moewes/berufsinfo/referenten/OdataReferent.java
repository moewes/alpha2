package net.moewes.berufsinfo.referenten;

import lombok.Data;
import net.moewes.quarkus.odata.annotations.EntityKey;
import net.moewes.quarkus.odata.annotations.ODataEntity;

@Data
@ODataEntity(OdataReferent.ENTITY_NAME)
public class OdataReferent {

    public static final String ENTITY_NAME = "Referent";

    @EntityKey
    private String id;

    private String name;
    private String email;
}
