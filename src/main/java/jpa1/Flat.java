package jpa1;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Flats")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long NumberInTable;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double flatSquare;

    @Column(nullable = false)
    private int numberOfRooms;

    @Column(nullable = false)
    private double flatValue;

    public Flat() {
    }

    public Flat (String district, String address, double flatSquare, int numberOfRooms, double flatValue) {
        this.district = district;
        this.address = address;
        this.flatSquare = flatSquare;
        this.numberOfRooms = numberOfRooms;
        this.flatValue = flatValue;
    }

    public long getNumberInTable() {
        return NumberInTable;
    }

    public void setNumberInTable(long numberInTable) {
        NumberInTable = numberInTable;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFlatSquare() {
        return flatSquare;
    }

    public void setFlatSquare(double flatSquare) {
        this.flatSquare = flatSquare;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getFlatValue() {
        return flatValue;
    }

    public void setFlatValue(double flatValue) {
        this.flatValue = flatValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return NumberInTable == flat.NumberInTable && Double.compare(flat.flatSquare, flatSquare) == 0 && numberOfRooms == flat.numberOfRooms && Objects.equals(district, flat.district) && Objects.equals(address, flat.address) && Objects.equals(flatValue, flat.flatValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NumberInTable, district, address, flatSquare, numberOfRooms, flatValue);
    }

    @Override
    public String toString() {
        return "Flat{" +
                "NumberInTable=" + NumberInTable +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", flatSquare=" + flatSquare +
                ", numberOfRooms=" + numberOfRooms +
                ", flatValue=" + flatValue +
                '}';
    }
}
