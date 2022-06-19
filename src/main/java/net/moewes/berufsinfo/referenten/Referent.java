package net.moewes.berufsinfo.referenten;

import com.microsoft.azure.storage.table.TableServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Referent extends TableServiceEntity {

    private String name;
    private String email;

    public Referent(String pkey, String rkey) {
        super();
        setPartitionKey(pkey);
        setRowKey(rkey);
    }
}
