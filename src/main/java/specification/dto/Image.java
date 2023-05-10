package specification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@ToString(of = {"id", "name", "size"})
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String mimeType;
    private long size;
    private byte[] content;
}
