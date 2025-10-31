package dev.glitch.wrasslin.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.glitch.wrasslin.model.VideoModel;
import dev.glitch.wrasslin.service.VideoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/videos")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class VideoAdminController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final VideoService service;

    @PostMapping("/load")
    @ResponseStatus(HttpStatus.CREATED)
    public List<VideoModel> saveRecords(@RequestBody List<VideoModel> records) {
        return service.saveRecords(records);
    }

    @PostMapping("/loadone")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoModel saveRecord(@RequestBody VideoModel record) {
        return service.saveRecord(record);
    }

    @PostMapping("/loadunique")
    @ResponseStatus(HttpStatus.CREATED)
    public List<VideoModel> saveUniqueRecords(@RequestBody List<VideoModel> records) {
        return service.saveRecordIfUniqueUrl(records);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteRecord(id);
    }

    @DeleteMapping(value = "/delete")
    public void deleteById(@RequestBody List<VideoModel> videos) {
        service.deleteRecords(videos);
    }

    @PostMapping(value = "/update")
    public VideoModel updateRecord(@RequestBody VideoModel record) {
        return service.updateRecord(record);
    }

    /**
     * Currently needed to trigger H2 full-text indexing after initial data load.
     */
    @GetMapping("/reindex")
    public void reindex() {
        service.reindex();
    }

    @GetMapping("/download/json")
    public ResponseEntity<String> downloadJson() throws Exception {
        String allRecords = mapper.writeValueAsString(service.dumpDB());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=wrasslin_records.json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(allRecords.getBytes(StandardCharsets.UTF_8).length);

        return ResponseEntity.ok().headers(headers).body(allRecords);
    }
}
