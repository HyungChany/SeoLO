package com.c104.seolo.domain.marker.entity;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "marker")
public class Marker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marker_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(name = "marker_x", nullable = false)
    private Double locationX;

    @Column(name = "marker_y", nullable = false)
    private Double locationY;

    protected Marker() {}

    private Marker(Builder builder) {
        this.facility = builder.facility;
        this.machine = builder.machine;
        this.locationX = builder.locationX;
        this.locationY = builder.locationY;
    }

    public static class Builder {
        private Facility facility;
        private Machine machine;
        private double locationX;
        private double locationY;

        public Builder facility(Facility newFacility) {
            if (newFacility == null) {
                throw new IllegalArgumentException("Facility cannot be null");
            }
            this.facility = newFacility;
            return this;
        }

        public Builder machine(Machine newMachine) {
            if (newMachine == null) {
                throw new IllegalArgumentException("Machine cannot be null");
            }
            this.machine = newMachine;
            return this;
        }

        public Builder locationX(double newLocationX) {
            if (newLocationX < 0) {
                throw new IllegalArgumentException("LocationX cannot be negative");
            }
            this.locationX = newLocationX;
            return this;
        }

        public Builder locationY(double newLocationY) {
            if (newLocationY < 0) {
                throw new IllegalArgumentException("LocationY cannot be negative");
            }
            this.locationY = newLocationY;
            return this;
        }

        public Marker build() {
            return new Marker(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
