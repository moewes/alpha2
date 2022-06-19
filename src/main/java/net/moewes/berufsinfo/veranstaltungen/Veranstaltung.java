package net.moewes.berufsinfo.veranstaltungen;

import com.microsoft.azure.storage.table.TableServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Veranstaltung extends TableServiceEntity {

    private String beschreibung;
    private String datum;
    private String zeit;

    private String berufsfeld;
    private String referent;

    public Veranstaltung(String pkey, String rkey) {
        super();
        setPartitionKey(pkey);
        setRowKey(rkey);
    }
}
