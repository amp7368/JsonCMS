package apple.web.cms.model.image;

import apple.web.cms.model.raw.DJsonDocument;
import io.ebean.Model;
import io.ebean.annotation.Index;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class DImage extends Model {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String path;
    @Index
    @Column(nullable = false)
    private int version;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
    private DJsonDocument meta;
    private String filename;
}
