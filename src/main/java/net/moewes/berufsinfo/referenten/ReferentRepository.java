package net.moewes.berufsinfo.referenten;

import lombok.SneakyThrows;
import net.moewes.Dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReferentRepository {

    public static final String PARTITION_KEY = "R";
    private Map<String, OdataReferent> data = new HashMap<>();

    @Inject
    Dao<Referent> dao;

    @Inject
    ReferentMapper mapper;

    @PostConstruct
    public void init() {
        
    }

    @SneakyThrows
    public List<OdataReferent> getAll() {

        return dao.getAll()
                .stream()
                .sorted(Comparator.comparing(Referent::getName))
                .map(mapper::toOdata)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Optional<OdataReferent> find(String id) {

        Referent referent = dao.get(PARTITION_KEY, id);
        return Optional.ofNullable(mapper.toOdata(referent));
    }

    @SneakyThrows
    public void update(OdataReferent data) {

        Referent referent = mapper.toEntity(data); // TODO
        referent.setRowKey(data.getId()); // TODO
        referent.setPartitionKey(PARTITION_KEY);
        dao.save(referent);
    }

    @SneakyThrows
    public void delete(String id) {
        Referent referent = dao.get(PARTITION_KEY, id);
        dao.delete(referent);
    }
}
