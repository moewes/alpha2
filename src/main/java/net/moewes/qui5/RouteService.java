package net.moewes.qui5;

import net.moewes.quarkus.odata.EntityCollectionProvider;
import net.moewes.quarkus.odata.annotations.ODataService;

import java.util.ArrayList;
import java.util.List;

@ODataService(value = "Routes",entityType = "Route")
public class RouteService implements EntityCollectionProvider<ODataUi5Route> {

    @Override
    public List<ODataUi5Route> getCollection() {

        List<ODataUi5Route> routes = new ArrayList<>();

        ODataUi5Route referentenRoute = new ODataUi5Route();
        referentenRoute.setId("referenten");
        referentenRoute.setPattern("Referenten");
        referentenRoute.setView("Referenten");

        routes.add(referentenRoute);

        return routes;
    }
}
