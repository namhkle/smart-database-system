import java.util.Random;

public class Queries {
    private static final String READ_QUERY  = "read";
    private static final String WRITE_QUERY = "write";
    private static final String[] READ_QUERIES = {
            "SELECT * FROM actor;",
            "SELECT customer_id, store_id FROM customer",
            "SELECT customer_id, store_id, first_name, last_name FROM customer " +
                    "LIMIT 25 OFFSET 10",
            "SELECT * FROM staff WHERE first_name='Jon'",
            "SELECT title, rental_rate FROM film" +
                    " WHERE rental_rate < (SELECT avg(rental_rate) FROM film)" +
                    " ORDER BY rental_rate DESC",
            "SELECT postal_code, count(*) FROM customer" +
                    " INNER JOIN address ON customer.address_id = address.address_id" +
                    " GROUP BY postal_code ORDER BY count DESC",
            "SELECT first_name, last_name, c.address_id, postal_code FROM customer c" +
                    " INNER JOIN address a ON c.address_id = a.address_id",
            "SELECT title, first_name, last_name FROM film f, film_actor fa, actor a" +
                    " WHERE f.film_id=fa.film_id AND fa.actor_id=a.actor_id",
            "SELECT f.film_id, title, inventory_id, store_id FROM film f" +
                    " LEFT JOIN inventory i ON f.film_id = i.film_id" +
                    " WHERE i.film_id IS NULL"
    };


    public Queries() {

    }

    public static String getRandomString(){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        sb.append(alph.charAt(rand.nextInt(26)));
        return sb.toString();
    }

    public String getReadQuery() {
        Random rand = new Random();
        return READ_QUERIES[rand.nextInt(READ_QUERIES.length)];
    }

    public String getWriteQuery() {
        String[] WRITE_QUERIES = {
//                "INSERT INTO language (name, last_update) VALUES " +
//                        "('"+getRandomString()+"', '2021-03-29 12:00:00');",
                "UPDATE film SET title = '"+getRandomString()+"', description = 'blah' WHERE " +
                        "film_id < 400;",
                "UPDATE address SET address = '"+getRandomString()+"', district = 'no district', " +
                        "phone = '333-333-3333' WHERE address_id < 200;",
//                "INSERT INTO actor(first_name, last_name, last_update) VALUES " +
//                        "('"+getRandomString()+"', '"+getRandomString()+"', '2021-04-08 12:00:00');"
        };
        Random rand = new Random();
        return WRITE_QUERIES[rand.nextInt(WRITE_QUERIES.length)];
    }
}
