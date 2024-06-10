package com.eszaray.timeplanspring;

import com.eszaray.timeplanspring.configuration.DatabaseConnect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

/**
 * Everything starts here, the commented part is just some early testing to test if it successfully connects to the NeonTech database
 *
 */
@SpringBootApplication
public class TimeplanSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeplanSpringApplication.class, args);

//        var query = "CREATE TABLE bidang_ime (nama_bidang VARCHAR(50), nama_ketua_bidang VARCHAR(50), nama_pengurus_bidang VARCHAR(50)[]);";
//
//
//        try (var connection = DatabaseConnect.connect();
//             var statement = connection.createStatement()) {
//            System.out.println("SUCCESSFULLY CONNECTED!");
//
//            statement.executeUpdate(query);
//        } catch (SQLException e) {
//            System.out.println("FAILED TO CONNECT!");
//        }
    }

}
