package dev.glitch.wrasslin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.glitch.wrasslin.model.MatchPlaylistModel;
import dev.glitch.wrasslin.service.MatchPlaylistService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/match-playlists")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class MatchPlaylistAdminController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final MatchPlaylistService service;

    @PostMapping("/load")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MatchPlaylistModel> saveRecords(@RequestBody List<MatchPlaylistModel> records) {
        return service.savePlaylists(records);
    }

    @PostMapping("/loadone")
    @ResponseStatus(HttpStatus.CREATED)
    public MatchPlaylistModel saveRecord(@RequestBody MatchPlaylistModel record) {
        return service.savePlaylist(record);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteRecord(id);
    }

    @PostMapping(value = "/update")
    public MatchPlaylistModel updateRecord(@RequestBody MatchPlaylistModel record) {
        return service.updatePlaylist(record);
    }
}
