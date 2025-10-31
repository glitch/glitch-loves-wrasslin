-- Create table & enable full-text-indexing for MySQL
CREATE TABLE IF NOT EXISTS video (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    family VARCHAR(255) DEFAULT NULL,
    notes TEXT DEFAULT NULL,
    position VARCHAR(255) DEFAULT NULL,
    related VARCHAR(255) DEFAULT NULL,
    tags TEXT DEFAULT NULL,
    url VARCHAR(255),
    INDEX idx_wrasslin_url(url),
    INDEX idx_wrasslin_family(family),
    INDEX idx_wrasslin_position(position),
    FULLTEXT INDEX idx_wrasslin_fulltext_family(family),
    FULLTEXT INDEX idx_wrasslin_fulltext_notes(notes),
    FULLTEXT INDEX idx_wrasslin_fulltext_position(position),
    FULLTEXT INDEX idx_wrasslin_fulltext_related(related),
    FULLTEXT INDEX idx_wrasslin_fulltext_tags(tags),
    FULLTEXT INDEX idx_wrasslin_fulltext(family, notes, position, tags)
) ENGINE=InnoDB;
