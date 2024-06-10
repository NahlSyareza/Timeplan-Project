package com.eszaray.timeplanspring.controller;

import com.eszaray.timeplanspring.configuration.DatabaseConnect;
import com.eszaray.timeplanspring.model.BaseResponse;
import com.eszaray.timeplanspring.model.Bidang;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// executeQuery() for SELECT statements
// executeUpdate() for INSERT statements

/**
 * Controller for queries related to bidang
 */

@RestController
@RequestMapping("/bidang")
public class BidangController {
    public final String CONNECTION_ERROR_MSG = "Connection error!";

    /**
     * Registers new bidang to the database
     *
     * @param nama_bidang
     * @param password_bidang
     * @param nama_ketua_bidang
     * @param nama_pengurus_bidang
     * @return
     */
    @PostMapping("/registerBidang")
    BaseResponse<Bidang> registerBidang(
            @RequestParam String nama_bidang,
            @RequestParam String password_bidang,
            @RequestParam String nama_ketua_bidang,
            @RequestParam String[] nama_pengurus_bidang
    ) {
        var query = "INSERT INTO bidang_ime VALUES (?,?,?,?);";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, nama_bidang.toUpperCase());
            request.setString(2, password_bidang);
            request.setString(3, nama_ketua_bidang);
            request.setArray(4, connection.createArrayOf("TEXT", nama_pengurus_bidang));

            request.executeUpdate();

            return new BaseResponse<>(true, "Bidang " + nama_bidang + " telah diregister!", new Bidang(nama_bidang, password_bidang, nama_ketua_bidang, nama_pengurus_bidang));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Edits currently existing bidang
     *
     * @param nama_bidang_old
     * @param nama_bidang_new
     * @param password_bidang
     * @param nama_ketua_bidang
     * @param nama_pengurus_bidang
     * @return
     */
    @PostMapping("/editBidang")
    BaseResponse<Bidang> editBidang(
            @RequestParam String nama_bidang_old,
            @RequestParam String nama_bidang_new,
            @RequestParam String password_bidang,
            @RequestParam String nama_ketua_bidang,
            @RequestParam String[] nama_pengurus_bidang
    ) {
        var query = "UPDATE bidang_ime SET nama_bidang=?,password_bidang=?,nama_ketua_bidang=?,nama_pengurus_bidang=? WHERE nama_bidang=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, nama_bidang_new);
            request.setString(2, password_bidang);
            request.setString(3, nama_ketua_bidang);
            request.setArray(4, connection.createArrayOf("TEXT", nama_pengurus_bidang));
            request.setString(5, nama_bidang_old);

            request.executeUpdate();

            return new BaseResponse<>(true, "Bidang " + nama_bidang_old + " telah diedit!", new Bidang(nama_bidang_new, password_bidang, nama_ketua_bidang, nama_pengurus_bidang));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Manages if an account with a password exists and returns an error if no such account and password combination is found
     *
     * @param nama_bidang
     * @param password_bidang
     * @return
     */
    @GetMapping("/loginBidang")
    BaseResponse<Bidang> loginBidang(
            @RequestParam String nama_bidang,
            @RequestParam String password_bidang
    ) {
//        var query = "SELECT * FROM bidang_ime WHERE nama_bidang='" + nama_bidang + "' AND password_bidang='" + password_bidang + "';";
        var query = "SELECT * FROM bidang_ime WHERE nama_bidang=? AND password_bidang=?;";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, nama_bidang);
            request.setString(2, password_bidang);

            var rs = request.executeQuery();

            int counter = 0;

            while (rs.next()) {
                counter++;
            }

            if (counter == 0 || counter > 1) {
                return new BaseResponse<>(false, "Nama atau password bidang salah!", null);
            } else {
                return new BaseResponse<>(true, "Sukses login bidang " + nama_bidang + "!", null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Gets a bidang using it's name
     *
     * @param nama_bidang
     * @return
     */
    @GetMapping("/getBidang")
    BaseResponse<Bidang> getBidang(
            @RequestParam String nama_bidang
    ) {
        var query = "SELECT * FROM bidang_ime WHERE nama_bidang=?;";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, nama_bidang);

            var rs = request.executeQuery();

            rs.next();

            String[] starr = rs.getArray("nama_pengurus_bidang") == null ? new String[]{""} : (String[]) rs.getArray("nama_pengurus_bidang").getArray();

            Bidang bidang = new Bidang(
                    rs.getString("nama_bidang"),
                    rs.getString("password_bidang"),
                    rs.getString("nama_ketua_bidang"),
                    starr);
            return new BaseResponse<>(true, "Informasi bidang berhasil didapatkan!", bidang);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Deletes a bidang by specifying a name
     *
     * @param namaBidang
     * @return
     */
    @PostMapping("/deleteBidang")
    BaseResponse<Bidang> deleteBidang(
            @RequestParam String namaBidang
    ) {
        var query1 = "DELETE FROM bidang_ime WHERE nama_bidang=?";
        var query2 = "DELETE FROM proker_ime WHERE nama_bidang=?";
        var query3 = "DELETE FROM proker_per_bulan WHERE nama_bidang=?";
        var query4 = "DELETE FROM milestone_proker_ime WHERE nama_proker IN (SELECT nama_proker FROM proker_ime WHERE nama_bidang=?)";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request3 = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request4 = connection.prepareStatement(query4, Statement.RETURN_GENERATED_KEYS);

            System.out.println(namaBidang);

            request1.setString(1, namaBidang);
            request2.setString(1, namaBidang);
            request3.setString(1, namaBidang);
            request4.setString(1, namaBidang);

            request4.executeUpdate();
            request1.executeUpdate();
            request2.executeUpdate();
            request3.executeUpdate();

            return new BaseResponse<>(true, "Bidang " + namaBidang + " berhasil dihapus!", null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }
}
