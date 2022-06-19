package net.moewes.berufsinfo.berufsfelder;

import com.microsoft.azure.storage.StorageException;
import net.moewes.Dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class BerufsfeldRepository {
    private Map<String, OdataBerufsfeld> data = new HashMap<>();

    @Inject
    Dao<Berufsfeld> dao;

    @Inject
    BerufsfeldMapper mapper;

    @PostConstruct
    public void init() {

    }

    public List<OdataBerufsfeld> getAll() {

        try {
            return dao.getAll()
                    .stream()
                    .sorted(Comparator.comparing(Berufsfeld::getName))
                    .map(mapper::toOdata)
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }


        return data.values().stream().collect(Collectors.toList());  // FIXME
    }

    public Optional<OdataBerufsfeld> find(String id) {

        try {
            Berufsfeld berufsfeld = dao.get("BF", id);
            return Optional.ofNullable(mapper.toOdata(berufsfeld));
        } catch (URISyntaxException e) {
            e.printStackTrace(); // FIXME
        } catch (StorageException e) {
            e.printStackTrace(); // FIXME
        }
        return Optional.ofNullable(data.get(id)); // TODO
    }

    public void update(OdataBerufsfeld veranstaltung) {

        Berufsfeld berufsfeld = mapper.toEntity(veranstaltung); // TODO
        berufsfeld.setRowKey(veranstaltung.getId()); // TODO
        berufsfeld.setPartitionKey("BF"); // TODO
        try {
            dao.save(berufsfeld);
        } catch (URISyntaxException e) {
            e.printStackTrace();  // FIXME
        } catch (StorageException e) {
            e.printStackTrace(); // FIXME
        }
        data.put(veranstaltung.getId(), veranstaltung);
    }

    public void delete(String id) {

        try {
            Berufsfeld berufsfeld = dao.get("BF", id);
            dao.delete(berufsfeld);
        } catch (URISyntaxException e) {
            e.printStackTrace(); // FIXME
        } catch (StorageException e) {
            e.printStackTrace(); // FIXME
        }
    }
}
