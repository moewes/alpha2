package net.moewes.berufsinfo.veranstaltungen;

import lombok.SneakyThrows;
import net.moewes.Dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class VeranstaltungRepository {

    public static final String PARTITION_KEY = "Berufsinformationsveranstaltung";

    private Map<String, OdataVeranstaltung> data = new HashMap<>();

    @Inject
    Dao<Veranstaltung> dao;

    @Inject
    VeranstaltungMapper mapper;

    @PostConstruct
    public void init() {

    }

    @SneakyThrows
    public List<OdataVeranstaltung> getAll() {

        return dao.getAll()
                .stream()
                .sorted(Comparator.comparing(Veranstaltung::getDatum)
                        .thenComparing(Veranstaltung::getZeit))
                .map(mapper::toOdata)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Optional<OdataVeranstaltung> find(String id) {
        Veranstaltung veranstaltung = dao.get(PARTITION_KEY, id);
        return Optional.ofNullable(mapper.toOdata(veranstaltung));
    }

    @SneakyThrows
    public void update(OdataVeranstaltung data) {
        Veranstaltung veranstaltung = mapper.toEntity(data); // TODO
        veranstaltung.setRowKey(data.getId()); // TODO
        veranstaltung.setPartitionKey(PARTITION_KEY);
        dao.save(veranstaltung);
    }

    @SneakyThrows
    public void delete(String id) {
        Veranstaltung veranstaltung = dao.get(PARTITION_KEY, id);
        dao.delete(veranstaltung);
    }
}
