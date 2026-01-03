package dev.glitch.wrasslin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.glitch.wrasslin.model.MatchPlaylistModel;
import dev.glitch.wrasslin.service.MatchPlaylistService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/match-playlists")
@RequiredArgsConstructor
public class MatchPlaylistController {

    private final MatchPlaylistService service;

    @GetMapping(value = "/all")
    public List<MatchPlaylistModel> getAll() {
        return service.getPlaylists();
    }

    @GetMapping(value = "/all/{year}")
    public List<MatchPlaylistModel> getAllByYear(@PathVariable("year") int year) {
        return service.getPlaylistsByYear(year);
    }
}
