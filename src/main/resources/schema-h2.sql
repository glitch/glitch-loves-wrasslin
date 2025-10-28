-- # Schema generation for h2 database
-- Note different between Lucene full text prefix FTL and standard full text prefix FT

-- Lucene version
-- CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
-- CALL FTL_INIT();

-- Standard version
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();

CREATE TABLE VIDEO(ID INT PRIMARY KEY, URL VARCHAR, POSITION VARCHAR, FAMILY VARCHAR, TAGS VARCHAR, NOTES VARCHAR, RELATED VARCHAR);

-- Lucene version
-- CALL FTL_CREATE_INDEX('PUBLIC', 'VIDEO', NULL);

-- Standard version
CALL FT_CREATE_INDEX('PUBLIC', 'VIDEO', NULL);

-- Note: You will probably need to `CALL FT_REINDEX()` or `CALL FTL_REINDEX()` after your first data load
-- after that it should update in real-time.