package net.moewes.berufsinfo.stat;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.TableQuery;
import net.moewes.Dao;
import net.moewes.berufsinfo.berufsfelder.Berufsfeld;
import net.moewes.berufsinfo.referenten.Referent;
import net.moewes.berufsinfo.veranstaltungen.Veranstaltung;
import net.moewes.quarkus.odata.EntityCollectionProvider;
import net.moewes.quarkus.odata.annotations.ODataService;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ODataService(value = "Anmeldungen", entityType = "Anmeldung")
public class StatistikService implements EntityCollectionProvider<StatistikOdata> {

    @Inject
    Dao<Veranstaltung> veranstaltungDao;

    @Inject
    Dao<Anmeldung> anmeldungDao;

    @Inject
    Dao<Referent> referentDao;

    @Inject
    Dao<Berufsfeld> berufsfeldDao;

    @Override
    public List<StatistikOdata> getCollection() {

        List<StatistikOdata> result = new ArrayList<>();

        List<Veranstaltung> veranstaltungList = null;
        try {
            veranstaltungList = veranstaltungDao.getAll();

            veranstaltungList.sort(Comparator.comparing(Veranstaltung::getDatum)
                    .thenComparing(Veranstaltung::getZeit));

            veranstaltungList.forEach(veranstaltung -> {
                StatistikOdata resultItem = new StatistikOdata();
                resultItem.setId(veranstaltung.getRowKey());
                resultItem.setDatum(formatDate(veranstaltung.getDatum()));
                resultItem.setZeit(formatZeit(veranstaltung.getZeit()));
                resultItem.setBerufsfeld(getBerufsfeldName(veranstaltung.getBerufsfeld()));
                resultItem.setReferent(getReferentName(veranstaltung.getReferent()));
                resultItem.setAnmeldungen(getAnmeldungen(veranstaltung.getRowKey()));
                result.add(resultItem);
            });
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }


        return result;
    }

    private int getAnmeldungen(String rowKey) {

        int result = 0;
        try {
            TableQuery<Anmeldung> query =
                    TableQuery.from(Anmeldung.class)
                            .where("PartitionKey eq '" + rowKey + "'");
            List<Anmeldung> queryResult = anmeldungDao.query(query);

            for (Anmeldung anmeldung : queryResult) {
                result++;
            }
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String formatZeit(String zeit) {
        return zeit.substring(0, 5);
    }

    private String formatDate(String datum) {
        String[] parts = datum.split("-");
        return parts[2] + "." + parts[1] + "." + parts[0];
    }

    private String getReferentName(String referentId) {

        try {
            Referent referent = referentDao.get("R", referentId);
            return referent.getName();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getBerufsfeldName(String berufsfeldId) {
        try {
            Berufsfeld berufsfeld = berufsfeldDao.get("BF", berufsfeldId);
            return berufsfeld.getName();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
            return "";
        }
    }
}
