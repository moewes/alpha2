package net.moewes.berufsinfo.berufsfelder;


import com.microsoft.azure.storage.table.TableServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Berufsfeld extends TableServiceEntity {

    private String name = "";

    public Berufsfeld(String pkey, String rkey) {
        super();
        setPartitionKey(pkey);
        setRowKey(rkey);
    }
}
