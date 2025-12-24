package ru.practicum.service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.service.model.enums.State;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String annotation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    @ToString.Exclude
    private User initiator;

    @Embedded
    @Builder.Default
    private Location location  = new Location();

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    private Boolean paid = false;

    @Column(name = "participant_limit")
    private Integer participantLimit = 0;

    @Column(name = "request_moderation")
    private Boolean requestModeration = true;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private Set<Request> requests = new HashSet<>();

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private Set<Compilation> compilations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
