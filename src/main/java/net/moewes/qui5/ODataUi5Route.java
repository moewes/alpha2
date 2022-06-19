package net.moewes.qui5;

import lombok.Data;
import net.moewes.quarkus.odata.annotations.EntityKey;
import net.moewes.quarkus.odata.annotations.ODataEntity;

@Data
@ODataEntity("Route")
public class ODataUi5Route {

    @EntityKey
    private String id;
    private String view;
    private String pattern;
}
