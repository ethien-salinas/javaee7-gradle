package org.certificatic.jndi;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("rs")
public class JndiResource {

    private Logger logger = Logger.getLogger("org.certificatic.jndi.JndiResource");

    @Resource(name = "derbyDataSource")
    private DataSource ds;

    @Path("jdbc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject hellodb() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            Connection conn = ds.getConnection();
            // uses connection
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // create table
            stmt.executeUpdate("Create table USERS (id int primary key, name varchar(30))");

            // insert rows (CREATE)
            stmt.executeUpdate("INSERT into USERS values (1,'Ethien')");
            stmt.executeUpdate("INSERT into USERS values (2,'Daniel')");
            stmt.executeUpdate("INSERT into USERS values (3,'Adrian')");
            stmt.executeUpdate("INSERT into USERS values (4,'Eduardo')");

            // query (READ)
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            // print out query result
            while (rs.next()) {
                builder.add(rs.getString("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return builder.build();
    }

    @Path("jdbc2")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject hellodb2() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            // JNDI
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/derbyDataSource");

            Connection conn = dataSource.getConnection();
            // uses connection
            Statement stmt = conn.createStatement();
            // create table
            stmt.executeUpdate("Create table USERS (id int primary key, name varchar(30))");

            // insert rows (CREATE)
            stmt.executeUpdate("INSERT into USERS values (1,'Ariadna')");
            stmt.executeUpdate("INSERT into USERS values (2,'Mariel')");
            stmt.executeUpdate("INSERT into USERS values (3,'Sara')");
            stmt.executeUpdate("INSERT into USERS values (4,'Yahari')");

            // query (READ)
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            // print out query result
            while (rs.next()) {
                builder.add(rs.getString("id"), rs.getString("name"));
            }
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return builder.build();
    }

}
