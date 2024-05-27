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
            @RequestParam String namaBidang,
            @RequestParam String namaProker,
            @RequestParam String steeringComittee,
            @RequestParam JenisProker jenisProker,
            @RequestParam int tanggalStart,
            @RequestParam Bulan bulanStart,
            @RequestParam int tanggalEnd,
            @RequestParam Bulan bulanEnd
    ) {
        var query1 = "INSERT INTO proker_ime VALUES (?,?,?,?::JENIS);";
        var query2 = "INSERT INTO proker_per_bulan VALUES (?,?,?,?::BULAN,?,?::BULAN);";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);

            request1.setString(1, namaBidang.toUpperCase());
            request1.setString(2, namaProker);
            request1.setString(3, steeringComittee);
            request1.setString(4, jenisProker.name());

            request2.setString(1, namaBidang.toUpperCase());
            request2.setString(2, namaProker);
            request2.setInt(3, tanggalStart);
            request2.setString(4, bulanStart.name());
            request2.setInt(5, tanggalEnd);
            request2.setString(6, bulanEnd.name());

            request1.executeUpdate();
            request2.executeUpdate();

            return new BaseResponse<>(true, "Ditambahkan proker  " + namaProker + " oleh bidang " + namaBidang + "!", new Proker(namaBidang, namaProker, steeringComittee, jenisProker));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @GetMapping("/getNamaProker")
    BaseResponse<Proker> getNamaProker(
            @RequestParam String namaProker
    ) {
        var query = "SELECT * FROM proker_ime WHERE nama_proker=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, namaProker);

            var rs = request.executeQuery();

            rs.next();

            Proker proker = new Proker(rs.getString("nama_bidang"), rs.getString("nama_proker"), rs.getString("steering_comittee"), JenisProker.valueOf(rs.getString("jenis_proker")));

            return new BaseResponse<>(true, "Berhasil mendapatkan proker " + namaProker, proker);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @GetMapping("/getBidangProker")
    BaseResponse<List<ProkerDisplay>> getBidangProker(
            @RequestParam String namaBidang
    ) {
        var query = "SELECT * FROM proker_per_bulan WHERE nama_bidang=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, namaBidang);

            var rs = request.executeQuery();

            List<ProkerDisplay> list = new ArrayList<>();

            while (rs.next()) {
                ProkerDisplay prokerDisplay = new ProkerDisplay(rs.getString("nama_bidang"), rs.getString("nama_proker"), Bulan.valueOf(rs.getString("bulan_start")), Bulan.valueOf(rs.getString("bulan_end")), rs.getInt("tanggal_start"), rs.getInt("tanggal_end"));
                list.add(prokerDisplay);
            }


            return new BaseResponse<>(true, "Berhasil mendapatkan seluruh proker bidang  " + namaBidang, list);
        } catch (SQLException e) {
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

            while (rs.next()) {
                ProkerDisplay prokerDisplay = new ProkerDisplay(rs.getString("nama_bidang"), rs.getString("nama_proker"), Bulan.valueOf(rs.getString("bulan_start")), Bulan.valueOf(rs.getString("bulan_end")), rs.getInt("tanggal_start"), rs.getInt("tanggal_end"));
                list.add(prokerDisplay);
            }

            return new BaseResponse<>(true, "Berhasil mendapatkan proker!", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    @PostMapping("/editProker")
    BaseResponse<Proker> editProker(
            String namaBidangOld,
            String namaBidangNew
    ) {
        var query1 = "UPDATE proker_ime SET nama_bidang=? WHERE nama_bidang=?";
        var query2 = "UPDATE proker_per_bulan SET nama_bidang=? WHERE nama_bidang=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement request2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);

            request1.setString(1, namaBidangNew);
            request1.setString(2, namaBidangOld);
            request2.setString(1, namaBidangNew);
            request2.setString(2, namaBidangOld);

            request1.executeUpdate();
            request2.executeUpdate();

            return new BaseResponse<>(true, "Proker " + namaBidangOld + " telah diedit!", null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }
}
