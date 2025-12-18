package ru.practicum.stats.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table()                                  //----------------------------------------
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String app;
    private String uri;
    private String ip;

    LocalDateTime timeStamp;                //--------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndpointHit)) return false;
        return id != null && id.equals(((EndpointHit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
