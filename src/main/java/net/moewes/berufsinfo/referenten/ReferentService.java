package net.moewes.berufsinfo.referenten;

import net.moewes.quarkus.odata.EntityCollectionProvider;
import net.moewes.quarkus.odata.EntityProvider;
import net.moewes.quarkus.odata.annotations.ODataAction;
import net.moewes.quarkus.odata.annotations.ODataService;
import org.apache.olingo.server.api.ODataApplicationException;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ODataService(value = ReferentService.SERVICE_NAME, entityType = OdataReferent.ENTITY_NAME)
public class ReferentService implements EntityProvider<OdataReferent>,
        EntityCollectionProvider<OdataReferent> {

    public static final String SERVICE_NAME = "Referenten";

    @Inject
    ReferentRepository repository;

    @Inject
    RegisterService mailService;

    @Override
    public List<OdataReferent> getCollection() {
        return repository.getAll();
    }

    @Override
    public Optional<OdataReferent> find(Map<String, String> keys) {

        String id = keys.get("Id");
        return repository.find(id);
    }

    @Override
    public OdataReferent create(Object entity) {
        OdataReferent referent = null;

        if (entity instanceof OdataReferent) {
            referent = (OdataReferent) entity;
            referent.setId(UUID.randomUUID().toString());
            repository.update(referent);
        }
        return referent;
    }

    @Override
    public void update(Map<String, String> keys, Object entity) {
        OdataReferent referent;

        String id = keys.get("Id");
        if (entity instanceof OdataReferent) {
            referent = (OdataReferent) entity;
            referent.setId(id);
            repository.update(referent);
        }
    }

    @Override
    public void delete(Map<String, String> keys) {

        String id = keys.get("Id");
        repository.delete(id);
    }

    @ODataAction
    public String sendMail(OdataReferent referent) {

        try {
            mailService.sendMail(referent);
        } catch (ODataApplicationException e) {
            e.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
}
