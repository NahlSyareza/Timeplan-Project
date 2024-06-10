package com.eszaray.timeplanspring.controller;

import com.eszaray.timeplanspring.configuration.DatabaseConnect;
import com.eszaray.timeplanspring.model.BaseResponse;
import com.eszaray.timeplanspring.model.Milestone;
import com.eszaray.timeplanspring.model.Status;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for queries related to milestone
 */
@RestController
@RequestMapping("/milestone")
public class MilestoneController {
    public final String CONNECTION_ERROR_MSG = "Connection error!";

    /**
     * Creates a new blank milestone with it's associated proker
     *
     * @param namaProker
     * @param namaMilestone
     * @return
     */
    @PostMapping("/addProkerMilestone")
    BaseResponse<Milestone> addProkerMilestone(
            @RequestParam String namaProker,
            @RequestParam String namaMilestone
    ) {
        var query = "INSERT INTO milestone_proker_ime VALUES (?,?,'START','');";

        try (var connection = DatabaseConnect.connect()) {
            var request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, namaProker);
            request.setString(2, namaMilestone);

            request.executeUpdate();

            return new BaseResponse<>(true, "Berhasil menambahkan milestone " + namaMilestone + " untuk proker " + namaProker + "!", new Milestone(namaProker, namaMilestone, Status.START, ""));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Gets milestone that's related to the specified proker name
     *
     * @param namaProker
     * @return
     */
    @GetMapping("/getProkerMilestone")
    BaseResponse<List<Milestone>> getProkerMilestone(
            @RequestParam String namaProker
    ) {
        var query = "SELECT * FROM milestone_proker_ime WHERE nama_proker=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, namaProker);

            var rs = request.executeQuery();

            List<Milestone> milestoneList = new ArrayList<>();

            while (rs.next()) {
                Milestone milestone = new Milestone(rs.getString("nama_proker"), rs.getString("nama_milestone"), Status.valueOf(rs.getString("progres_milestone")), rs.getString("deskripsi_milestone"));
                milestoneList.add(milestone);
            }

            return new BaseResponse<>(true, "Berhasil mendapat milestone proker " + namaProker + "!", milestoneList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Edits the contents of a milestone
     *
     * @param namaProker
     * @param namaMilestone
     * @param progresMilestone
     * @param deskripsiMilestone
     * @return
     */
    @PostMapping("/editProkerMilestone")
    BaseResponse<Milestone> editProkerMilestone(
            @RequestParam String namaProker,
            @RequestParam String namaMilestone,
            @RequestParam Status progresMilestone,
            @RequestParam String deskripsiMilestone
    ) {
        var query = "UPDATE milestone_proker_ime SET deskripsi_milestone=?,progres_milestone=?::STATUS WHERE nama_proker=? AND nama_milestone=?;";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, deskripsiMilestone);
            request.setString(2, progresMilestone.name());
            request.setString(3, namaProker);
            request.setString(4, namaMilestone);

            request.executeUpdate();

            return new BaseResponse<>(true, "Milestone berhasil diedit!", new Milestone(namaProker, namaMilestone, progresMilestone, deskripsiMilestone));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }

    /**
     * Deletes a proker from a specified proker
     *
     * @param namaProker
     * @param namaMilestone
     * @return
     */
    @PostMapping("/deleteProkerMilestone")
    BaseResponse<Milestone> deleteProkerMilestone(
            @RequestParam String namaProker,
            @RequestParam String namaMilestone
    ) {
        var query = "DELETE FROM milestone_proker_ime WHERE nama_proker=? AND nama_milestone=?";

        try (var connection = DatabaseConnect.connect()) {
            PreparedStatement request = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            request.setString(1, namaProker);
            request.setString(2, namaMilestone);

            request.executeUpdate();

            return new BaseResponse<>(true, "Berhasil menghapus milestone " + namaMilestone + " dari proker " + namaProker, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BaseResponse<>(false, CONNECTION_ERROR_MSG, null);
    }
}
