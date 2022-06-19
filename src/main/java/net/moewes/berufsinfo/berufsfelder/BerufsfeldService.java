package net.moewes.berufsinfo.berufsfelder;

import net.moewes.quarkus.odata.EntityCollectionProvider;
import net.moewes.quarkus.odata.EntityProvider;
import net.moewes.quarkus.odata.annotations.ODataService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ODataService(value = BerufsfeldService.SERVICE_NAME, entityType = "Berufsfeld")
public class BerufsfeldService implements EntityProvider<OdataBerufsfeld>,
        EntityCollectionProvider<OdataBerufsfeld> {

    public static final String SERVICE_NAME = "Berufsfelder";

    @Inject
    BerufsfeldRepository repository;

    @Override
    public List<OdataBerufsfeld> getCollection() {
        return repository.getAll();
    }

    @Override
    public Optional<OdataBerufsfeld> find(Map<String, String> keys) {

        String id = keys.get("Id");
        return repository.find(id);
    }

    @Override
    public OdataBerufsfeld create(Object entity) {
        OdataBerufsfeld berufsfeld = null;

        if (entity instanceof OdataBerufsfeld) {
            berufsfeld = (OdataBerufsfeld) entity;
            berufsfeld.setId(UUID.randomUUID().toString());
            repository.update(berufsfeld);
        }
        return berufsfeld;
    }

    @Override
    public void update(Map<String, String> keys, Object entity) {
        OdataBerufsfeld berufsfeld;

        String id = keys.get("Id");
        if (entity instanceof OdataBerufsfeld) {
            berufsfeld = (OdataBerufsfeld) entity;
            berufsfeld.setId(id);
            repository.update(berufsfeld);
        }
    }

    @Override
    public void delete(Map<String, String> keys) {

        String id = keys.get("Id");
        repository.delete(id);
    }
}
