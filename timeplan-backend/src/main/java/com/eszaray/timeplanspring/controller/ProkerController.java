package com.eszaray.timeplanspring.controller;

import com.eszaray.timeplanspring.configuration.DatabaseConnect;
import com.eszaray.timeplanspring.model.*;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/proker")
public class ProkerController {
    public final String CONNECTION_ERROR_MSG = "Connection error!";

    @PostMapping("/addProker")
    BaseResponse<Proker> addProker(
            @RequestParam String nama_bidang,
            @RequestParam String nama_proker,
            @RequestParam String steering_comittee,
            @RequestParam JenisProker jenis_proker,
            @RequestParam int tanggal_start,
            @RequestParam Bulan bulan_start,
            @RequestParam int tanggal_end,
            @RequestParam Bulan bulan_end
    ) {
        var query1 = "INSERT INTO proker_ime VALUES (?,?,?,?::JENIS);";
        var query2 = "INSERT INTO proker_per_bulan VALUES (?,?,?,?::BULAN,?,?::BULAN);";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);

            request1.setString(1, nama_bidang.toUpperCase());
            request1.setString(2, nama_proker);
            request1.setString(3, steering_comittee);
            request1.setString(4, jenis_proker.name());

            request2.setString(1, nama_bidang.toUpperCase());
            request2.setString(2, nama_proker);
            request2.setInt(3, tanggal_start);
            request2.setString(4, bulan_start.name());
            request2.setInt(5, tanggal_end);
            request2.setString(6, bulan_end.name());

            request1.executeUpdate();
            request2.executeUpdate();

            return new BaseResponse<>(true, "Ditambahkan proker  " + nama_proker + " oleh bidang " + nama_bidang + "!", new Proker(nama_bidang, nama_proker, steering_comittee, jenis_proker));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @GetMapping("/getProker")
    BaseResponse<Proker> getProker(
        @RequestParam String nama_proker
    ) {
        var query = "SELECT * FROM proker_ime WHERE nama_proker=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, nama_proker);

            var rs = request.executeQuery();

            rs.next();

            Proker proker = new Proker(rs.getString("nama_bidang"), rs.getString("nama_proker"), rs.getString("steering_comittee"), JenisProker.valueOf(rs.getString("jenis_proker")));

            return new BaseResponse<>(true, "Berhasil mendapatkan proker " + nama_proker, proker);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @PostMapping("/editProker")
    BaseResponse<Proker> editProker(
            String nama_bidang_old,
            String nama_bidang_new
    ) {
        var query1 = "UPDATE proker_ime SET nama_bidang=? WHERE nama_bidang=?";
        var query2 = "UPDATE proker_per_bulan SET nama_bidang=? WHERE nama_bidang=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);

            request1.setString(1, nama_bidang_new);
            request1.setString(2, nama_bidang_old);
            request2.setString(1, nama_bidang_new);
            request2.setString(2, nama_bidang_old);

            request1.executeUpdate();
            request2.executeUpdate();

            return new BaseResponse<>(true, "Proker " + nama_bidang_old + " telah diedit!", null);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @GetMapping("/getProkerDisplay")
    BaseResponse<List<ProkerDisplay>> getProkerDisplay(
    ) {
        var query1 = "SELECT * FROM proker_per_bulan;";

        try (var connection = DatabaseConnect.connect()) {
            var request = connection.createStatement();

            var rs = request.executeQuery(query1);

            List<ProkerDisplay> list = new ArrayList<>();

            while(rs.next()) {
                ProkerDisplay proker_display = new ProkerDisplay(rs.getString("nama_bidang"), rs.getString("nama_proker"), Bulan.valueOf(rs.getString("bulan_start")), Bulan.valueOf(rs.getString("bulan_end")), rs.getInt("tanggal_start"), rs.getInt("tanggal_end"));
                list.add(proker_display);
            }

            return new BaseResponse<>(true, "Berhasil mendapatkan proker!", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }
}
