package jpa1;

import javafx.util.converter.BigDecimalStringConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add Flat");
                    System.out.println("2: add random Flats");
                    System.out.println("3: delete Flat");
                    System.out.println("4: change Flat");
                    System.out.println("5: view all Flats");
                    System.out.println("6: find Flat by parameters");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addFlat(sc);
                            break;
                        case "2":
                            insertRandomFlats(sc);
                            break;
                        case "3":
                            deleteFlat(sc);
                            break;
                        case "4":
                            changeFlat(sc);
                            break;
                        case "5":
                            viewAllFlats();
                            break;
                        case "6":
                            findFlatByParameters(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addFlat(Scanner sc) {
        System.out.print("Enter district name: ");
        String district = sc.nextLine();
        System.out.print("Enter address: ");
        String address = sc.nextLine();
        System.out.print("Enter square of flat: ");
        String sFlatSquare = sc.nextLine();
        double flatSquare = Double.parseDouble(sFlatSquare);
        System.out.print("Enter number of rooms in flat: ");
        String sNumberOfRooms = sc.nextLine();
        int numberOfRooms = Integer.parseInt(sNumberOfRooms);
        System.out.print("Enter value of flat: ");
        String sFlatValue = sc.nextLine();
        double flatValue = Double.parseDouble(sFlatValue);

        em.getTransaction().begin();
        try {
            Flat f = new Flat(district, address, flatSquare, numberOfRooms, flatValue);
            em.persist(f);
            em.getTransaction().commit();

            System.out.println("Unique_Number of Flat: " + f.getNumberInTable());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteFlat(Scanner sc) {
        System.out.print("Enter flat number: ");
        String sNumberInTable = sc.nextLine();
        long numberInTable = Long.parseLong(sNumberInTable);

        Flat f = em.getReference(Flat.class, numberInTable);
        if (f == null) {
            System.out.println("Flat not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(f);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeFlat(Scanner sc) {
        System.out.print("Enter Number of flat in Table: ");
        String unNumberS = sc.nextLine();
        long unNumber = Long.parseLong(unNumberS);

        System.out.print("Enter new value of flat: ");
        String sFlatValue = sc.nextLine();
        double flatValue = Double.parseDouble(sFlatValue);

        Flat f = null;

        try {
            Query query = em.createQuery(
                    "SELECT x FROM Flat x WHERE x.NumberInTable = :unNumber", Flat.class);
            query.setParameter("unNumber", unNumber);
            f = (Flat) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Flat not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        ///........

        em.getTransaction().begin();
        try {
            f.setFlatValue(flatValue);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomFlats(Scanner sc) {
        System.out.print("Enter flats count: ");
        String fCount = sc.nextLine();
        int count = Integer.parseInt(fCount);

        em.getTransaction().begin();
        try {
            for (int i = 0; i < count; i++) {
                Flat f = new Flat(randomDistrict(), randomAddress(), Math.round(RND.nextDouble() * 100), RND.nextInt(5), Math.round(RND.nextDouble() * 100000));
                em.persist(f);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void findFlatByParameters(Scanner sc) {

        System.out.print("Enter square of your flat: ");
        String sFlatSquare = sc.nextLine();
        double flatSquare = Double.parseDouble(sFlatSquare);
        System.out.print("Enter number of rooms in your flat: ");
        String sNumberOfRooms = sc.nextLine();
        int numberOfRooms = Integer.parseInt(sNumberOfRooms);
        System.out.print("Enter value of your flat: ");
        String sFlatValue = sc.nextLine();
        double flatValue = Double.parseDouble(sFlatValue);

        Query query = em.createQuery(
                "SELECT f FROM Flat f WHERE f.flatSquare = :flatSquare AND f.numberOfRooms = :numberOfRooms AND f.flatValue = :flatValue", Flat.class);
        query.setParameter("flatSquare", flatSquare);
        query.setParameter("numberOfRooms", numberOfRooms);
        query.setParameter("flatValue", flatValue);

        List<Flat> list = (List<Flat>) query.getResultList();

        if (list.size() != 0) {
            for (Flat f : list) {
                System.out.println(f);
            }
        } else {
            System.out.println("No such flat in database");
        }
    }

    private static void viewAllFlats() {
        Query query = em.createQuery(
                "SELECT f FROM Flat f", Flat.class);
        List<Flat> list = (List<Flat>) query.getResultList();

        for (Flat f : list)
            System.out.println(f);
    }

    static final String[] DISTRICS = {"Solomyanskiy", "Podolskiy", "Schevchenkovskiy", "Pecherskiy"};
    static final String[] ADDRESSES = {"Kreschatyk 19", "Kreschatyk 20", "Kreschatyk 21"};

    static final Random RND = new Random();

    static String randomDistrict() {
        return DISTRICS[RND.nextInt(DISTRICS.length)];
    }

    static String randomAddress() {
        return ADDRESSES[RND.nextInt(ADDRESSES.length)];
    }
}


