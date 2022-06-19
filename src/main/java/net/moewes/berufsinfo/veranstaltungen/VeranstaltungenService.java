package net.moewes.berufsinfo.veranstaltungen;

import net.moewes.quarkus.odata.EntityCollectionProvider;
import net.moewes.quarkus.odata.EntityProvider;
import net.moewes.quarkus.odata.annotations.ODataService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ODataService(value = "Veranstaltungen", entityType = OdataVeranstaltung.ENTITY_NAME)
public class VeranstaltungenService implements EntityProvider<OdataVeranstaltung>,
        EntityCollectionProvider<OdataVeranstaltung> {

    @Inject
    VeranstaltungRepository repository;

    @Override
    public List<OdataVeranstaltung> getCollection() {
        return repository.getAll();
    }

    @Override
    public Optional<OdataVeranstaltung> find(Map<String, String> keys) {

        String id = keys.get("Id");
        return repository.find(id);
    }

    @Override
    public OdataVeranstaltung create(Object entity) {

        OdataVeranstaltung veranstaltung = null;

        if (entity instanceof OdataVeranstaltung) {
            veranstaltung = (OdataVeranstaltung) entity;
            veranstaltung.setId(UUID.randomUUID().toString());
            repository.update(veranstaltung);
        }
        return veranstaltung;
    }

    @Override
    public void update(Map<String, String> keys, Object entity) {

        OdataVeranstaltung veranstaltung;

        String id = keys.get("Id");
        if (entity instanceof OdataVeranstaltung) {
            veranstaltung = (OdataVeranstaltung) entity;
            veranstaltung.setId(id);
            repository.update(veranstaltung);
        }
    }

    @Override
    public void delete(Map<String, String> keys) {
        String id = keys.get("Id");
        repository.delete(id);
    }
}
