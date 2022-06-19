package net.moewes.berufsinfo.stat;

import com.microsoft.azure.storage.table.TableServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Anmeldung extends TableServiceEntity {

    // private String name; // FIXME
    // private String email;
    // private String token;
    private String datum;
    private String zeit;

    public Anmeldung(String pkey, String rkey) {
        super();
        setPartitionKey(pkey);
        setRowKey(rkey);
    }
}
